package com.hwt.domain.entity.user.redPacket;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 
 */
public class HxUserRobRedPacket  implements Serializable {
	
	  /**
     * 用户id
     */
    private Long user_id;

    /**
     * 红包id
     */
    private Long red_packet_id;

    /**
     * 抢到的金额
     */
    private BigDecimal accept_amount;
    
    /**
     * 交易单号
     */
    private String trans_num;

    /**
     * 创建时间
     */
    private Long creat_time;

    private static final long serialVersionUID = 1L;

    public BigDecimal getAccept_amount() {
        return accept_amount;
    }

    public void setAccept_amount(BigDecimal accept_amount) {
        this.accept_amount = accept_amount;
    }

    public Long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Long creat_time) {
        this.creat_time = creat_time;
    }

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getRed_packet_id() {
		return red_packet_id;
	}

	public void setRed_packet_id(Long red_packet_id) {
		this.red_packet_id = red_packet_id;
	}

	public String getTrans_num() {
		return trans_num;
	}

	public void setTrans_num(String trans_num) {
		this.trans_num = trans_num;
	}
    
    
}