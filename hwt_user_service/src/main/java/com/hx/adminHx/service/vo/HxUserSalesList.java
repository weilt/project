package com.hx.adminHx.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hx.core.page.asyn.PageResult;
import io.swagger.annotations.ApiModel;

import java.util.Map;

@ApiModel(value = "商家销售展示")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class HxUserSalesList extends PageResult<Map<String, Object>> {

}
