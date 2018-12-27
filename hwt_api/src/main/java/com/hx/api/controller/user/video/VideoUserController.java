package com.hx.api.controller.user.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.user.Vo.HxUseRealVo;
import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.video.vo.HxUserVideoInfoVo;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.user.utils.BaseUtil;
import com.hx.user.video.service.VideoUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "API - VideoUserController", description = "小视频用户操作接口")
@RestController
public class VideoUserController {
	
	@Autowired
	private VideoUserService videoUserService;
	
	/**
	 * 查询自己基本的信息
	 */
	@ApiOperation(value = "查询自己基本的信息", notes = "查询自己基本的信息" , response = HxUserVideoInfoVo.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),


	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "videoUser/query_own",method = RequestMethod.POST)
	public ResultEntity query_own(String token){
		 LoginVo loginVo = BaseUtil.getLoginVo(token);
		 HxUserVideoInfoVo hxUserVideoInfoVo =  videoUserService.query_own(loginVo.getUser_id());
		return new ResultEntity(hxUserVideoInfoVo);
	}
	
	
	/**
	 * 申请发小视频等级
	 */
	@ApiOperation(value = "申请发小视频等级", notes = "申请发小视频等级" , response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "apply_grade", value = "申请的等级  2-中等（60秒） 3-高级（能直播）", dataType = "string",required = true,paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","apply_grade"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "videoUser/grade_apply",method = RequestMethod.POST)
	public ResultEntity grade_apply(String token, Integer apply_grade){
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		videoUserService.grade_apply(loginVo.getUser_id(),apply_grade);
		return new ResultEntity();
	}
	
}
