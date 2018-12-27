package com.hwt.domain.entity.user.Vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="用户真实信息")
public class HxUseRealVo implements Serializable {
    /**
     * 用户id
     */
	@ApiModelProperty(value="用户id")
    private Long user_id;

    /**
     * 用户真实姓名
     */
	@ApiModelProperty(value="用户真实姓名")
    private String real_name;

    /**
     * 身份证号
     */
	@ApiModelProperty(value="身份证号")
    private String identity_no;

    /**
     * 身份证正面
     */
	@ApiModelProperty(value="身份证正面")
    private String identity_positive;

    /**
     * 身份证反面
     */
	@ApiModelProperty(value="身份证反面")
    private String identity_negative;

    /**
     * 手持证件照
     */
	@ApiModelProperty(value="手持证件照")
    private String identity_hold;

    /**
     * 现住址
     */
	@ApiModelProperty(value="现住址")
    private String present_address;
	
	/**
	 *常用联系电话
	 */
	@ApiModelProperty(value="常用联系电话")
	private String often_phone;

	

    private static final long serialVersionUID = 1L;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getIdentity_no() {
        return identity_no;
    }

    public void setIdentity_no(String identity_no) {
        this.identity_no = identity_no;
    }

    public String getIdentity_positive() {
        return identity_positive;
    }

    public void setIdentity_positive(String identity_positive) {
        this.identity_positive = identity_positive;
    }

    public String getIdentity_negative() {
        return identity_negative;
    }

    public void setIdentity_negative(String identity_negative) {
        this.identity_negative = identity_negative;
    }

    public String getIdentity_hold() {
        return identity_hold;
    }

    public void setIdentity_hold(String identity_hold) {
        this.identity_hold = identity_hold;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

	public String getOften_phone() {
		return often_phone;
	}

	public void setOften_phone(String often_phone) {
		this.often_phone = often_phone;
	}

    
}