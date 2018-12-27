package com.hwt.domain.entity.information;

import java.io.Serializable;

/**
 * 资讯评论点赞
 * @author 
 */
public class HwInformationCommentGood implements Serializable {
	
	/**
     * 用户id
     */
    private Long user_id;

    /**
     * 评论id
     */
    private Long comment_id;
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

	public Long getComment_id() {
		return comment_id;
	}

	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
	}

	public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }
}