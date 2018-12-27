package com.hx.adminHx.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hx.core.page.asyn.PageResult;
import io.swagger.annotations.ApiModel;

import java.util.Map;

@ApiModel(value = "小视频后台数量展示")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class HxUserVideoPage extends PageResult<Map<String, Object>> {
//审核状态 默认1 未审核 2-通过 3-未通过' count是总数

    private Integer status0;

    private Integer status1;

    private Integer status2;

    private Integer status3;

    public Integer getStatus0() {
        return status0;
    }

    public void setStatus0(Integer status0) {
        this.status0 = status0;
    }

    public Integer getStatus1() {
        return status1;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public Integer getStatus3() {
        return status3;
    }

    public void setStatus3(Integer status3) {
        this.status3 = status3;
    }

}
