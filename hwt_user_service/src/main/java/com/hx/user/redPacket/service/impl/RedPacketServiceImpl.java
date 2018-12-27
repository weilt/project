package com.hx.user.redPacket.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.HxUserRobRedPacket;
import com.hwt.domain.entity.user.redPacket.vo.HxUserRedPacketVo;
import com.hwt.domain.entity.user.wallet.HxUserWallet;
import com.hwt.domain.entity.user.wallet.HxUserWalletBill;
import com.hwt.domain.mapper.user.redPacket.HxUserRedPacketMapper;
import com.hwt.domain.mapper.user.redPacket.HxUserRobRedPacketMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletBillMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletMapper;
import com.hx.core.exception.BaseException;
import com.hx.core.redis.RedisCache;
import com.hx.core.redis.RedisLock;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.SequenceFactoryBean2;
import com.hx.core.utils.ZZBigDecimalUtils;
import com.hx.user.redPacket.service.RedPacketService;

@Service
public class RedPacketServiceImpl implements RedPacketService{
	
	@Autowired
	private HxUserRedPacketMapper hxUserRedPacketMapper;
	
	@Autowired
	private HxUserRobRedPacketMapper hxUserRobRedPacketMapper;
	
	@Autowired
	private HxUserWalletBillMapper hxUserWalletBillMapper;
	
	@Autowired
	private HxUserWalletMapper hxUserWalletMapper;

	@Override
	@Transactional
	public HxUserRedPacket send(Long user_id, String title, String content, Integer is_random,Long group_id, Long accept_user_id,
			BigDecimal send_amount,Integer packet_number) {
		
		long currentTimeMillis = System.currentTimeMillis();
		
		
		 
		HxUserRedPacket hxUserRedPacket = new HxUserRedPacket();
		hxUserRedPacket.setAccept_user_id(accept_user_id);
		
		hxUserRedPacket.setContent(content);
		hxUserRedPacket.setCreat_time(currentTimeMillis);
		hxUserRedPacket.setGroup_id(group_id);
		hxUserRedPacket.setPacket_number(packet_number);
		
		hxUserRedPacket.setSend_amount(send_amount);
		hxUserRedPacket.setTitle(title);
		hxUserRedPacket.setUser_id(user_id);
		hxUserRedPacket.setIs_pay(0);
		hxUserRedPacket.setIs_random(is_random);
		
		//返回主键添加
		hxUserRedPacketMapper.insertBackId(hxUserRedPacket);
		
//		Long red_packet_id = hxUserRedPacket.getRed_packet_id();
//		
//		//将数据加入缓存
//		List<BigDecimal> math = RedPacketUtils.math(send_amount, packet_number);
//		String redpacketprefix = HwPrefix.RedPacketPrefix+red_packet_id+"_"+group_id+"_"+accept_user_id;
//		System.err.println(redpacketprefix);
//		RedisCache.setpar(redpacketprefix, JsonUtils.Bean2Json(math), 24*60*60, RedisCache.db10);
		return hxUserRedPacket;
	}

	@Override
	@Transactional
	public int rob(LoginVo loginVo, Long red_packet_id, Long group_id, Long accept_user_id) throws Exception {
		String redpacketprefix = HwPrefix.RedPacketPrefix+red_packet_id+"_"+group_id+"_"+accept_user_id;
		//缓存锁
		String redPacketLockPrefix = HwPrefix.RedPacketLockPrefix+red_packet_id+"_"+group_id+"_"+accept_user_id;
		//缓存锁
		RedisLock redisLock = new RedisLock(redPacketLockPrefix);
		//String str = RedisCache.get(redpacketprefix, RedisCache.db10);
		Map<String, String> hgetgey = RedisCache.hgetgey(redpacketprefix, RedisCache.db10);
		System.err.println(hgetgey);
		String key = null;
		//失效时间
		//int experice = 0;
		
		//1-是最后一个  -1不是最后一个  0-没抢到
		int num = -1;
		if (!hgetgey.isEmpty()){
			
			redisLock.lock(2000, 2);
			long currentTimeMillis = System.currentTimeMillis();

			HxUserRobRedPacket packet = hxUserRobRedPacketMapper.selectByByPrimaryKey(loginVo.getUser_id(),red_packet_id);
			if( packet!=null){
				throw new BaseException("已经抢过红包了！");
			}
			
			for (String aa : hgetgey.keySet()) {
				key = aa;
				break;
			}
			
			BigDecimal bigDecimal = new BigDecimal(hgetgey.remove(key));
			String trans_num = new SequenceFactoryBean2().getObject();
			HxUserRobRedPacket hxUserRobRedPacket = new HxUserRobRedPacket();
			hxUserRobRedPacket.setAccept_amount(bigDecimal);
			hxUserRobRedPacket.setUser_id(loginVo.getUser_id());
			hxUserRobRedPacket.setCreat_time(currentTimeMillis);
			hxUserRobRedPacket.setRed_packet_id(red_packet_id);
			hxUserRobRedPacket.setTrans_num(trans_num);
			hxUserRobRedPacketMapper.insertSelective(hxUserRobRedPacket);
			
			//生成账单
			
			HxUserWalletBill walletBill = robred(loginVo.getUser_id(),bigDecimal,trans_num,"抢红包","抢到一个红包",null);
			walletBill.setSource(4);
			hxUserWalletBillMapper.insertSelective(walletBill);
			if (hgetgey.isEmpty()){
				//说明是最后一个了
				RedisCache.del(redpacketprefix, RedisCache.db10);
				hxUserRedPacketMapper.linwan_time(currentTimeMillis,red_packet_id);
				num = 1;
				
			} else {
				RedisCache.hdel(redpacketprefix, key, RedisCache.db10);
			}
						//释放锁
			redisLock.unLock();
		} else {
			return 0;
		}
		return num;
		
	}

