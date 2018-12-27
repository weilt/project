package com.hwt.domain.entity.information.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="订单评论列表返回")
public class HwInformationCommentBaseVo implements Serializable {
    /**
     * 评论id
     */
	@ApiModelProperty(value="评论id")
    private Long comment_id;

    /**
     * 资讯id
     */
	@ApiModelProperty(value="资讯id")
    private Long information_id;

    /**
     * 评论人
     */
	@ApiModelProperty(value="评论人")
    private InformationCommentUserVo comment_user;

    /**
     * 恢复的谁
     */
	@ApiModelProperty(value="恢复的谁")
    private InformationCommentUserVo parent_comment_user;

    /**
     * 内容
     */
	@ApiModelProperty(value="内容")
    private String content;

    /**
     * 1-评论 2-点赞
     */
	@ApiModelProperty(value=" 1-评论 2-点赞")
    private Integer type;


    /**
     * 创建时间
     */
	@ApiModelProperty(value="创建时间")
    private Long create_time;


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


	public InformationCommentUserVo getComment_user() {
		return comment_user;
	}


	public void setComment_user(InformationCommentUserVo comment_user) {
		this.comment_user = comment_user;
	}


	public InformationCommentUserVo getParent_comment_user() {
		return parent_comment_user;
	}


	public void setParent_comment_user(InformationCommentUserVo parent_comment_user) {
		this.parent_comment_user = parent_comment_user;
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


	public Long getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

    
}