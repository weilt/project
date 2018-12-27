package com.hx.api.controller.user.redPacket;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.Vo.UserVo;
import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.vo.HxUserRedPacketVo;
import com.hwt.domain.entity.user.redPacket.vo.UserRobRedPacketVo;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.core.exception.BaseException;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.ObjectHelper;
import com.hx.user.redPacket.service.RedPacketService;
import com.hx.user.utils.BaseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "API - RedPacketController", description = "红包操作接口")
@RestController
public class RedPacketController {

	@Autowired
	private RedPacketService redPacketService;

	/**
	 * 发红包
	 * 
	 * @param token
	 *            - 验证登录状态 - Y
	 * @return
	 */
	@ApiOperation(value = "发红包", notes = "发红包", response = HxUserRedPacket.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "title", value = "红包名称", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "content", value = "消息文本内容", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "is_random", value = "1-随机  0-均等", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "group_id", value = "群id 0不是群", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "accept_user_id", value = "收红包人id  0-说明是群红包", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "send_amount", value = "红包金额", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "packet_number", value = "红包数", dataType = "string", required = true, paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "send_amount", "packet_number" }, checkedType = { CheckedType.TOKEN })
	@RequestMapping(value = "redPacket/send", method = RequestMethod.POST)
	public ResultEntity send(String token, String title, String content,@RequestParam(defaultValue = "1")Integer is_random,
			@RequestParam(defaultValue = "0") Long group_id, @RequestParam(defaultValue = "0") Long accept_user_id,
			BigDecimal send_amount, Integer packet_number) {

		if (send_amount.doubleValue() < packet_number * 0.01) {
			throw new BaseException("红包金额和红包数量有误！");
		}

		if (group_id == 0 && accept_user_id == 0) {
			throw new BaseException("必须有接受方！");
		}

		packet_number = packet_number < 1 ? 1 : packet_number;
		packet_number = packet_number > 100 ? 100 : packet_number;

		LoginVo loginVo = BaseUtil.getLoginVo(token);

		HxUserRedPacket hxUserRedPacket = redPacketService.send(loginVo.getUser_id(), title, content, is_random, group_id,
				accept_user_id, send_amount, packet_number);

		return new ResultEntity(hxUserRedPacket);

	}

	/**
	 * 判断能否抢
	 */
	@ApiOperation(value = "判断能否抢", notes = "判断能否抢", response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "group_id", value = "群id 0不是群", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "accept_user_id", value = "收红包人id  0-说明是群红包", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "red_packet_id", value = "红包id", dataType = "string", required = true, paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "red_packet_id",}, checkedType = { CheckedType.TOKEN })
	@RequestMapping(value = "redPacket/isRob", method = RequestMethod.POST)
	public ResultEntity isRob(String token, Long red_packet_id,
			@RequestParam(defaultValue = "0") Long group_id, @RequestParam(defaultValue = "0") Long accept_user_id) {
		String redpacketprefix = HwPrefix.RedPacketPrefix+red_packet_id+"_"+group_id+"_"+accept_user_id;
		
		//System.err.println(redpacketprefix);
		//String str = RedisCache.get(redpacketprefix, RedisCache.db10);
		Map<String, String> hgetgey = RedisCache.hgetgey(redpacketprefix, RedisCache.db10);
		
		if(!hgetgey.isEmpty()){
			
			if (accept_user_id!=0){
				
				LoginVo loginVo = BaseUtil.getLoginVo(token);
				if (!loginVo.getUser_id().equals(accept_user_id)){
					//不是自己能领的红包返回-2  私发的情况
					return new ResultEntity(-2);
				}
			}
			
			return new ResultEntity(1);
		} else {
			return new ResultEntity(-1);
		}

	}
	
	/**
	 * 抢红包
	 */
	@ApiOperation(value = "抢红包", notes = "抢红包", response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "group_id", value = "群id 0不是群", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "accept_user_id", value = "收红包人id  0-说明是群红包", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "red_packet_id", value = "红包id", dataType = "string", required = true, paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "red_packet_id",}, checkedType = {CheckedType.TOKEN})
	@RequestMapping(value = "redPacket/rob", method = RequestMethod.POST)
	public ResultEntity rob(String token, Long red_packet_id,
			@RequestParam(defaultValue = "0") Long group_id, @RequestParam(defaultValue = "0") Long accept_user_id) throws Exception  {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		if (accept_user_id!=0){
			String redpacketprefix = HwPrefix.RedPacketPrefix+red_packet_id+"_"+group_id+"_"+loginVo.getUser_id();
			
			//System.err.println(redpacketprefix);
			//String str = RedisCache.get(redpacketprefix, RedisCache.db10);
			Map<String, String> hgetgey = RedisCache.hgetgey(redpacketprefix, RedisCache.db10);
			System.err.println(hgetgey);
			if (hgetgey.isEmpty()){
				throw new BaseException("该红包你不能抢！");
			}
		}
		//1-是最后一个  -1不是最后一个  0-没抢到 
		 int num = redPacketService.rob(loginVo,red_packet_id,group_id,accept_user_id);
				return new ResultEntity(num);
		
	}
	/**
	 * 查询红包详情
	 */
	@ApiOperation(value = " 查询红包详情", notes = " 查询红包详情", response = HxUserRedPacketVo.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "red_packet_id", value = "红包id", dataType = "string", required = true, paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "red_packet_id",}, checkedType = {CheckedType.TOKEN})
	@RequestMapping(value = "redPacket/queryInfo", method = RequestMethod.POST)
	public ResultEntity queryInfo(String token, Long red_packet_id){
		
		HxUserRedPacketVo hxUserRedPacketVo = redPacketService.queryInfo(red_packet_id);
		return new ResultEntity(hxUserRedPacketVo);
	}
}
