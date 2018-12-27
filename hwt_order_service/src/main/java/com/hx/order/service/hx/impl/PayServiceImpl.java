package com.hx.order.service.hx.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.hwt.domain.entity.order.HwOrder;
import com.hwt.domain.entity.order.HwOrderUnpaid;
import com.hwt.domain.entity.order.HxOrderInfo;
import com.hwt.domain.entity.order.vo.HxOrderInfoVo;
import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.HxUserTransfer;
import com.hwt.domain.entity.user.wallet.HxUserWallet;
import com.hwt.domain.entity.user.wallet.HxUserWalletBill;
import com.hwt.domain.mapper.bureau.HwTravelBureauMapper;
import com.hwt.domain.mapper.order.HwOrderMapper;
import com.hwt.domain.mapper.order.HwOrderUnpaidMapper;
import com.hwt.domain.mapper.order.HxOrderInfoMapper;
import com.hwt.domain.mapper.user.HxUserMapper;
import com.hwt.domain.mapper.user.redPacket.HxUserRedPacketMapper;
import com.hwt.domain.mapper.user.redPacket.HxUserTransferMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletBillMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletMapper;
import com.hx.core.exception.BaseException;
import com.hx.core.pay.alipay.AliPayConstants;
import com.hx.core.pay.alipay.AlipaySDK;
import com.hx.core.pay.alipay.PayType;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.SequenceFactoryBean2;
import com.hx.core.utils.ZZBigDecimalUtils;
import com.hx.core.wyim.entity.emu.SMSNotice;
import com.hx.core.wyim.entity.emu.SMSType;
import com.hx.core.wyim.notice.SentNotice;
import com.hx.order.service.RedPacketUtils.RedPacketUtils;
import com.hx.order.service.hx.PayService;

@Service
public class PayServiceImpl implements PayService{
	
	@Autowired
	private HwOrderMapper hwOrderMapper;
	
	@Autowired
	private HxOrderInfoMapper hxOrderInfoMapper;
	
	@Autowired
	private HxUserWalletBillMapper hxUserWalletBillMapper;
	
	@Autowired
	private HxUserWalletMapper hxUserWalletMapper;
	
	@Autowired
	private HwOrderUnpaidMapper hwOrderUnpaidMapper;
	
	@Autowired
	private SentNotice sentNotice;
	
	@Autowired
	private HxUserMapper hxUserMapper;
	
	@Autowired 
	private HwTravelBureauMapper hwTravelBureauMapper;
	
	@Autowired
	private HxUserRedPacketMapper hxUserRedPacketMapper;
	
	@Autowired
	private HxUserTransferMapper hxUserTransferMapper;
	
	@Value("${RedORTransferExpirationDate}")
	private Integer RedORTransferExpirationDate;

