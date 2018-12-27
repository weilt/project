package com.hx.api.controller.sts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hx.api.aliyun.file.app.APPUpload;
import com.hx.api.aliyun.file.app.AliyunAPPSTS;
import com.hx.api.base.ResultCode;
import com.hx.api.base.ResultEntity;
import com.hx.api.tencent.file.TecentAPPSTS;
import com.hx.api.tencent.file.TecentUpload;
import com.hx.api.tencent.file.video.Signature;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "API - STSController", description = "临时文件上传token获取")
@RestController
public class STSController {
	/**
	 * 获取图片上传token
	 * @param token - 验证登录状态 - Y
	 * @return
	 */
	@ApiOperation(value = "获取阿里图片上传token", notes = "获取阿里图片上传token", response = AliyunAPPSTS.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
	})
	
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "account/getAppToken",method = RequestMethod.POST)
	public ResultEntity getAppToken(){
		
			AliyunAPPSTS token = APPUpload.getToken();
			return new ResultEntity(token);
		
	}
	
	/**
	 * 获取腾讯的临时文件上传token
	 * @throws Exception 
	 */
	@ApiOperation(value = "获取腾讯的临时文件上传token", notes = "获取腾讯的临时文件上传token", response = TecentAPPSTS.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
	})
	
	//@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "account/getAppTencentToken",method = RequestMethod.GET)
	public ResultEntity getAppTencentToken() throws Exception {
		String str = RedisCache.get(HwPrefix.TencentVODPrefix);
		
		if (ObjectHelper.isEmpty(str)){
			Map<String, Object> map = new HashMap<>();
			map.put("signa", Signature.getUploadSignature());
			
			//缓存20分钟
			RedisCache.set(HwPrefix.TencentVODPrefix, JsonUtils.Bean2Json(map), 20*60*1000);
			return new ResultEntity(map);
		} else {
			Map<String, Object> map = JsonUtils.json2Map(str);
			return new ResultEntity(map);
		}
		
		
	}
}
