package com.hwt.domain.entity.user.redPacket;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="红包")
public class HxUserRedPacket implements Serializable {
    /**
     * 红包id
     */
	@ApiModelProperty(value="红包id")
    private Long red_packet_id;

    /**
     * 发红包人id
     */
	@ApiModelProperty(value="发红包人id")
    private Long user_id;

    /**
     * 红包名称
     */
	@ApiModelProperty(value="红包名称")
    private String title;

    /**
     * 消息文本内容
     */
	@ApiModelProperty(value="消息文本内容")
    private String content;

	/**
	  * 交易单号
	  */
	@ApiModelProperty(value="1-随机  0-均等")
	private String trans_num;

    /**
     * 1-随机  0-均等
     */
	@ApiModelProperty(value="1-随机  0-均等")
    private Integer is_random;
	


    /**
     * 群id 0不是群
     */
	@ApiModelProperty(value=" 群id 0不是群")
    private Long group_id;

    /**
     * 收红包人id  0-说明是群红包
     */
	@ApiModelProperty(value="收红包人id  0-说明是群红包")
    private Long accept_user_id;

    /**
     * 红包金额
     */
	@ApiModelProperty(value="红包金额")
    private BigDecimal send_amount;

	  /**
     * 红包个数
     */
	@ApiModelProperty(value="红包个数")
    private Integer packet_number;
    /**
     * 创建时间
     */
	@ApiModelProperty(value="创建时间")
    private Long creat_time;

    /**
     * 领完的时间
     */
	@ApiModelProperty(value="领完的时间")
    private Long linwan_time;


	 /**
    * 0-未支付  1-已支付
    */
	@ApiModelProperty(value="0-未支付  1-已支付")
   private Integer is_pay;

   /**
    * 支付时间
    */
	@ApiModelProperty(value="支付时间")
   private Long pay_time;
	
    private static final long serialVersionUID = 1L;

    public Long getRed_packet_id() {
        return red_packet_id;
    }

    public void setRed_packet_id(Long red_packet_id) {
        this.red_packet_id = red_packet_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
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

    public Long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Long creat_time) {
        this.creat_time = creat_time;
    }

    public Long getLinwan_time() {
        return linwan_time;
    }

    public void setLinwan_time(Long linwan_time) {
        this.linwan_time = linwan_time;
    }

	public Integer getPacket_number() {
		return packet_number;
	}

	public void setPacket_number(Integer packet_number) {
		this.packet_number = packet_number;
	}

	public Integer getIs_random() {
		return is_random;
	}

	public void setIs_random(Integer is_random) {
		this.is_random = is_random;
	}

	public String getTrans_num() {
		return trans_num;
	}

	public void setTrans_num(String trans_num) {
		this.trans_num = trans_num;
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
    
    
}