	@Override
	@Transactional
	public void wallet_pay(Long user_id, String order_num, String payment_password, String smsVerify) throws Exception {
		
		
		HwOrderUnpaid hwOrderUnpaid = hwOrderUnpaidMapper.selectByOrder_num(order_num);
		if (hwOrderUnpaid==null){
			throw new BaseException("订单不存在!");
		}
//		int compareTo = hwOrderUnpaid.getPayment().compareTo(new BigDecimal("5000"));
//		if (compareTo!=-1){
//			//说明不小于5000
//			String code = RedisCache.get(SMSType.walletPay.smsTypePrefix + hwOrderUnpaid.getBuyer_phone());
//			
//			if(StringUtils.isBlank(code) || !code.equals(smsVerify)) {
//				throw new BaseException("验证码输入错误!");
//			}
//		}
		HwOrder hwOrder = getOrder(hwOrderUnpaid);
		
		HxOrderInfoVo hxOrderInfo = hxOrderInfoMapper.query_by_order_id(hwOrder.getOrder_id());
		
		HxUserWalletBill walletPay = walletPay(hwOrder, payment_password, hxOrderInfo);
		walletPay.setSource(3);
		
		hwOrder.setUpdate_time(System.currentTimeMillis());
		
		hwOrderMapper.insertSelective(hwOrder);
		hxUserWalletBillMapper.insertSelective(walletPay);
		hwOrderUnpaidMapper.deleteByPrimaryKey(hwOrderUnpaid.getOrder_id());
		
		//发消息
		if(hwOrder.getCicerone_id()!=0){
			sentNotice.sendSystem(getUserImId(hwOrder.getCicerone_id()), "新的订单通知", "您有一个新的订单", null, 8, hwOrder.getOrder_id()+"", null);
		}else {
			sentNotice.sendSystemSms(getBureauPhone(hwOrder.getBureau_id()), SMSNotice.bureauorder.smsTypeTemplateNumber, null);
		}
		
		sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "支付通知", "您有一笔订单支付成功", null, 2, null, JsonUtils.Bean2Json(walletPay));
	}
	
	/**
	 * 创建订单
	 * @param hwOrderUnpaid
	 * @return
	 */
	private HwOrder getOrder(HwOrderUnpaid hwOrderUnpaid) {
		HwOrder hwOrder = new HwOrder();
		hwOrder.setOrder_id(hwOrderUnpaid.getOrder_id());
		hwOrder.setOrder_num(hwOrderUnpaid.getOrder_num());
		hwOrder.setBureau_id(hwOrderUnpaid.getBureau_id());
		hwOrder.setCicerone_id(hwOrderUnpaid.getCicerone_id());
		hwOrder.setLine_id(hwOrderUnpaid.getLine_id());
		hwOrder.setUser_id(hwOrderUnpaid.getUser_id());
		hwOrder.setInsurance_id(hwOrderUnpaid.getInsurance_id());
		hwOrder.setCoupon_id(hwOrderUnpaid.getCoupon_id());
		hwOrder.setPayment(hwOrderUnpaid.getPayment());
		hwOrder.setCreate_time(hwOrderUnpaid.getCreate_time());
		hwOrder.setStart_time(hwOrderUnpaid.getStart_time());
		hwOrder.setEnd_time(hwOrderUnpaid.getEnd_time());
		hwOrder.setBuyer_message(hwOrderUnpaid.getBuyer_message());
		hwOrder.setBuyer_name(hwOrderUnpaid.getBuyer_name());
		hwOrder.setBuyer_phone(hwOrderUnpaid.getBuyer_phone());
		hwOrder.setAdult_num(hwOrderUnpaid.getAdult_num());
		hwOrder.setChildren_num(hwOrderUnpaid.getChildren_num());
		hwOrder.setPeople_num(hwOrderUnpaid.getPeople_num());
		return hwOrder;
	}
	/**
	 * 零钱支付
	 * @param hwOrder
	 * @param payment_password
	 * @param hxOrderInfo 
	 * @return
	 * @throws Exception 
	 */
	private HxUserWalletBill walletPay(HwOrder hwOrder, String payment_password, HxOrderInfoVo hxOrderInfo) throws Exception {
		Long user_id = hwOrder.getUser_id();
		BigDecimal payment = hwOrder.getPayment();
		
		HxUserWallet hxUserWallet = hxUserWalletMapper.selectByUserId(user_id);
		
		if (ObjectHelper.isEmpty(hxUserWallet)){
			throw new RuntimeException("余额不足，请充值，或者采用其他方式支付！");
		}
		
		if (ObjectHelper.isEmpty(hxUserWallet.getPayment_password())){
			throw new RuntimeException("请先设置支付密码！");
		}
		if(!hxUserWallet.getPayment_password().equals(ObjectHelper.passWord(payment_password, hxUserWallet.getPayment_salt()))){
			
			throw new RuntimeException("支付密码错误！");
		}
		
		if (hxUserWallet.getIs_can().compareTo(payment)==-1){
			throw new RuntimeException("余额不足，请充值，或者采用其他方式支付！");
		}
		
		//扣钱
		int num = hxUserWalletMapper.safeBalanceByUserId(user_id, payment);
		if (num!=1){
			throw new RuntimeException("余额不足，请充值，或者采用其他方式支付！");
		}
		hwOrder.setStatus(1);
		hwOrder.setPayment_time(System.currentTimeMillis());
		hwOrder.setPaymen_type(3);
		//hxUserWalletMapper.safeBalanceByUserId(user_id, payment);
		HxUserWalletBill createBill = createBill(user_id, hxOrderInfo.getDec(), hxOrderInfo.getDec(), 2, hwOrder.getOrder_num(), new SequenceFactoryBean2().getObject(),
				"订单支付", hwOrder.getPayment(), ZZBigDecimalUtils.safeSubtract(true, hxUserWallet.getBalance(), hwOrder.getPayment()), null, 0, 1);
		
		
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
	
	/**
	 * 通过用户id查询im_id
	 * @param user_id
	 * @return
	 */
	private String getUserImId(Long user_id) {
		String im_id = hxUserMapper.queryImIdByUserId(user_id);
		return im_id;
	}
	
	/**
	 * 通过旅行社id查询电话
	 */
	private String getBureauPhone(Long bureau_id){
		String phone = hwTravelBureauMapper.getBureauPhone(bureau_id);
		return phone ;
	}

	@Override
	public Object zhifubao_pay(String token,Long user_id, String order_num) {
		HwOrderUnpaid hwOrderUnpaid = hwOrderUnpaidMapper.selectByOrder_num(order_num);
		
		Map<String, Object> map = null;
		PayType payType = new PayType(user_id, 2);
		String attach = JsonUtils.Bean2Json(payType);  //自定义数据包
		//支付宝支付
		String body = new AlipaySDK().toPay(hwOrderUnpaid.getPayment().toString(),"订单支付", attach, order_num);
		map = new HashMap<>();
		map.put("body", body);
		map.put("appid", AliPayConstants.app_id); //APPID
		if(map == null || map.size() == 0) {
			throw new BaseException("获取支付订单失败");
		}
		System.out.println(map);
		return map;
	}

	
	@Override
	@Transactional
	public void pay_callBack(String out_trade_no, String passback_params, int type,int success) throws Exception {
		PayType payType = JsonUtils.json2Bean(passback_params, PayType.class);
		
		if(ObjectHelper.isEmpty(payType)) {
			return;
		}
		// 1- 充值支付  ，2- 支付
		switch (payType.getPayType()) {
		case 1:
			payRecharge(out_trade_no, payType,type,success);
			break;
		case 2:
			payOrder(out_trade_no, payType, type,success);
			break;
		
		default:
			break;
		}
	}
	
	/**
	 * 支付回调信息 通知处理
	 * @param out_trade_no - 订单ID
	 *  attach - 自定义参数信息
	 * @param type - 1-微信支付，2-支付宝充值
	 * @param type - success 1成功 2失败
	 */
	private void payOrder(String out_trade_no, PayType payType, int type,int success) throws Exception {
		
		
		
		HwOrderUnpaid hwOrderUnpaid = hwOrderUnpaidMapper.selectByOrder_num(out_trade_no);
		if (hwOrderUnpaid==null){
			throw new BaseException("订单不存在!");
		}
		
		HwOrder hwOrder = getOrder(hwOrderUnpaid);
		
		HxOrderInfoVo hxOrderInfo = hxOrderInfoMapper.query_by_order_id(hwOrder.getOrder_id());
		
		HxUserWalletBill threePay = threePay(hwOrder, hxOrderInfo,type,payType);
		
		//根据是否充值判断是不是钱包操作
		if (payType.getPayType()==1){
			threePay.setIs_wallet(1);
		} else {
			threePay.setIs_wallet(0);
		}
		threePay.setIs_success(success);
		hxUserWalletBillMapper.insertSelective(threePay);
		if (success==2){
			//说明支付失败
			return ;
		}
		
		
		hwOrder.setUpdate_time(System.currentTimeMillis());
		
		hwOrderMapper.insertSelective(hwOrder);
		
		hwOrderUnpaidMapper.deleteByPrimaryKey(hwOrderUnpaid.getOrder_id());
		
		//给商家发通知
		if(hwOrder.getCicerone_id()!=0){
		
			sentNotice.sendSystem(getUserImId(hwOrder.getCicerone_id()), "新的订单通知", "您有一个新的订单", null, 8, hwOrder.getOrder_id()+"", null);
		}else {
			sentNotice.sendSystemSms(getBureauPhone(hwOrder.getBureau_id()), SMSNotice.bureauorder.smsTypeTemplateNumber, null);
		}
		
		//给用户发通知
		if (type==1){
			
			sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "支付通知", "您有一笔订单支付成功", null, 2, null, JsonUtils.Bean2Json(threePay));
		}else {
			
			sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "支付通知", "您有一笔订单支付成功", null, 2, null, JsonUtils.Bean2Json(threePay));
		}
	}

	/**
	 * 第三方支付
	 * @param hwOrder
	 * @param hxOrderInfo
	 * @param type   - 1-微信支付，2-支付宝充值
	 * @param payType
	 * @return
	 * @throws Exception
	 */
	private HxUserWalletBill threePay(HwOrder hwOrder, HxOrderInfoVo hxOrderInfo, int type, PayType payType) throws Exception {
		Long user_id = hwOrder.getUser_id();
		HxUserWallet hxUserWallet = hxUserWalletMapper.selectByUserId(user_id);
		
		hwOrder.setStatus(1);
		hwOrder.setPayment_time(System.currentTimeMillis());
		hwOrder.setPaymen_type(3);
		//hxUserWalletMapper.safeBalanceByUserId(user_id, payment);
		HxUserWalletBill createBill = createBill(user_id, hxOrderInfo.getDec(), hxOrderInfo.getDec(), 2, hwOrder.getOrder_num(), new SequenceFactoryBean2().getObject(),
				"订单支付", hwOrder.getPayment(),  hxUserWallet.getBalance(), "111111111", 0, 1);
		
		return createBill;
	}

	/**
	 * 充值
	 * @param out_trade_no  订单编号
	 * @param payType
	 * @param type
	 * @throws Exception 
	 */
	private void payRecharge(String out_trade_no, PayType payType, int type,int success) throws Exception {
		HxUserWalletBill selectByTrans_num = hxUserWalletBillMapper.selectByTrans_num(out_trade_no);
		if (selectByTrans_num.getIs_success()==1){
			System.out.println("已经充值成功");
			return ;
		}
		Long user_id = selectByTrans_num.getName_id();
		HxUserWallet wallet = hxUserWalletMapper.selectByUserId(user_id);
		if(success==1){
			//查询钱包
			hxUserWalletMapper.addBalanceByUserId(user_id, selectByTrans_num.getOperation_amount());
			selectByTrans_num.setBalance(ZZBigDecimalUtils.safeAdd(selectByTrans_num.getOperation_amount(), wallet.getBalance()));
		} else {
			selectByTrans_num.setBalance(wallet.getBalance());
		}
		 
		//账单
		
		selectByTrans_num.setIs_success(success);
		selectByTrans_num.setEnd_time(System.currentTimeMillis());
		hxUserWalletBillMapper.updateByPrimaryKeySelective(selectByTrans_num);
		
		
		
		//通知
		//sentNotice.sendSystem(getUserImId(selectByTrans_num.getName_id()), "充值", "充值", null, 2, null, JsonUtils.Bean2Json(selectByTrans_num));
	}

	@Override
	@Transactional
	public Object wallet_redORTransfer(Long user_id, String pay_body, String payment_password, String smsVerify,
			Integer pay_type) throws Exception {
		
		long currentTimeMillis = System.currentTimeMillis();
		Map<String, Object> json2Map = JsonUtils.json2Map(pay_body);
		if (pay_type==1){
			
			if (json2Map.get("red_packet_id")==null){
				throw new BaseException("红包id必传！");
			} 
			Long red_packet_id = Long.parseLong(json2Map.get("red_packet_id").toString());
			HxUserRedPacket selectByPrimaryKey = hxUserRedPacketMapper.selectByPrimaryKey(red_packet_id);
			if (selectByPrimaryKey==null){
				throw new BaseException("红包不存在！");
			}
			
			if (selectByPrimaryKey.getIs_pay()==1){
				throw new BaseException("不能重复付款！");
			}
			
			//生成账单
			String trans_num = new SequenceFactoryBean2().getObject();
			
			//零钱支付
			HxUserWalletBill walletBill = pay_redORTransfer(user_id,selectByPrimaryKey.getSend_amount(),payment_password,trans_num,"红包","发了一个红包",null);
			walletBill.setSource(3);
			//发红包
			selectByPrimaryKey.setPay_time(currentTimeMillis);
			selectByPrimaryKey.setIs_pay(1);
			hxUserRedPacketMapper.updateByPrimaryKey(selectByPrimaryKey);
			
			hxUserWalletBillMapper.insertSelective(walletBill);
			
			//发红包
			
			//Long red_packet_id = selectByPrimaryKey.getRed_packet_id();
			
			
			List<String> math = null;
			int is_random = selectByPrimaryKey.getIs_random();
			BigDecimal send_amount = selectByPrimaryKey.getSend_amount();
			int packet_number = selectByPrimaryKey.getPacket_number();
			if (is_random ==1){
				math = RedPacketUtils.math(send_amount, packet_number);
			} else {
				math = new ArrayList<>();
				//每个红包的金额等于总的除以个数
				BigDecimal safeDivide = ZZBigDecimalUtils.safeDivide(send_amount, packet_number, null);
				for (int i = 0; i < packet_number; i++) {
					math.add(safeDivide.toString());
				}
			}
			
			//将数据加入缓存
			String redpacketprefix = HwPrefix.RedPacketPrefix+red_packet_id+"_"+
					selectByPrimaryKey.getGroup_id()+"_"+selectByPrimaryKey.getAccept_user_id();
			//RedisCache.setpar(redpacketprefix, JsonUtils.Bean2Json(math), 24, RedisCache.db10);
			RedisCache.hsetpar(redpacketprefix, null, math, RedORTransferExpirationDate, RedisCache.db10);
			return selectByPrimaryKey;
		
		} else {
			//转账
			if (json2Map.get("transfer_id")==null){
				throw new BaseException("转账id必传！");
			}
			
			//转账方式  1-聊天界面  2-扫码
			//Integer transfer_type = json2Map.get("transfer_type")==null?1:Integer.parseInt( json2Map.get("transfer_type").toString());
			
			
			Long transfer_id = Long.parseLong(json2Map.get("transfer_id").toString());
			HxUserTransfer selectByPrimaryKey = hxUserTransferMapper.selectByPrimaryKey(transfer_id);
			if (selectByPrimaryKey==null){
				throw new BaseException("转账不存在！");
			}
			
			if (selectByPrimaryKey.getIs_pay()==1){
				throw new BaseException("不能重复付款！");
			}
			
			//生成账单
			String trans_num = new SequenceFactoryBean2().getObject();
			
			//零钱支付
			HxUserWalletBill walletBill = pay_redORTransfer(user_id,selectByPrimaryKey.getSend_amount(),payment_password,trans_num,"转账","发起了一次转账",null);
			walletBill.setBill_type(9);
			walletBill.setSource(3);
			//发红包
			selectByPrimaryKey.setPay_time(currentTimeMillis);
			selectByPrimaryKey.setIs_pay(1);
			
			
			
			
			//发红包
			
			//Long red_packet_id = selectByPrimaryKey.getRed_packet_id();
			
			
			if (selectByPrimaryKey.getTransfer_type()==1){
				//shuom z
				BigDecimal send_amount = selectByPrimaryKey.getSend_amount();
				
				
				//将数据加入缓存
				String redpacketprefix = HwPrefix.TransferPrefix+transfer_id+"_"+
						selectByPrimaryKey.getUser_id()+"_"+selectByPrimaryKey.getAccept_user_id();
				RedisCache.setpar(redpacketprefix, send_amount.toString(), RedORTransferExpirationDate, RedisCache.db10);
			} else {
				walletBill.setBill_dec("发起了一次扫码转账");
				//说明是扫码付款，则直接转入收款方
				selectByPrimaryKey.setLinqu_time(currentTimeMillis);
				//生成账单
				String trans_num1 = new SequenceFactoryBean2().getObject();
				HxUserWalletBill robred = robred(selectByPrimaryKey.getAccept_user_id(), 
						selectByPrimaryKey.getSend_amount(), trans_num1, "收款","收到一笔转账",null);
				robred.setSource(4);
				robred.setBill_type(10);
				hxUserWalletBillMapper.insertSelective(robred);
				
				//发送系统消息
				String im_id = hxUserMapper.queryImIdByUserId(selectByPrimaryKey.getAccept_user_id());
				sentNotice.sendSystem(im_id, "收到转账", "扫码收款", null, 2, null, JsonUtils.Bean2Json(robred));
			}

			
			hxUserWalletBillMapper.insertSelective(walletBill);
			
			hxUserTransferMapper.updateByPrimaryKey(selectByPrimaryKey);
			
			
			
			return selectByPrimaryKey;
		}
		
	}
	
	/**
	 * 自动钱包转账
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
	 * 转账，发红包支付
	 * @param user_id
	 * @param send_amount
	 * @param payment_password
	 * @param trans_num
	 * @param title
	 * @return
	 */
	private HxUserWalletBill pay_redORTransfer(Long user_id ,BigDecimal send_amount, String payment_password,String trans_num, String title,String bill_dec,String remarks) {
		HxUserWallet hxUserWallet = hxUserWalletMapper.selectByUserId(user_id);
		
		if (ObjectHelper.isEmpty(hxUserWallet)){
			throw new RuntimeException("余额不足，请充值，或者采用其他方式支付！");
		}
		
		if (ObjectHelper.isEmpty(hxUserWallet.getPayment_password())){
			throw new RuntimeException("请先设置支付密码！");
		}
		if(!hxUserWallet.getPayment_password().equals(ObjectHelper.passWord(payment_password, hxUserWallet.getPayment_salt()))){
			
			throw new RuntimeException("支付密码错误！");
		}
		
		if (hxUserWallet.getIs_can().compareTo(send_amount)==-1){
			throw new RuntimeException("余额不足，请充值，或者采用其他方式支付！");
		}
		
		//扣钱
		int num = hxUserWalletMapper.safeBalanceByUserId(user_id, send_amount);
		if (num!=1){
			throw new RuntimeException("余额不足，请充值，或者采用其他方式支付！");
		}
		
		//hxUserWalletMapper.safeBalanceByUserId(user_id, payment);
		HxUserWalletBill createBill = createBill(user_id, bill_dec, title, 7, null, trans_num,
				remarks , send_amount, ZZBigDecimalUtils.safeSubtract(true, hxUserWallet.getBalance(), send_amount), null, 0, 1);
		
		return createBill;
		
		
	}

	/**
	 * 红包
	 * @param user_id
	 * @param title
	 * @param content
	 * @param is_random
	 * @param group_id
	 * @param accept_user_id
	 * @param send_amount
	 * @param packet_number
	 * @param trans_num
	 * @return
	 */
