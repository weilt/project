package com.hwt.domain.entity.information;

import java.io.Serializable;

/**
 * 资讯点赞
 * @author 
 */
public class HwInformationGood implements Serializable {
	
	 /**
     * 用户id
     */
    private Long user_id;

    /**
     * 创建时间
     */
    private Long information_id;
    
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

	public Long getInformation_id() {
		return information_id;
	}

	public void setInformation_id(Long information_id) {
		this.information_id = information_id;
	}

	public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }
}