package com.hx.adminHx.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hx.core.page.asyn.PageResult;
import io.swagger.annotations.ApiModel;

import java.util.Map;

@ApiModel(value = "资金管理后台数量展示")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class HxUserWallAccountPageVo extends PageResult<Map<String, Object>> {
    //订单数量
    private Integer orderNum;
    //充值数量
    private Integer rechargeNum;
    //提现数量
    private Integer drawingsNum;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Integer rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public Integer getDrawingsNum() {
        return drawingsNum;
    }

    public void setDrawingsNum(Integer drawingsNum) {
        this.drawingsNum = drawingsNum;
    }
}
