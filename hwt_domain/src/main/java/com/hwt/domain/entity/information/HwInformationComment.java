package com.hwt.domain.entity.information;

import java.io.Serializable;

/**
 * @author 
 */
public class HwInformationComment implements Serializable {
    /**
     * 评论id
     */
    private Long comment_id;

    /**
     * 资讯id
     */
    private Long information_id;

    /**
     * 评论人id
     */
    private Long user_id;

    /**
     * 恢复的谁  0-不是
     */
    private Long parent_user_id;

    /**
     * 回复的哪一条  0-不是
     */
    private Long parent_comment_id;

    /**
     * 是否是顶级回复   0--否 1- 是
     */
    private Integer is_top;

    /**
     * 关系评论的id  评论的最顶上的那一个id  0为直接评论的资讯
     */
    private Long relation_comment_id;

    /**
     * 内容
     */
    private String content;

    /**
     * 1-评论 2-点赞
     */
    private Integer type;

    /**
     * 是否删除  0-否 1-是
     */
    private Integer is_hide;

    /**
     * 创建时间
     */
    private Long create_time;

    /**
     * 被赞次数
     */
    private Long good_num;

    /**
     * 回复数
     */
    private Long comment_num;

    private static final long serialVersionUID = 1L;

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public Long getInformation_id() {
        return information_id;
    }

    public void setInformation_id(Long information_id) {
        this.information_id = information_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getParent_user_id() {
        return parent_user_id;
    }

    public void setParent_user_id(Long parent_user_id) {
        this.parent_user_id = parent_user_id;
    }

    public Long getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(Long parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public Integer getIs_top() {
        return is_top;
    }

    public void setIs_top(Integer is_top) {
        this.is_top = is_top;
    }

    

    public Long getRelation_comment_id() {
		return relation_comment_id;
	}

	public void setRelation_comment_id(Long relation_comment_id) {
		this.relation_comment_id = relation_comment_id;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIs_hide() {
        return is_hide;
    }

    public void setIs_hide(Integer is_hide) {
        this.is_hide = is_hide;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getGood_num() {
        return good_num;
    }

    public void setGood_num(Long good_num) {
        this.good_num = good_num;
    }

    public Long getComment_num() {
        return comment_num;
    }

    public void setComment_num(Long comment_num) {
        this.comment_num = comment_num;
    }
}