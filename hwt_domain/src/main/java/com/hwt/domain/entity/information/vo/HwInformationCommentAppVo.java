package com.hwt.domain.entity.information.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="订单评论列表手机端返回")
public class HwInformationCommentAppVo implements Serializable {
	

    /**
     * 是否是顶级回复   0--否 1- 是
     */
	@ApiModelProperty(value="是否是顶级回复   0--否 1- 是")
    private Integer is_top;

	
	 /**
     * 回复 哪一条的内容
     */
	@ApiModelProperty(value="回复 哪一条的内容")
    private String parent_comment_content;
	
    /**
     * 关系评论的id  评论的最顶上的那一个id  0为直接评论的资讯
     */
	@ApiModelProperty(value="关系评论的id  评论的最顶上的那一个id  0为直接评论的资讯")
    private Long relation_comment_id;

    /**
     * 被赞次数
     */
	@ApiModelProperty(value="被赞次数")
    private Long good_num;

    /**
     * 回复数
     */
	@ApiModelProperty(value="回复数")
    private Long comment_num;

    private static final long serialVersionUID = 1L;

	public Integer getIs_top() {
		return is_top;
	}

	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}

	public String getParent_comment_content() {
		return parent_comment_content;
	}

	public void setParent_comment_content(String parent_comment_content) {
		this.parent_comment_content = parent_comment_content;
	}

	public Long getRelation_comment_id() {
		return relation_comment_id;
	}

	public void setRelation_comment_id(Long relation_comment_id) {
		this.relation_comment_id = relation_comment_id;
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