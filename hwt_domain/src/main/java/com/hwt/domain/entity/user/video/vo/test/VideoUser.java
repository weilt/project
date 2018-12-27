package com.hwt.domain.entity.user.video.vo.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="小视频用户")
public class VideoUser {

	/**
	 * id
	 * 
	 */
	@ApiModelProperty(value="id")
	private Long user_id;
	
	/**
	 * 账号
	 */
	@ApiModelProperty(value="账号")
	private String user_account;
	
	/**
	 * 头像
	 */
	@ApiModelProperty(value="头像")
	private String user_icon;
	
	/**
	 * 昵称
	 */
	@ApiModelProperty(value="昵称")
	private String nickname;
	
	/**
	 * 云信id
	 */
	@ApiModelProperty(value="云信id")
	private String im_id;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getUser_icon() {
		return user_icon;
	}

	public void setUser_icon(String user_icon) {
		this.user_icon = user_icon;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIm_id() {
		return im_id;
	}

	public void setIm_id(String im_id) {
		this.im_id = im_id;
	}
}