
package com.hwt.domain.entity.price.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 价格 
 * @author Administrator
 *
 */
@ApiModel(value=" 价格 展示 ")
public class HwPriceVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    /**
     * 成人价格
     */
	@ApiModelProperty(value="成人价格")
    private BigDecimal adult_price;

    /**
     * 儿童价格
     */
	@ApiModelProperty(value="儿童价格")
    private BigDecimal child_price;

    /**
     * 单位  1-每单 2-每人 3-每天 4-每小时
     */
	@ApiModelProperty(value="单位  1-每单 2-每人 3-每天 4-每小时")
    private String company;

    /**
     * 时间字符串形式  yyyy-MM-dd
     */
	@ApiModelProperty(value=" 时间字符串形式  yyyy-MM-dd")
    private String time_str;

    /**
     * 时间 毫秒时间戳形式
     */
	@ApiModelProperty(value="时间 毫秒时间戳形式")
    private Long time;

    /**
     * 该属性目前只能针对导游   0-可被下单 1-已被下单
     */
	@ApiModelProperty(value="该属性目前只能针对导游   0-可被下单 1-已被下单")
    private Integer status;

    /**
     * 人数上限  0-无上限
     */
	@ApiModelProperty(value=" 人数上限  0-无上限")
    private Integer person_num;

	
	public BigDecimal getAdult_price() {
		return adult_price;
	}

	public void setAdult_price(BigDecimal adult_price) {
		this.adult_price = adult_price;
	}

	public BigDecimal getChild_price() {
		return child_price;
	}

	public void setChild_price(BigDecimal child_price) {
		this.child_price = child_price;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTime_str() {
		return time_str;
	}

	public void setTime_str(String time_str) {
		this.time_str = time_str;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPerson_num() {
		return person_num;
	}

	public void setPerson_num(Integer person_num) {
		this.person_num = person_num;
	}

   
	
}