	/**
	 * 钱包操作
	 * @param user_id
	 * @param bigDecimal
	 * @param trans_num
	 * @param //string
	 * @return
	 */
	private HxUserWalletBill robred(Long user_id, BigDecimal bigDecimal, String trans_num, String title,String bill_dec,String remarks) {
		HxUserWallet hxUserWallet = hxUserWalletMapper.selectByUserId(user_id);
		
		
		//加钱
		hxUserWalletMapper.addBalanceById(user_id, bigDecimal, 1);
		
		//hxUserWalletMapper.safeBalanceByUserId(user_id, payment);
		HxUserWalletBill createBill = createBill(user_id, bill_dec, title, 8, null, trans_num,
				remarks, bigDecimal, ZZBigDecimalUtils.safeAdd(hxUserWallet.getBalance(), bigDecimal), null, 1, 1);
		
		return createBill;
	}

	@Override
	public HxUserRedPacketVo queryInfo(Long red_packet_id) {
		HxUserRedPacketVo userRobRedPacketVo = hxUserRedPacketMapper.queryInfoById(red_packet_id);
		
		return userRobRedPacketVo;
	}
	
	/**
	 * 创建账单
	 * @param user_id    用户id
	 * @param bill_dec		描述
	 * @param bill_title	说明
	 * @param bill_type		1-充值  2-支付 3-收入 4-退款 5-提现  6-违约金收入 7-发红包 8-抢红包 9-转账 10-收转账
	 * @param order_num		订单编号
	 * @param trans_num		交易单号
	 * @param remarks		备注
	 * @param operation_amount  操作金额 
	 * @param balance		总余额
	 * @param flow_num		第三方支付流水号
	 * @param is_get		是否进账  0-否 1- 是
	 * @param is_success	是否成功  0-否 1-是
	 * @return
	 */
	private HxUserWalletBill createBill(Long user_id, String bill_dec,String bill_title, Integer bill_type,String order_num, 
			String trans_num,String remarks,BigDecimal operation_amount,BigDecimal balance, String flow_num, Integer is_get,Integer is_success){
		HxUserWalletBill hxUserWalletBill = new HxUserWalletBill();
		long currentTimeMillis = System.currentTimeMillis();
		hxUserWalletBill.setBalance(balance);
		hxUserWalletBill.setBill_dec(bill_dec);
		hxUserWalletBill.setBill_title(bill_title);
		hxUserWalletBill.setBill_type(bill_type);
		hxUserWalletBill.setCreate_time(currentTimeMillis);
		hxUserWalletBill.setEnd_time(currentTimeMillis);
		hxUserWalletBill.setFlow_num(flow_num);
		hxUserWalletBill.setIs_get(is_get);
		hxUserWalletBill.setIs_success(is_success);
		hxUserWalletBill.setIs_wallet(1);
		hxUserWalletBill.setName_id(user_id);
		hxUserWalletBill.setName_type(1);
		hxUserWalletBill.setOrder_num(order_num);
		hxUserWalletBill.setRemarks(remarks);
		hxUserWalletBill.setSource(1);
		hxUserWalletBill.setStare_time(currentTimeMillis);
		hxUserWalletBill.setTrans_num(trans_num);
		hxUserWalletBill.setOperation_amount(operation_amount);
		return hxUserWalletBill;
	}
}
