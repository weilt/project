package com.hx.adminHx.service.vo;

import com.hx.core.page.asyn.PageResult;
import io.swagger.annotations.ApiModel;

import java.util.Map;

@ApiModel(value = "首页数量展示")
public class AdminHome extends PageResult<Map<String, Object>> {

    //今日新增
    private Integer today;

    //昨日新增
    private Integer yesterday;

    //本月新增
    private Integer month;

    //用户总数
    private Integer userCount;

    //商家入驻审核 数量
    // 显示数量为旅行社与导游待处理数量之和
    private Integer merchantCount;

    //退款申请 数量
    //显示数量为线路退款待处理的数量
    private Integer refundCount;

    //小视频审核
    // 显示是待审核数量
    private Integer videoCount;

    //提现申请
    //显示数量为提现未对账数量
    private Integer depositCount;

    //平台今日订单数
    //支付成功的订单数量（导游+线路）
    private Integer orderCount;

    //今日导游订单数
    private Integer orderGuideCount;

    //今日导线路单数
    private Integer orderCircuitCount;

    //平台今日销售总额
    //今日订单数量入账金额总和
    private Double todayAmount;

    //平台昨日销售总额
    //昨天订单数量入账金额总和
    private Double yesterdayAmount;

    //近7天订单入账总金额
    private Double hebdomadAmount;

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getYesterday() {
        return yesterday;
    }

    public void setYesterday(Integer yesterday) {
        this.yesterday = yesterday;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(Integer merchantCount) {
        this.merchantCount = merchantCount;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getDepositCount() {
        return depositCount;
    }

    public void setDepositCount(Integer depositCount) {
        this.depositCount = depositCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getOrderGuideCount() {
        return orderGuideCount;
    }

    public void setOrderGuideCount(Integer orderGuideCount) {
        this.orderGuideCount = orderGuideCount;
    }

    public Integer getOrderCircuitCount() {
        return orderCircuitCount;
    }

    public void setOrderCircuitCount(Integer orderCircuitCount) {
        this.orderCircuitCount = orderCircuitCount;
    }

    public Double getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(Double todayAmount) {
        this.todayAmount = todayAmount;
    }

    public Double getYesterdayAmount() {
        return yesterdayAmount;
    }

    public void setYesterdayAmount(Double yesterdayAmount) {
        this.yesterdayAmount = yesterdayAmount;
    }

    public Double getHebdomadAmount() {
        return hebdomadAmount;
    }

    public void setHebdomadAmount(Double hebdomadAmount) {
        this.hebdomadAmount = hebdomadAmount;
    }
}
