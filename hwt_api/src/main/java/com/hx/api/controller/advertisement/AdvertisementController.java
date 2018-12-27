package com.hx.api.controller.advertisement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.mg.information.vo.HwInformationVo;
import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hx.advertisement.service.hx.AdvertisementService;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.core.utils.ObjectHelper;
import com.hx.user.utils.BaseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 广告
 * @author Administrator
 *
 */
@RestController
@Api(value = "API - AdvertisementController", description = "广告")
public class AdvertisementController {
	
	@Autowired
	private AdvertisementService advertisementService;
	

	/**
	 * 广告点击次数加1
	 */
	@ApiOperation(value = "广告点击次数加1" , notes = "广告点击次数加1", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "ad_id", value = "广告id", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"ad_id"})
	@PostMapping("advertisement/lookAdd")
	public ResultEntity lookAdd(String token,Long ad_id){
		
		advertisementService.lookAdd(ad_id);
		return new ResultEntity();
	}
	
}
