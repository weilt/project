package com.hwt.domain.entity.user.redPacket.vo;

import java.math.BigDecimal;
import java.util.List;

import com.hwt.domain.entity.user.BaseUserVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 红包详情
 * @author Administrator
 *
 */
@ApiModel(value="红包详情")
public class HxUserRedPacketVo {
	
	/**
	 * 红包id
	 */
	@ApiModelProperty(value="红包id")
	private Long red_packet_id;
	
	
	/**
	 * 发红包的人
	 */
	@ApiModelProperty(value="发红包的人")
	private BaseUserVo userRedPacket;
	
	/**
	 * 红包名称
	 */
	@ApiModelProperty(value="红包名称")
	private String title;
	
	/**
	 * 消息文本内容
	 */
	@ApiModelProperty(value="消息文本内容")
	private String content;
	
	   /**
     * 1-随机  0-均等
     */
	@ApiModelProperty(value="1-随机  0-均等")
    private Integer is_random;

	/**
	 * 红包个数
	 */
	@ApiModelProperty(value="红包个数")
	private Integer packet_number;
	
	/**
	 * 红包金额
	 */
	@ApiModelProperty(value="红包金额")
	private BigDecimal send_amount;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private Long creat_time;
	
	
	/**
	 * 领完的时间
	 */
	@ApiModelProperty(value="领完的时间")
	private Long linwan_time;
	
	/**
	 * 领取的信息
	 */
	@ApiModelProperty(value="领取的信息")
	private List<UserRobRedPacketVo> userRobRedPacketVos;
	
	


	public Long getRed_packet_id() {
		return red_packet_id;
	}

	public void setRed_packet_id(Long red_packet_id) {
		this.red_packet_id = red_packet_id;
	}

	

	public BaseUserVo getUserRedPacket() {
		return userRedPacket;
	}

	public void setUserRedPacket(BaseUserVo userRedPacket) {
		this.userRedPacket = userRedPacket;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPacket_number() {
		return packet_number;
	}

	public void setPacket_number(Integer packet_number) {
		this.packet_number = packet_number;
	}

	public BigDecimal getSend_amount() {
		return send_amount;
	}

	public void setSend_amount(BigDecimal send_amount) {
		this.send_amount = send_amount;
	}

	public Long getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Long creat_time) {
		this.creat_time = creat_time;
	}

	public Long getLinwan_time() {
		return linwan_time;
	}

	public void setLinwan_time(Long linwan_time) {
		this.linwan_time = linwan_time;
	}

	public List<UserRobRedPacketVo> getUserRobRedPacketVos() {
		return userRobRedPacketVos;
	}

	public void setUserRobRedPacketVos(List<UserRobRedPacketVo> userRobRedPacketVos) {
		this.userRobRedPacketVos = userRobRedPacketVos;
	}

	public Integer getIs_random() {
		return is_random;
	}

	public void setIs_random(Integer is_random) {
		this.is_random = is_random;
	}

	
	
	
}
