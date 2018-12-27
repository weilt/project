package com.hx.user.async.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.HxUserTransfer;
import com.hwt.domain.entity.user.wallet.HxUserWallet;
import com.hwt.domain.entity.user.wallet.HxUserWalletBill;
import com.hwt.domain.mapper.user.HxUserMapper;
import com.hwt.domain.mapper.user.redPacket.HxUserRedPacketMapper;
import com.hwt.domain.mapper.user.redPacket.HxUserRobRedPacketMapper;
import com.hwt.domain.mapper.user.redPacket.HxUserTransferMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletBillMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletMapper;
import com.hx.core.redis.RedisCache;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.SequenceFactoryBean2;
import com.hx.core.utils.ZZBigDecimalUtils;
import com.hx.core.wyim.notice.SentNotice;
import com.hx.user.async.UserAsyncService;

@Service
public class UserAsyncServiceImpl implements UserAsyncService {

	@Autowired
    private HxUserTransferMapper hxUserTransferMapper;
    
    @Autowired
    private HxUserRedPacketMapper hxUserRedPacketMapper;
    
    @Autowired
    private HxUserRobRedPacketMapper hxUserRobRedPacketMapper;
    
    @Autowired
    private HxUserWalletMapper hxUserWalletMapper;
    
    @Autowired
    private HxUserWalletBillMapper hxUserWalletBillMapper;
    
    @Autowired
    private SentNotice sentNotice;
    
    @Autowired
    private HxUserMapper hxUserMapper;
    
    /**
     * 转账过期
     * @param message
     * @throws Exception 
     */
   // @Async("asyncServiceExecutor")
    @Transactional
	@Override
	public void transferExpire(String message) throws Exception {
//		if(ObjectHelper.isEmpty(message)){
//			
//		}
		String[] split = message.split("_");
		
		//前缀
//		String prefix = split[0];
		
		//实体的id
		Long transfer_id = Long.parseLong(split[1]) ;
		
		
		//转账人id
		Long user_id = Long.parseLong(split[2]) ;
		
//		//接受人id
//		Long accept_user_id = Long.parseLong(split[3]);
//		
//		//处理的次数
//		Integer count = 0;
//		if (split.length==5){
//			count = Integer.parseInt(split[4]);
//			
//		}
		
		//处理数据
		HxUserTransfer selectByPrimaryKey = hxUserTransferMapper.selectByPrimaryKey(transfer_id);
		if (selectByPrimaryKey==null){
			return;
		}
		if (selectByPrimaryKey.getIs_accept()==0){
			
			//说明转账确实是在24小时内未接受 发起自动退款
			String trans_num = new SequenceFactoryBean2().getObject();
			System.err.println(trans_num+"-----------------------"+selectByPrimaryKey.getIs_accept());
			HxUserWalletBill robred = robred(user_id, selectByPrimaryKey.getSend_amount(), trans_num, "转账退回","超过24小时未被领取，自动退回",null);
			robred.setSource(4);
			robred.setBill_type(11);
			hxUserWalletBillMapper.insertSelective(robred);
			
			selectByPrimaryKey.setRefuse_trans_num(trans_num);
			selectByPrimaryKey.setRefuse_time(System.currentTimeMillis());
			selectByPrimaryKey.setIs_accept(3);
			hxUserTransferMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
			
			//发送系统消息
			String im_id = hxUserMapper.queryImIdByUserId(selectByPrimaryKey.getUser_id());
			sentNotice.sendSystem(im_id, "转账退回", "超过24小时未被领取，自动退回", null, 2, null, JsonUtils.Bean2Json(robred));
		}
		
	}

	/**
	 * 红包过期
	 * @param message
	 * @throws Exception 
	 */
    //@Async("asyncServiceExecutor")
    @Transactional
	@Override
    public void redPacketExpire(String message) throws Exception {
		
		String[] split = message.split("_");
		
//		//前缀
//		String prefix = split[0];
		
		//实体的id
		Long red_packet_id = Long.parseLong(split[1]) ;
		
		
//		//群id
//		Long group_id = Long.parseLong(split[2]) ;
//		
//		//接受人id
//		Long accept_user_id = Long.parseLong(split[3]);
//		
//		//处理的次数
//		Integer count = 0;
//		if (split.length==5){
//			count = Integer.parseInt(split[4]);
//			
//		}
		//处理数据
		
		HxUserRedPacket selectByPrimaryKey = hxUserRedPacketMapper.selectByPrimaryKey(red_packet_id);
		if (selectByPrimaryKey.getLinwan_time()==null){
			//说明确实没被领完
			BigDecimal acceptTotalAmount = hxUserRobRedPacketMapper.acceptTotalAmount(red_packet_id);
			acceptTotalAmount = acceptTotalAmount ==null?new BigDecimal("0.00"):acceptTotalAmount;
			if (selectByPrimaryKey.getSend_amount().compareTo(acceptTotalAmount)==-1){
				throw new RuntimeException("数据有误！");
			}
			//退钱
			String trans_num = new SequenceFactoryBean2().getObject();
			BigDecimal safeSubtract = ZZBigDecimalUtils.safeSubtract(true, selectByPrimaryKey.getSend_amount(), acceptTotalAmount);
			HxUserWalletBill robred = robred(selectByPrimaryKey.getUser_id(), safeSubtract, trans_num, "红包退回","超过24小时，剩余红包自动退回",null);
			robred.setSource(4);
			robred.setBill_type(12);
			hxUserWalletBillMapper.insertSelective(robred);
			
			//发送系统消息
			String im_id = hxUserMapper.queryImIdByUserId(selectByPrimaryKey.getUser_id());
			sentNotice.sendSystem(im_id, "红包退回", "超过24小时，剩余红包自动退回", null, 2, null, JsonUtils.Bean2Json(robred));
		}
		
	}
	
	/**
	 * 钱包操作
	 * @param user_id
	 * @param bigDecimal
	 * @param trans_num
	 * @param string
	 * @return
	 */
	private HxUserWalletBill robred(Long user_id, BigDecimal bigDecimal, String trans_num, String title,String bill_dec,String remarks) {
		HxUserWallet hxUserWallet = hxUserWalletMapper.selectByUserId(user_id);
		
		
		//加钱
		hxUserWalletMapper.addBalanceById(user_id, bigDecimal, 1);
		
		//hxUserWalletMapper.safeBalanceByUserId(user_id, payment);
		HxUserWalletBill createBill = createBill(user_id, bill_dec, title, 7, null, trans_num,
				remarks, bigDecimal, ZZBigDecimalUtils.safeAdd(hxUserWallet.getBalance(), bigDecimal), null, 1, 1);
		
		return createBill;
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
