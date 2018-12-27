package com.hwt.domain.entity.mg.information.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 资讯
 * @author Administrator
 *
 */
@ApiModel(value="资讯详情")
public class HwInformationVo extends HwInformationBaseVo {
	
	/**
	 * 资讯分类  0-为默认分类
	 */
	@ApiModelProperty(value = " 资讯分类  0-为默认分类")
	private Long information_type;
	
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	
	/**
	 * 来源
	 */
	@ApiModelProperty(value = "来源")
	private String source;

	/**
	 * 点赞的本人id 如果没有点赞 则为null 或者 0
	 */
	@ApiModelProperty(value = "点赞的本人id 如果没有点赞 则为null 或者 0")
	private Long good_user_id;
	
	public Long getInformation_type() {
		return information_type;
	}


	public void setInformation_type(Long information_type) {
		this.information_type = information_type;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public Long getGood_user_id() {
		return good_user_id;
	}


	public void setGood_user_id(Long good_user_id) {
		this.good_user_id = good_user_id;
	}
	
	
}
