package com.hwt.domain.entity.user.redPacket.vo;

import java.math.BigDecimal;

import com.hwt.domain.entity.user.BaseUserVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author Administrator
 *
 */
@ApiModel(value="抢红包详情")
public class UserRobRedPacketVo {
	
	@ApiModelProperty(value="抢红包的人")
	private BaseUserVo userRobRedPacket;
	
	@ApiModelProperty(value="红包id")
	private Long red_packet_id;
	
	@ApiModelProperty(value="抢到的金额")
	private BigDecimal accept_amount;
	
	@ApiModelProperty(value="创建时间")
	private Long creat_time;

	public BaseUserVo getUserRobRedPacket() {
		return userRobRedPacket;
	}

	public void setUserRobRedPacket(BaseUserVo userRobRedPacket) {
		this.userRobRedPacket = userRobRedPacket;
	}

	public Long getRed_packet_id() {
		return red_packet_id;
	}

	public void setRed_packet_id(Long red_packet_id) {
		this.red_packet_id = red_packet_id;
	}

	public BigDecimal getAccept_amount() {
		return accept_amount;
	}

	public void setAccept_amount(BigDecimal accept_amount) {
		this.accept_amount = accept_amount;
	}

	public Long getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Long creat_time) {
		this.creat_time = creat_time;
	}
	
	
	
}
