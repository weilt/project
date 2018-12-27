package com.hx.order.service.admin.impl;

import com.hwt.domain.entity.order.HwOrder;
import com.hwt.domain.entity.order.HwOrderRefund;
import com.hwt.domain.entity.order.vo.*;
import com.hwt.domain.entity.user.wallet.HxUserWallet;
import com.hwt.domain.entity.user.wallet.HxUserWalletBill;
import com.hwt.domain.mapper.bureau.HwTravelBureauMapper;
import com.hwt.domain.mapper.order.HwOrderMapper;
import com.hwt.domain.mapper.order.HwOrderRefundMapper;
import com.hwt.domain.mapper.order.HxOrderInfoMapper;
import com.hwt.domain.mapper.order.HxOrderMapper;
import com.hwt.domain.mapper.user.HxUserMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletBillMapper;
import com.hwt.domain.mapper.user.wallet.HxUserWalletMapper;
import com.hx.core.mongo.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.SequenceFactoryBean2;
import com.hx.core.utils.ZZBigDecimalUtils;
import com.hx.core.wyim.entity.emu.SMSNotice;
import com.hx.core.wyim.notice.SentNotice;
import com.hx.order.service.admin.AdminRefundOrderService;
import com.hx.order.service.admin.vo.AdminPageOrder;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class AdminRefundOrderServiceImpl implements AdminRefundOrderService {
    @Autowired
    private HxOrderMapper hxOrderMapper;
    @Autowired
    private HwOrderMapper hwOrderMapper;
    @Autowired
    private HxUserWalletMapper hxUserWalletMapper;
    @Autowired
    private HxOrderInfoMapper hxOrderInfoMapper;
    @Autowired
    private SentNotice sentNotice;
    @Autowired
    private HxUserMapper hxUserMapper;
    @Autowired
    private HxUserWalletBillMapper hxUserWalletBillMapper;
    @Autowired
    private HwOrderRefundMapper hwOrderRefundMapper;
    @Autowired
    private HwTravelBureauMapper hwTravelBureauMapper;
//    @Autowired
//    private HxOrderMapper hxOrderMapper;

    /**
     * 查询线路订单
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public AdminPageOrder selectQuery(Map<String, Object> map) throws Exception {


        //待退款
        Long status4 = 0l;

        //退款完成
        Long status5 = 0l;

        //已完成
        Long status6 = 0l;

        //全部订单
        Long status0 = 0l;

        List<HwOrder> hwOrders = hxOrderMapper.RefundSelectGuideQuery(map);
        if (!ObjectHelper.isEmpty(hwOrders)){
            status0 = (long) hwOrders.size();
            for (HwOrder hwOrder : hwOrders) {
                if (hwOrder.getStatus()==4){
                    status4++;
                }
                if (hwOrder.getStatus()==5){//&&System.currentTimeMillis()<hwOrder.getStart_time()
                    status5++;
                }
                if(hwOrder.getStatus()==6){//&&System.currentTimeMillis()>=hwOrder.getStart_time()
                    status6++;
                }
            }
        }

        //满足条件的总条数
       Long count = hxOrderMapper.selectQueryCountByMap2(map);

        //满足条件的数据
        List<Map<String, Object>> list = hxOrderMapper.RefundSelectQueryByMap(map);

        AdminPageOrder adminPageOrder = new AdminPageOrder();
        adminPageOrder.setCount(count);
        adminPageOrder.setDataList(list);
        adminPageOrder.setStatus0(status0);
        adminPageOrder.setStatus4(status4);
        adminPageOrder.setStatus5(status5);
        adminPageOrder.setStatus6(status6);

        return adminPageOrder;
    }

    /**
     * 查询导游订单
     * @param map
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @Override
    public AdminPageOrder selectGuideQuery(Map<String, Object> map) throws ParseException, Exception {

        //待退款
        Long status4 = 0l;

        //退款完成
        Long status5 = 0l;

        //已完成
        Long status6 = 0l;

        //全部订单
        Long status0 = 0l;

        List<HwOrder> hwOrders = hxOrderMapper.RefundSelectGuideQuery(map);
        if (!ObjectHelper.isEmpty(hwOrders)){
            status0 = (long) hwOrders.size();
            for (HwOrder hwOrder : hwOrders) {
                if (hwOrder.getStatus()==4){
                    status4++;
                }
                if (hwOrder.getStatus()==5){//&&System.currentTimeMillis()<hwOrder.getStart_time()
                    status5++;
                }
                if(hwOrder.getStatus()==6){//&&System.currentTimeMillis()>=hwOrder.getStart_time()
                    status6++;
                }
            }
        }
        //满足条件的总条数
        Long count = hxOrderMapper.selectQueryCountByMap2(map);

        //满足条件的数据
        List<Map<String, Object>> list = hxOrderMapper.RefundSelectGuideQueryByMap(map);

        AdminPageOrder adminPageOrder = new AdminPageOrder();
       adminPageOrder.setCount(count);
        adminPageOrder.setDataList(list);
        adminPageOrder.setStatus0(status0);
        adminPageOrder.setStatus4(status4);
        adminPageOrder.setStatus5(status5);
        adminPageOrder.setStatus6(status6);

        return adminPageOrder;
    }

    @Override
    public Map<String,Object>  selectQureyDetails(Long order_id) {
        Map<String,Object>  orderDetailsVo = new HashMap<>();
        //基本信息
        HwOrderVo hwOrderVo = hxOrderMapper.selectByorder_id(order_id);
        if(hwOrderVo == null){
            throw new RuntimeException("该订单不存在");
        }
        orderDetailsVo.put("hwOrderVo",hwOrderVo);
        //商品信息
        HxOrderInfoVo hxOrderInfoVo = hxOrderMapper.selectQueryByOrderInfo(order_id);
        orderDetailsVo.put("hxOrderInfoVo",hxOrderInfoVo);
        //说明存在退款情况
        HwOrderRefundVo hwOrderRefundVo = hxOrderMapper.selectQueryByOrderRefund(order_id);
         orderDetailsVo.put("hwOrderRefundVo",hwOrderRefundVo);
        //用户信息 保险信息
        List<HwOrderUserVo> hwOrderUserVos =  hxOrderMapper.selectQueryListByOrderUser(order_id);
        orderDetailsVo.put("hwOrderUserVos",hwOrderUserVos);

        String order_num = hwOrderVo.getOrder_num();

        String trans_num =hxOrderMapper.queryByBill_type(order_num);
        //根据商家或导游号查询卖家名称
        Long id = 0l;
        String name =null;
        if (hwOrderVo.getBureau_id() ==0){
            //导游
            id = hwOrderVo.getCicerone_id();
            name = hxOrderMapper.selectByCiceroneName(id);
        }else {
            //旅行社
            id = hwOrderVo.getBureau_id();
            name = hxOrderMapper.selectByBureauName(id);
        }

        orderDetailsVo.put("name",name);
        orderDetailsVo.put("trans_num",trans_num);

        return orderDetailsVo;
    }
    // date要转换的long类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    @Override
    public List consent(Long order_id, BigDecimal contract_money,String operator) throws Exception {
        HwOrder hwOrder = hwOrderMapper.selectByPrimaryKey(order_id);
        if (hwOrder.getStatus() != 4){
                throw new RuntimeException("订单状态异常 不可退款");
        }
        HxUserWallet hxUserWallet = hxUserWalletMapper.selectByBureau_id(hwOrder.getBureau_id());
        Long succeed = getInteger(order_id, contract_money, operator);
        //0-成功 1-失败
        List list = null;
        if (succeed == 1l){
            //修改操作 清空商家余额
            hxUserWalletMapper.updateByEmpty(hwOrder.getBureau_id());
            list.add(hwOrder.getBureau_id());
            list.add(hxUserWallet.getBalance());
            try {
                throw new RuntimeException("违约金为"+contract_money+"元,请联系ID为"+hwOrder.getBureau_id()+
                        "的商家 对方的余额不足 已扣除对方金额"+hxUserWallet.getBalance()+"元");
            }catch (Exception e){
                throw new RuntimeException("违约金为"+contract_money+"元,请联系ID为"+hwOrder.getBureau_id()+
                        "的商家 对方的余额不足 已扣除对方金额"+hxUserWallet.getBalance()+"元");
            }finally {
                return list;//商家id
            }

        }
        return list;//0
    }
    @Transactional
    public Long getInteger(Long order_id, BigDecimal contract_money, String operator) throws Exception {
        //基本信息
        HwOrder hwOrder = hwOrderMapper.selectByPrimaryKey(order_id);
        HwOrderRefund hwOrderRefund = hwOrderMapper.selectByOrderRefund(order_id);
        if (hwOrder == null) {
            throw new RuntimeException("该订单不存在");
        }

        //当前时间
        Date date = new Date();
        long time_long = dateToLong(date);
        String refund_dec = "全额退款";
        String breaksum = "根据违约条款进行退款";
        Long consent_time = time_long;
        Long refund_time = time_long;
        //不为null 为旅行社发起的退款
        if (StringUtils.isNoneBlank(hwOrderRefund.getBusiness_remarks())) {
            if (contract_money.longValue() == 0) {
                //给用户添加钱
                hxUserWalletMapper.addBalanceById(hwOrder.getUser_id(), hwOrder.getPayment(), 1);
                //设置参数
                HxUserWalletBill insertBill = insertBill(hwOrder, hwOrder.getPayment(), 2, 4, "全额退款", 1, 1, null);
                insertBill.setEnd_time(time_long);
                insertBill.setSource(3);
                //插入参数
                hxUserWalletBillMapper.insertSelective(insertBill);
                //修改订单表中的数据
                hxOrderMapper.updateByOrder(order_id, time_long);
                //修改退款表中的数据                                                       退款描述     拒绝时间        操作人    同意退款时间   违约金              拒绝退款原因
                hxOrderMapper.updateByOrderRefund(order_id, hwOrder.getPayment(), 1, refund_dec, null, operator, consent_time, null, null);
                sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "订单退款通知", "您有一个线路订单已被取消", null, 2, null, JsonUtils.Bean2Json(insertBill));

            }
            //有违约金 根据违约条款退款
            if (contract_money.longValue() != 0) {
                long value = hwOrder.getPayment().longValue() + contract_money.longValue();
                //给商家添加违约金bureau_id
                hxUserWalletMapper.updateByBureau_id(hwOrder.getBureau_id(), contract_money);
                //给用户添加钱 加违约金赔偿
                hxUserWalletMapper.addBalanceByBreakId(hwOrder.getUser_id(), new BigDecimal(value), BigDecimal.valueOf(0), 1);
                //设置参数
                HxUserWalletBill insertBill = insertBill(hwOrder, new BigDecimal(value), 2, 4, "根据违约条款退款", 1, 1, null);
                insertBill.setEnd_time(time_long);
                insertBill.setSource(3);
                //插入参数
                hxUserWalletBillMapper.insertSelective(insertBill);
                //修改订单表中的数据
                hxOrderMapper.updateByOrder(order_id, time_long);
                //修改退款表中的数据                                                  退款描述       拒绝时间        操作人    同意退款时间  违约金          拒绝退款原因
                hxOrderMapper.updateByOrderRefund(order_id, new BigDecimal(value), 1, breaksum, null, operator, consent_time, contract_money, null);
                sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "订单退款通知", "您有一个线路订单已被取消", null, 2, null, JsonUtils.Bean2Json(insertBill));

                //查询商家的钱是不是够扣除违约金用
                HxUserWallet hxUserWallet = hxUserWalletMapper.selectByBureau_id(hwOrder.getBureau_id());
                if ((hxUserWallet.getIs_not_can().longValue() - contract_money.longValue()) >= 0) {
                    //冻结金额够用
                    hxUserWalletMapper.updateByButton(hwOrder.getBureau_id(), contract_money);
                } else if ((hxUserWallet.getIs_can().longValue() - contract_money.longValue()) >= 0) {//冻结金额不够用 判断可用余额
                    //可用金额够用
                    hxUserWalletMapper.updateByButton2(hwOrder.getBureau_id(), contract_money);
                } else if ((hxUserWallet.getBalance().longValue() - contract_money.longValue()) >= 0) { //可用金额不够用 判断总余额
                    //总余额够用
                    //剩余的总金额 = 可用金额  走到这一步说明 冻结金额不够(先扣冻结金额)
                    Long bigMoney = hxUserWallet.getBalance().longValue() - contract_money.longValue();

                    hxUserWalletMapper.updateByButton3(hwOrder.getBureau_id(), new BigDecimal(bigMoney),contract_money);
                } else {
                    return 1l;
//                    try {
//                        throw new RuntimeException("请联系ID为"+hwOrder.getBureau_id()+"的商家 对方的余额不足 不能扣除对方违约金");
//                    }catch (Exception e){
//                        throw new RuntimeException("请联系ID为"+hwOrder.getBureau_id()+"的商家 对方的余额不足 不能扣除对方违约金");
//                    }finally {
//                        hxUserWalletMapper.
//
//                    }
                }
            }
        } else {//为null 就是用户发起的退款
            //无违约金 同意退款
            if (contract_money.longValue() == 0 ||contract_money == null) {

                hxUserWalletMapper.addBalanceById(hwOrder.getUser_id(), hwOrder.getPayment(), 1);
                //设置参数
                HxUserWalletBill insertBill = insertBill(hwOrder, hwOrder.getPayment(), 2, 4, "全额退款", 1, 1, null);
                insertBill.setEnd_time(time_long);
                insertBill.setSource(3);
                //插入参数
                hxUserWalletBillMapper.insertSelective(insertBill);
                //修改订单表中的数据
                hxOrderMapper.updateByOrder(order_id, time_long);
                //修改退款表中的数据                                                       退款描述     拒绝时间        操作人    同意退款时间   违约金              拒绝退款原因
                hxOrderMapper.updateByOrderRefund(order_id, hwOrder.getPayment(), 1, refund_dec, null, operator, consent_time, null, null);
                sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "订单退款通知", "您有一笔退款金额已到账", null, 2, null, JsonUtils.Bean2Json(insertBill));


//                //给商家发通知
//                if(hwOrder.getCicerone_id()!=0){
//                    //导游
//                    sentNotice.sendSystem(getUserImId(hwOrder.getCicerone_id()), "订单", "有人向你取消下单", null, 1, hwOrder.getOrder_id()+"", com.hx.core.utils.JsonUtils.Bean2Json(insertBill));
//                }else {
//                    //旅行社
//                    sentNotice.sendSystemSms(getBureauPhone(hwOrder.getBureau_id()), SMSNotice.bureauordercancel.smsTypeTemplateNumber, null);
//                }
            }
            //有违约金 根据违约条款退款
            if (contract_money.longValue() != 0) {
                long value = hwOrder.getPayment().longValue() - contract_money.longValue();
                //给用户退款
                hxUserWalletMapper.addBalanceByBreakId(hwOrder.getUser_id(), new BigDecimal(value), contract_money, 1);
                //给商家 违约金赔偿
                hxUserWalletMapper.addBalanceByBreakId(hwOrder.getBureau_id(),contract_money, BigDecimal.valueOf(0),2);
                //设置参数
                HxUserWalletBill insertBill = insertBill(hwOrder, new BigDecimal(value), 2, 4, "根据违约条款退款", 1, 1, null);
                insertBill.setEnd_time(time_long);
                insertBill.setSource(3);
                //插入参数
                hxUserWalletBillMapper.insertSelective(insertBill);
                //修改订单表中的数据
                hxOrderMapper.updateByOrder(order_id, time_long);
                //修改退款表中的数据                                                        退款描述     拒绝时间      操作人    同意退款时间         违约金            拒绝退款原因
                hxOrderMapper.updateByOrderRefund(order_id, new BigDecimal(value), 1, breaksum, null, operator, consent_time, contract_money, null);
                sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "订单退款通知", "您有一笔退款金额已到账", null, 2, null, JsonUtils.Bean2Json(insertBill));

//                if(hwOrder.getCicerone_id()!=0){
//                    //导游
//                    sentNotice.sendSystem(getUserImId(hwOrder.getCicerone_id()), "订单", "有人向你取消下单", null, 8, hwOrder.getOrder_id()+"", com.hx.core.utils.JsonUtils.Bean2Json(insertBill));
//                }else {
//                    //旅行社
//                    sentNotice.sendSystemSms(getBureauPhone(hwOrder.getBureau_id()), SMSNotice.bureauordercancel.smsTypeTemplateNumber, null);
//                }
            }
        }
        return 0l;
    }

    /**
     * 拒绝订单退款
     * @param order_id
     * @param refund_cause 退款原因
     */
    @Override
    public void refused(Long order_id, String refund_cause ,String operator) throws Exception{
        //基本信息
        HwOrder hwOrder = hwOrderMapper.selectByPrimaryKey(order_id);
        if(hwOrder == null){
            throw new RuntimeException("该订单不存在");
        }
        if (hwOrder.getStatus() != 4) {
            throw new RuntimeException("该订单不可做拒绝超作！");
        }
            //当前时间
            Date date = new Date();
            long time_long=dateToLong(date);
            Long refund_time_back = time_long;
            //拒绝退款 修改order表
            hxOrderMapper.updateByrefuseOrder(order_id,time_long);
            //拒绝退款 修改refund表
//        @Param("order_id")Long order_id,
//        @Param("refund_sum") BigDecimal refund_sum,
//        @Param("i") int i,
//        @Param("refund_dec") String refund_dec,
//        @Param("refund_time")Long refund_time,
//        @Param("operator")String operator,
//        @Param("consent_time")Long consent_time,
//        @Param("contract_money")BigDecimal contract_money,
//        @Param("refund_cause")String refund_cause);
            hxOrderMapper.updateByOrderRefund(order_id,hwOrder.getPayment(), 0,refund_cause ,refund_time_back , operator ,null,null ,refund_cause);
            //拒绝退款 插入账单
            HxUserWalletBill insertBill = insertBill(hwOrder, hwOrder.getPayment(), 2, 4, refund_cause, 0, 2,refund_cause);
            insertBill.setEnd_time(time_long);
            insertBill.setSource(3);
            //插入参数
            hxUserWalletBillMapper.insertSelective(insertBill);
            sentNotice.sendSystem(getUserImId(hwOrder.getUser_id()), "订单被拒绝退款", String.valueOf(refund_cause), null, 1, null, null);
    }

    /**
     * 账单
     * @param hwOrder		订单
     * @param operation_amount		操作金额
     * @param is_wallet		是否是钱包操作  1-否 2-是  默认1
     * @param bill_type		1-充值  2-支付 3-收入 4-退款 5-提现  6-违约金收入
     * @param remarks		备注
     * @param is_get		是否进账  0-否 1- 是
     * @param is_success	是否成功  0-待处理 1-是 2-失败
     * @param failed_reason 失败原因
     * @throws Exception
     */
    private HxUserWalletBill insertBill(HwOrder hwOrder, BigDecimal operation_amount, Integer is_wallet, Integer bill_type , String remarks, Integer is_get, Integer is_success ,String failed_reason) throws Exception{
        HxOrderInfoVo hxOrderInfo = hxOrderInfoMapper.query_by_order_id(hwOrder.getOrder_id());
        HxUserWallet hxUserWallet = hxUserWalletMapper.selectByUserId(hwOrder.getUser_id());
        if (is_get==1&&is_wallet==2){
            hxUserWallet.setBalance(ZZBigDecimalUtils.safeAdd(hxUserWallet.getBalance(), operation_amount));
        }
        if (is_get==0&&is_wallet==2){
            if (hxUserWallet.getIs_can().compareTo(operation_amount)==-1){
                throw new RuntimeException("余额不足");
            }
            hxUserWallet.setBalance(ZZBigDecimalUtils.safeSubtract(true,hxUserWallet.getBalance(), operation_amount));
        }
        HxUserWalletBill createBill = createBill(hwOrder.getUser_id(), hxOrderInfo.getDec(), hxOrderInfo.getDec(), bill_type, hwOrder.getOrder_num(), new SequenceFactoryBean2().getObject(),
                remarks, operation_amount, hxUserWallet.getBalance(), null, is_get, is_success,failed_reason);
        return createBill;

    }
    /**
     * 创建账单
     * @param user_id    用户id
     * @param bill_dec		描述
     * @param bill_title	说明
     * @param bill_type		1-充值  2-支付 3-收入 4-退款 5-提现  6-违约金收入
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
                                        String trans_num,String remarks,BigDecimal operation_amount,BigDecimal balance, String flow_num, Integer is_get,Integer is_success , String failed_reason){
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
        hxUserWalletBill.setFailed_reason(failed_reason);
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
}
