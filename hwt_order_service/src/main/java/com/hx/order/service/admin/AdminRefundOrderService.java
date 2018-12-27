package com.hx.order.service.admin;

import com.hwt.domain.entity.order.vo.OrderDetailsVo;
import com.hx.order.service.admin.vo.AdminPageOrder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface AdminRefundOrderService {
    /**
     * 查询线路
     * @param map
     * @return
     */
    AdminPageOrder selectQuery(Map<String, Object> map) throws Exception;

    /**
     * 查询导游
     * @param map
     * @return
     */
    AdminPageOrder selectGuideQuery(Map<String, Object> map) throws Exception;


    //退款订单详情
    Map<String,Object>  selectQureyDetails(Long order_id);
    /**
     * 同意退款
     * @param order_id
     * @param contract_money
     */
    List consent(Long order_id, BigDecimal contract_money, String operator) throws Exception;

    /**
     * 拒绝退款
     * @param order_id
     * @param refund_cause
     */
    void refused(Long order_id, String refund_cause ,String operator) throws Exception;
}
