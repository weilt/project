package com.hwt.domain.entity.user;

import java.io.Serializable;

/**
 * 用户真实信息
 * @author 
 */
public class HxUseReal implements Serializable {
    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 用户真实姓名
     */
    private String real_name;

    /**
     * 身份证号
     */
    private String identity_no;

    /**
     * 身份证正面
     */
    private String identity_positive;

    /**
     * 身份证反面
     */
    private String identity_negative;

    /**
     * 手持证件照
     */
    private String identity_hold;

    /**
     * 现住址
     */
    private String present_address;

    /**
     * 处理人
     */
    private Long admin_user_id;

    /**
     * 0-未审核 1-通过 2-未通过
     */
    private Integer status;

    /**
     * 未通过原因
     */
    private String fail_reason;

    /**
     * 常用联系电话
     */
    private String often_phone;

    /**
     * 更新时间
     */
    private Long update_time;

    /**
     * 创建时间
     */
    private Long create_time;

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

    public Long getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(Long admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public String getOften_phone() {
        return often_phone;
    }

    public void setOften_phone(String often_phone) {
        this.often_phone = often_phone;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }
}