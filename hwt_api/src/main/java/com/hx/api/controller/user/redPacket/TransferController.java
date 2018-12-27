package com.hx.api.controller.user.redPacket;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.HxUserTransfer;
import com.hwt.domain.entity.user.redPacket.vo.HxUserTransferVo;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.core.exception.BaseException;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.ObjectHelper;
import com.hx.user.redPacket.service.TransferService;
import com.hx.user.utils.BaseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "API - TransferController", description = "用户转账操作接口")
@RestController
public class TransferController {

	@Autowired
	private TransferService transferService;
	
	/**
	 * 转账
	 * 
	 * @param token
	 *            - 验证登录状态 - Y
	 * @return
	 */
	@ApiOperation(value = "发红包", notes = "发红包", response = HxUserTransfer.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "title", value = "红包名称", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "content", value = "消息文本内容", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "accept_user_id", value = "收款人id", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "send_amount", value = "红包金额", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "transfer_type", value = "转账方式  1-聊天界面  2-扫码", dataType = "string", required = true, paramType = "query"),
	})
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "send_amount", "accept_user_id" }, checkedType = { CheckedType.TOKEN })
	@RequestMapping(value = "transfer/send", method = RequestMethod.POST)
	public ResultEntity send(String token, String title, String content,
			Long accept_user_id,
			BigDecimal send_amount,Integer transfer_type) {

		if (send_amount.doubleValue() < 0.01) {
			throw new BaseException("转账金额有误！");
		}


		LoginVo loginVo = BaseUtil.getLoginVo(token);

		HxUserTransfer hxUserTransfer = transferService.send(loginVo.getUser_id(),title,content,accept_user_id,send_amount,transfer_type);

		return new ResultEntity(hxUserTransfer);

	}
	
	/**
	 * 判断能否收账
	 */
	@ApiOperation(value = "判断能否收账", notes = "判断能否收账", response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "transfer_id", value = "转账id", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "accept_user_id", value = "收钱人id", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "send_user_id", value = "转账人id", dataType = "string", required = true, paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "transfer_id","accept_user_id","send_user_id"}, checkedType = { CheckedType.TOKEN })
	@RequestMapping(value = "transfer/is_accept", method = RequestMethod.POST)
	public ResultEntity is_accept(String token, Long transfer_id,
			Long send_user_id, Long accept_user_id) {
		String redpacketprefix = HwPrefix.TransferPrefix+transfer_id+"_"+
				send_user_id+"_"+accept_user_id;
		
		//System.err.println(redpacketprefix);
		String str = RedisCache.get(redpacketprefix, RedisCache.db10);
		if(str!=null){
			
			if (accept_user_id!=0){
				
				LoginVo loginVo = BaseUtil.getLoginVo(token);
				if (!loginVo.getUser_id().equals(accept_user_id)){
					//不是自己能领的钱返回-2 
					return new ResultEntity(-2);
				}
			}
			//返回1 说明能领
			return new ResultEntity(1);
		} else {
			//判断是否领过了，还是过期 了  0-过期了  -1领过了
			int num = transferService.is_accept(transfer_id);
			return new ResultEntity(num);
		}
	}
	
	
	/**
	 * 领取/拒收
	 */
	@ApiOperation(value = "领取/拒收", notes = "领取/拒收", response = ResultEntity.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
		@ApiImplicitParam(name = "transfer_id", value = "转账id", dataType = "string", required = true, paramType = "query"),
		@ApiImplicitParam(name = "is_accept", value = " 1-接受 2-拒收", dataType = "string", required = true, paramType = "query"),
		@ApiImplicitParam(name = "accept_user_id", value = "收钱人id", dataType = "string", required = true, paramType = "query"),
		@ApiImplicitParam(name = "send_user_id", value = "转账人id", dataType = "string", required = true, paramType = "query")})
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "transfer_id","accept_user_id","send_user_id"}, checkedType = { CheckedType.TOKEN })
	@RequestMapping(value = "transfer/accept", method = RequestMethod.POST)
	public ResultEntity accept(String token, Long transfer_id,
			Long send_user_id, Long accept_user_id,Integer is_accept) throws Exception  {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
	
		//1-接受 2-拒收   0-已经操作过了
		 int num = transferService.accept(loginVo,transfer_id,send_user_id,accept_user_id,is_accept);
		 return new ResultEntity(num);
		
	}
	
	/**
	 * 查询转账详情
	 */
	@ApiOperation(value = " 查询转账详情", notes = " 查询转账详情", response = HxUserTransferVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string", required = true, paramType = "query"),
		@ApiImplicitParam(name = "transfer_id", value = "转账id", dataType = "string", required = true, paramType = "query"),
	})
	@ApiResponses({ @ApiResponse(code = 200, message = "") })
	@ValidateParam(field = { "token", "transfer_id"}, checkedType = { CheckedType.TOKEN })
	@RequestMapping(value = "transfer/info", method = RequestMethod.POST)
	public ResultEntity info(String token, Long transfer_id
			) throws Exception  {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
	
		//1-接受 2-拒收   0-已经操作过了
		HxUserTransferVo hxUserTransferVo = transferService.info(loginVo.getUser_id(),transfer_id);
		 return new ResultEntity(hxUserTransferVo);
		
	}
}
