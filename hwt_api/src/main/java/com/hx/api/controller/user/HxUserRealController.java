package com.hx.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.user.HxUseReal;
import com.hwt.domain.entity.user.Vo.HxUseRealVo;
import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.Vo.UserVo;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.user.service.HxUserRealService;
import com.hx.user.utils.BaseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "API - HxUserRealController", description = "用户真实信息操作接口")
@RestController
public class HxUserRealController {
	
	@Autowired
	private HxUserRealService hxUserRealService;
	
	/**
	 * 获取当前用户信息
	 * @param token - 验证登录状态 - Y
	 * @return
	 */
	@ApiOperation(value = "查询自己的真实信息", notes = "查询自己的真实信息" , response = HxUseRealVo.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "userReal/queryOwn",method = RequestMethod.POST)
	public ResultEntity accountInfo(String token){
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		HxUseRealVo hxUseRealVo = hxUserRealService.queryOwn(loginVo.getUser_id());
		
		return new ResultEntity(hxUseRealVo);
		
	}
	
	/**
	 * 添加/修改 自己的真实信息
	 */
	@ApiOperation(value = "添加/修改自己的真实信息", notes = "添加/修改自己的真实信息" , response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "real_name", value = "用户真实姓名", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "identity_no", value = "身份证号", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "identity_positive", value = "身份证正面", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "identity_negative", value = "身份证反面", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "identity_hold", value = "手持证件照", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "present_address", value = "现住址", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "often_phone", value = "常用联系电话", dataType = "string",paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "userReal/insertOrUpdate",method = RequestMethod.POST)
	public ResultEntity insert(String token,String real_name,String identity_no,String identity_positive,
			String identity_negative,String identity_hold,String present_address,String often_phone){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		Long user_id = loginVo.getUser_id();
		long currentTimeMillis = System.currentTimeMillis();
		HxUseReal hxUseReal = new HxUseReal();
		hxUseReal.setCreate_time(currentTimeMillis);
		hxUseReal.setIdentity_hold(identity_hold);
		hxUseReal.setIdentity_negative(identity_negative);
		hxUseReal.setIdentity_no(identity_no);
		hxUseReal.setIdentity_positive(identity_positive);
		hxUseReal.setOften_phone(often_phone);
		hxUseReal.setPresent_address(present_address);
		hxUseReal.setReal_name(real_name);
		hxUseReal.setUpdate_time(currentTimeMillis);
		hxUseReal.setUser_id(user_id);
		
		hxUserRealService.insertOrUpdate(user_id,hxUseReal);
		
		return new ResultEntity();
		
	}
	
}
