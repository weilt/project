package com.hx.api.controller.information;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.information.HwInformationComment;
import com.hwt.domain.entity.mg.information.vo.HwInformationBaseVo;
import com.hwt.domain.entity.mg.information.vo.HwInformationVo;
import com.hwt.domain.entity.mg.travel.line.vo.HwTravelLineDetailsVo;
import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.core.utils.ObjectHelper;
import com.hx.information.service.hx.InformationService;
import com.hx.user.utils.BaseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 资讯
 * @author Administrator
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(value = "API - InformationController", description = "资讯")
public class InformationController {
	
	@Autowired
	private InformationService informationService;
	
	
	/**
	 * 资讯查询
	 * @param token
	 * @param pageSize
	 * @param startNum
	 * @param orderBy
	 * @return
	 */
	@ApiOperation(value = "资讯查询" , notes = "资讯查询", response = HwInformationBaseVo.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query"),
    	@ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "string",paramType = "query"),
    	@ApiImplicitParam(name = "startNum", value = "最后一条记录，默认0", dataType = "string",paramType = "query"),
    	@ApiImplicitParam(name = "orderBy", value = "排序", dataType = "string",paramType = "query"),
    })
	@ValidateParam(field={"token"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/query")
	public ResultEntity qurey(String token,@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "0",name="startNum") Long startNum, String orderBy){
		Map<String, Object> map = new HashMap<>();
		map.put("pageSize", pageSize);
		map.put("startNum", startNum);
		map.put("orderBy", orderBy);
		List<Map<String, Object>> list = informationService.qurey(map);
		
		return new ResultEntity(list);
		
	}
	
	/**
	 * 查看详情
	 */
	@ApiOperation(value = "查看详情" , notes = "查看详情", response = HwInformationVo.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "information_id", value = "资讯id", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"information_id"})
	@PostMapping("information/queryInfo")
	public ResultEntity qureyInfo(String token,Long information_id){
		Long user_id = null ;
		if (!ObjectHelper.isEmpty(token)){
			LoginVo loginVo = BaseUtil.getLoginVo(token);
			user_id = loginVo.getUser_id();
		}
		Map<String, Object> map = informationService.qureyInfo(user_id,information_id);
		return new ResultEntity(map);
	}
	
	/**
	 * 资讯点赞 
	 */
	@ApiOperation(value = "资讯点赞 " , notes = "资讯点赞 ", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "information_id", value = "资讯id", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"token","information_id"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/information_good")
	public ResultEntity information_good(String token,Long information_id){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		informationService.information_good(loginVo.getUser_id(),information_id);
		return new ResultEntity();
	}
	
	/**
	 * 资讯取消赞
	 */
	@ApiOperation(value = "资讯取消赞" , notes = "资讯取消赞", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "information_id", value = "资讯id", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"token","information_id"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/information_not_good")
	public ResultEntity information_not_good(String token,Long information_id){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		informationService.information_not_good(loginVo.getUser_id(),information_id);
		return new ResultEntity();
	}
	
	/**
	 * 资讯评论
	 */
	@ApiOperation(value = "资讯评论" , notes = "资讯评论", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "information_id", value = "资讯id", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "parent_user_id", value = "恢复的谁  0-不是", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "parent_comment_id", value = "回复的哪一条  0-不是", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "content", value = "content", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"token","information_id"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/information_comment_insert")
	public ResultEntity information_comment_insert(String token,Long information_id,Long parent_user_id,Long parent_comment_id, String content){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		HwInformationComment hwInformationComment = informationService.information_comment_insert(loginVo.getUser_id(),information_id,
				parent_user_id,parent_comment_id,content);
		
		return new ResultEntity(hwInformationComment);
	}
	
	/**
	 * 删除评论
	 */
	@ApiOperation(value = "资讯评论" , notes = "资讯评论", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "comment_id", value = "回复的哪一条  0-不是", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"token","comment_id"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/information_comment_delete")
	public ResultEntity information_comment_delete(String token,Long comment_id){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		informationService.information_comment_delete(loginVo.getUser_id(),comment_id);
		
		return new ResultEntity();
	}
	
	/**
	 * 赞评论
	 */
	@ApiOperation(value = "赞评论" , notes = "赞评论", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "comment_id", value = "回复的哪一条  0-不是", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"token","comment_id"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/information_comment_good")
	public ResultEntity information_comment_good(String token,Long comment_id){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		informationService.information_comment_good(loginVo.getUser_id(),comment_id);
		
		return new ResultEntity();
	}
	
	/**
	 * 赞评论 取消
	 */
	@ApiOperation(value = "赞评论" , notes = "赞评论", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "comment_id", value = "回复的哪一条  0-不是", dataType = "string",paramType = "query",required = true),
    })
	@ValidateParam(field={"token","comment_id"}, checkedType = CheckedType.TOKEN)
	@PostMapping("information/information_comment_not_good")
	public ResultEntity information_comment_not_good(String token,Long comment_id){
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		informationService.information_comment_not_good(loginVo.getUser_id(),comment_id);
		
		return new ResultEntity();
	}
	
	/**
	 * 评论查询
	 */
	@ApiOperation(value = "评论查询" , notes = "评论查询", response = ResultEntity.class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query"),
    	@ApiImplicitParam(name = "information_id", value = "资讯id", dataType = "string",paramType = "query",required = true),
    	@ApiImplicitParam(name = "relation_comment_id", value = "关系评论的id  评论的最顶上的那一个id  0为直接评论的资讯   默认为0", dataType = "string",paramType = "query"),
    	@ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "string",paramType = "query"),
    	@ApiImplicitParam(name = "startNum", value = "最后一条记录，默认0", dataType = "string",paramType = "query"),
    })
	@ValidateParam(field={"information_id"})
	@PostMapping("information/information_comment_query")
	public ResultEntity information_comment_query(String token,Long information_id ,
			@RequestParam(defaultValue="0")Long relation_comment_id ,@RequestParam(defaultValue="10")Integer pageSize, 
			@RequestParam(defaultValue="0")Long startNum){
		
		Map<String, Object> map = new HashMap<>();
		if (ObjectHelper.isEmpty(token)){
			LoginVo loginVo = BaseUtil.getLoginVo(token);
			map.put("user_id", loginVo.getUser_id());
		}
		map.put("information_id", information_id);
		map.put("relation_comment_id", relation_comment_id);
		map.put("pageSize", pageSize);
		map.put("startNum", startNum);
		return new ResultEntity();
	}
}
