package com.hwt.domain.entity.user.redPacket;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="转账")
public class HxUserTransfer implements Serializable {
    /**
     * 转账id
     */
	@ApiModelProperty(value="转账id")
    private Long transfer_id;

    /**
     * 转账人id
     */
	@ApiModelProperty(value="转账人id")
    private Long user_id;

    /**
     * 收款人id
     */
	@ApiModelProperty(value="收款人id")
    private Long accept_user_id;

    /**
     * 转账的金额
     */
	@ApiModelProperty(value="转账的金额")
    private BigDecimal send_amount;

    /**
     * 转账名称
     */
	@ApiModelProperty(value="转账名称")
    private String title;

    /**
     * 消息文本内容
     */
	@ApiModelProperty(value="消息文本内容")
    private String content;
	

    /**
     * 转账方式  1-聊天界面  2-扫码
     */
	@ApiModelProperty(value="转账方式  1-聊天界面  2-扫码")
    private Integer transfer_type;

    /**
     * 交易单号
     */
	@ApiModelProperty(value="交易单号")
    private String trans_num;

    /**
     * 创建时间
     */
	@ApiModelProperty(value="创建时间")
    private Long creat_time;

    /**
     * 领取时间
     */
	@ApiModelProperty(value="领取时间")
    private Long linqu_time;

    /**
     * 是否接受  0-未处理  1-接受 2-拒收
     */
	@ApiModelProperty(value="是否接受  0-未处理  1-接受 2-拒收  3-超时")
    private Integer is_accept;

    /**
     * 拒收时间
     */
	@ApiModelProperty(value="拒收时间")
    private Long refuse_time;

    /**
     * 拒收交易单号
     */
	@ApiModelProperty(value=" 拒收交易单号")
    private String refuse_trans_num;
	
	 /**
     * 0-未支付 1-已支付
     */
	@ApiModelProperty(value="0-未支付 1-已支付")
    private Integer is_pay;

    /**
     * 支付时间
     */
	@ApiModelProperty(value="支付时间")
    private Long pay_time;

    private static final long serialVersionUID = 1L;

    public Long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(Long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAccept_user_id() {
        return accept_user_id;
    }

    public void setAccept_user_id(Long accept_user_id) {
        this.accept_user_id = accept_user_id;
    }

    public BigDecimal getSend_amount() {
        return send_amount;
    }

    public void setSend_amount(BigDecimal send_amount) {
        this.send_amount = send_amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrans_num() {
        return trans_num;
    }

    public void setTrans_num(String trans_num) {
        this.trans_num = trans_num;
    }

    public Long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Long creat_time) {
        this.creat_time = creat_time;
    }

    public Long getLinqu_time() {
        return linqu_time;
    }

    public void setLinqu_time(Long linqu_time) {
        this.linqu_time = linqu_time;
    }

    public Integer getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(Integer is_accept) {
        this.is_accept = is_accept;
    }

    public Long getRefuse_time() {
        return refuse_time;
    }

    public void setRefuse_time(Long refuse_time) {
        this.refuse_time = refuse_time;
    }

    public String getRefuse_trans_num() {
        return refuse_trans_num;
    }

    public void setRefuse_trans_num(String refuse_trans_num) {
        this.refuse_trans_num = refuse_trans_num;
    }

	public Integer getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(Integer is_pay) {
		this.is_pay = is_pay;
	}

	public Long getPay_time() {
		return pay_time;
	}

	public void setPay_time(Long pay_time) {
		this.pay_time = pay_time;
	}

	public Integer getTransfer_type() {
		return transfer_type;
	}

	public void setTransfer_type(Integer transfer_type) {
		this.transfer_type = transfer_type;
	}
    
    
}