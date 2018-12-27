package com.hwt.domain.entity.user.video;

import java.io.Serializable;

/**
 * @author 
 */
public class HxUserVideoGradeApply implements Serializable {
    /**
     * 申请id
     */
    private Long grade_apply_id;

    /**
     * 申请人
     */
    private Long user_id;

    /**
     * 申请的等级  2-中等（60秒） 3-高级（能直播）
     */
    private Integer apply_grade;

    /**
     * 0-未处理 1-通过 2-未通过
     */
    private Integer status;

    /**
     * 处理人
     */
    private Long admin_user_id;

    /**
     * 未通过原因
     */
    private String fail_reason;

    /**
     * 申请时间
     */
    private Long create_time;

    /**
     * 更新时间
     */
    private Long update_time;

    private static final long serialVersionUID = 1L;

    public Long getGrade_apply_id() {
        return grade_apply_id;
    }

    public void setGrade_apply_id(Long grade_apply_id) {
        this.grade_apply_id = grade_apply_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getApply_grade() {
		return apply_grade;
	}

	public void setApply_grade(Integer apply_grade) {
		this.apply_grade = apply_grade;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(Long admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }
}