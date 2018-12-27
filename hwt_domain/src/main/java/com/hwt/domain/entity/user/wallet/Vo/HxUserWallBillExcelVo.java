package com.hwt.domain.entity.user.wallet.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value="导出Excel文件")
public class HxUserWallBillExcelVo implements Serializable {
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String order_num;

    /**
     * 交易单号
     */
    @ApiModelProperty(value = "交易单号")
    private String trans_num;

    /**
     * 交易金额 或者 订单金额
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal operation_amount;

    /**
     * 支付方式 或者 提现方式 1-支付宝 2-微信 3-钱包 4-其他',
     */
    @ApiModelProperty(value = "支付方式")
    private Integer source;

    /**
     * 支付时间 或者 申请时间
     */
    @ApiModelProperty(value = "支付时间")
    private Long create_time;

    /**
     * 对账人员
     */
    @ApiModelProperty(value = "对账人员")
    private String handlers;

    /**
     * 对账时间
     */
    @ApiModelProperty(value = "对账时间")
    private Long account_time;

    /**
     * 状态 0-未对账 1-已对账'
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getTrans_num() {
        return trans_num;
    }

    public void setTrans_num(String trans_num) {
        this.trans_num = trans_num;
    }

    public BigDecimal getOperation_amount() {
        return operation_amount;
    }

    public void setOperation_amount(BigDecimal operation_amount) {
        this.operation_amount = operation_amount;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }

    public Long getAccount_time() {
        return account_time;
    }

    public void setAccount_time(Long account_time) {
        this.account_time = account_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
