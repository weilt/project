package com.hx.user.redPacket.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.redPacket.HxUserTransfer;
import com.hwt.domain.entity.user.redPacket.vo.HxUserTransferVo;
import com.hwt.domain.entity.user.wallet.HxUserWallet;
import com.hwt.domain.entity.user.wallet.HxUserWalletBill;
import com.hwt.domain.mapper.user.redPacket.HxUserTransferMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletBillMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletMapper;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.SequenceFactoryBean2;
import com.hx.core.utils.ZZBigDecimalUtils;
import com.hx.user.redPacket.service.TransferService;

@Service
public class TransferServiceImpl implements TransferService{
	
	@Autowired
	private HxUserTransferMapper hxUserTransferMapper;
	
	@Autowired
	private HxUserWalletMapper hxUserWalletMapper;
	
	@Autowired
	private HxUserWalletBillMapper hxUserWalletBillMapper;

	@Override
	@Transactional
	public HxUserTransfer send(Long user_id, String title, String content, Long accept_user_id,
			BigDecimal send_amount,Integer transfer_type) {
		long currentTimeMillis = System.currentTimeMillis();
		HxUserTransfer hxUserTransfer = new HxUserTransfer();
		hxUserTransfer.setAccept_user_id(accept_user_id);
		hxUserTransfer.setContent(content);
		hxUserTransfer.setCreat_time(currentTimeMillis);
		
		hxUserTransfer.setIs_accept(0);
		hxUserTransfer.setSend_amount(send_amount);
		hxUserTransfer.setTitle(title);
		hxUserTransfer.setUser_id(user_id);
		hxUserTransfer.setTransfer_type(transfer_type);
		hxUserTransferMapper.insertBackId(hxUserTransfer);
		return hxUserTransfer;
	}

	@Override
	@Transactional
	public int accept(LoginVo loginVo, Long transfer_id, Long send_user_id, Long accept_user_id, Integer is_accept) throws Exception {
		
		String redpacketprefix = HwPrefix.TransferPrefix+transfer_id+"_"+
				send_user_id+"_"+loginVo.getUser_id();
		System.err.println(redpacketprefix);
		String str = RedisCache.get(redpacketprefix, RedisCache.db10);
		
		if (!ObjectHelper.isEmpty(str)){
			HxUserTransfer selectByPrimaryKey = hxUserTransferMapper.selectByPrimaryKey(transfer_id);
			long currentTimeMillis = System.currentTimeMillis();
			String trans_num = new SequenceFactoryBean2().getObject();
			selectByPrimaryKey.setIs_accept(is_accept);
			if (is_accept==1){
				//说明是接受
				selectByPrimaryKey.setLinqu_time(currentTimeMillis);
				//生成账单
				HxUserWalletBill robred = robred(loginVo.getUser_id(), selectByPrimaryKey.getSend_amount(), trans_num, "收款","收到一笔转账",null);
				robred.setSource(4);
				robred.setBill_type(10);
				hxUserWalletBillMapper.insertSelective(robred);
				
			} else {
				//说明是拒收
				selectByPrimaryKey.setRefuse_time(currentTimeMillis);
				selectByPrimaryKey.setRefuse_trans_num(trans_num);
				//生成账单
				HxUserWalletBill robred = robred(send_user_id, selectByPrimaryKey.getSend_amount(), trans_num, "退款","对方拒绝收账",null);
				robred.setSource(4);
				robred.setBill_type(11);
				hxUserWalletBillMapper.insertSelective(robred);
			}
			
			hxUserTransferMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
			//删除缓存
			RedisCache.del(redpacketprefix, RedisCache.db10);
		} else {
			return 0;
		}
		return is_accept;
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

	@Override
	public int is_accept(Long transfer_id) {
		HxUserTransfer selectByPrimaryKey = hxUserTransferMapper.selectByPrimaryKey(transfer_id);
		if(selectByPrimaryKey.getLinqu_time()!=null){
			return -1;
		} 
		return 0;
	}

	@Override
	public HxUserTransferVo info(Long user_id, Long transfer_id) {
		HxUserTransferVo hxUserTransferVo = hxUserTransferMapper.queryHxUserTransferVoById(transfer_id);
		if (hxUserTransferVo==null){
			return null;
		} else {
			if (!hxUserTransferVo.getUser_id().equals(user_id)&&!hxUserTransferVo.getAccept_user_id().equals(user_id)){
				return null;
			} else {
				return hxUserTransferVo;
			}
		}
		
	}

}
