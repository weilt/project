package com.hx.order.service.admin.vo;

import com.hx.core.page.asyn.PageResult;

import java.util.Map;

/**
 * 订单列表
 *
 * @author Administrator
 */
public class AdminPageOrder extends PageResult<Map<String, Object>> {


    private Long status0;//全部订单

    private Long status1;//待确认

    private Long status2;//待开始

    private Long status3;//已完成

    private Long status4;//退款

    private Long status5;//退款完成

    private Long status6;//拒绝

    private Long status7;//失败

    private Long status8;//已关闭

    public Long getStatus7() {
        return status7;
    }

    public void setStatus7(Long status7) {
        this.status7 = status7;
    }

    public Long getStatus8() {
        return status8;
    }

    public void setStatus8(Long status8) {
        this.status8 = status8;
    }

    public Long getStatus6() {
        return status6;
    }

    public void setStatus6(Long status6) {
        this.status6 = status6;
    }

    public Long getStatus0() {
        return status0;
    }

    public void setStatus0(Long status0) {
        this.status0 = status0;
    }

    public Long getStatus1() {
        return status1;
    }

    public void setStatus1(Long status1) {
        this.status1 = status1;
    }

    public Long getStatus2() {
        return status2;
    }

    public void setStatus2(Long status2) {
        this.status2 = status2;
    }

    public Long getStatus3() {
        return status3;
    }

    public void setStatus3(Long status3) {
        this.status3 = status3;
    }

    public Long getStatus4() {
        return status4;
    }

    public void setStatus4(Long status4) {
        this.status4 = status4;
    }

    public Long getStatus5() {
        return status5;
    }

    public void setStatus5(Long status5) {
        this.status5 = status5;
    }

}