//	private HxUserRedPacket send(Long user_id, String title, String content, Integer is_random,Long group_id, Long accept_user_id,
//			BigDecimal send_amount,Integer packet_number, String trans_num) {
//		
//		long currentTimeMillis = System.currentTimeMillis();
//		
//		
//		 
//		HxUserRedPacket hxUserRedPacket = new HxUserRedPacket();
//		hxUserRedPacket.setAccept_user_id(accept_user_id);
//		
//		hxUserRedPacket.setContent(content);
//		hxUserRedPacket.setCreat_time(currentTimeMillis);
//		hxUserRedPacket.setGroup_id(group_id);
//		hxUserRedPacket.setLinwan_time(currentTimeMillis);
//		hxUserRedPacket.setPacket_number(packet_number);
//		
//		hxUserRedPacket.setSend_amount(send_amount);
//		hxUserRedPacket.setTitle(title);
//		hxUserRedPacket.setUser_id(user_id);
//		hxUserRedPacket.setTrans_num(trans_num);
//		
//		
//		//返回主键添加
//		hxUserRedPacketMapper.insertBackId(hxUserRedPacket);
//		
//		Long red_packet_id = hxUserRedPacket.getRed_packet_id();
//		
//		
//		List<BigDecimal> math = null;
//		if (is_random==1){
//			math = RedPacketUtils.math(send_amount, packet_number);
//		} else {
//			math = new ArrayList<>();
//			//每个红包的金额等于总的除以个数
//			BigDecimal safeDivide = ZZBigDecimalUtils.safeDivide(send_amount, packet_number, null);
//			for (int i = 0; i < packet_number; i++) {
//				math.add(safeDivide);
//			}
//		}
//		
//		//将数据加入缓存
//		String redpacketprefix = HwPrefix.RedPacketPrefix+red_packet_id+"_"+group_id+"_"+accept_user_id;
//		System.err.println(redpacketprefix);
//		RedisCache.setpar(redpacketprefix, JsonUtils.Bean2Json(math), 24*60*60, RedisCache.db10);
//		return hxUserRedPacket;
//	}
}
