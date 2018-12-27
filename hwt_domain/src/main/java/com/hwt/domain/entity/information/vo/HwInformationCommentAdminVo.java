package com.hwt.domain.entity.information.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="订单评论列表后台返回")
public class HwInformationCommentAdminVo extends HwInformationCommentBaseVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 是否删除  0-否 1-是
     */
	@ApiModelProperty(value="是否删除  0-否 1-是")
    private Integer is_hide;
    
	public Integer getIs_hide() {
		return is_hide;
	}
	public void setIs_hide(Integer is_hide) {
		this.is_hide = is_hide;
	}
}