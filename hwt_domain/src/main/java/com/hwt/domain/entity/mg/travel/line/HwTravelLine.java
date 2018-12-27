package com.hwt.domain.entity.mg.travel.line;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 旅行社线路
 * @author Administrator
 *
 */
public class HwTravelLine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 线路id
	 */
	private Long line_id; 
	
	/**
	 * 旅行社id
	 */
	private Long bureau_id; 
	
	/**
	 * 线路名称
	 */
	private String line_name;
	
	/**
	 * 行程天数
	 */
	private Integer day_num;
	
	/**
	 * 封面图片
	 */
	private String line_cover;
	
	/**
	 * 价格  json字符串
	 */
	private String line_price;
	
	/**
	 * 最大价格
	 */
	private BigDecimal max_price;
	
	/**
	 * 最小价格
	 */
	private BigDecimal min_price;
	

	/**
	 * 是否删除 0-删除 1-不删除
	 */
	private Integer is_hide;
	
	/**
	 * 销售数量
	 */
	private Integer sell_num;
	
	
	/**
	 * 创建时间
	 */
	private Long create_time;
	
	/**
	 * 修改时间
	 */
	private Long update_time;
	
	/**
	 * 线路分类id
	 */
	private Long line_type;
	
	/**
	 * 线路服务保障集  如 1,2,3
	 */
	private String line_service;
	
	/**
	 * 线路图片集
	 */
	private String line_images;
	
	@ApiModelProperty(value = "相符总分" )
    private Integer match_score;

    /**
     * 行程总分
     */
	@ApiModelProperty(value = "行程总分 " )
    private Integer trip_score;

    /**
     * 服务总分
     */
	@ApiModelProperty(value = "服务总分" )
    private Integer service_score;

    /**
     * 综合总分 
     */
	@ApiModelProperty(value = "综合总分 ")
    private Float score;
	
	/**
	 * 点评次数
	 */
	@ApiModelProperty(value = "点评次数")
	private Long score_num;
	/**
	 * 图文详情
	 */
	private String line_info;
	
	/**
	 * 景点关联集 如 z1zz2zz3z
	 */
	private String scenics;
	
	/**
	 * 关联景点详情
	 */
	private String scenics_info;
	
	/**
	 * 交通信息
	 */
	private String line_traffic;
	
	/**
	 * 酒店住宿
	 */
	private String line_hotel;
	
	/**
	 * 行程介绍
	 */
	private String line_trip;
	
	/**
	 * 餐饮
	 */
	private String line_restaurant;
	
	/**
	 * 费用包含
	 */
	private String line_cost_info;
	
	/**
	 * 费用不含
	 */
	private String line_notcost_info;

	/**
	 * 自费项目
	 */
	private String line_self_expense;
	
	/**
	 * 预定须知
	 */
	private String line_predetermined_instructions;
	
	/**
	 * 保险说明
	 */
	private String line_insurance_description;
	
	/**
	 * 取消说明
	 */
	private String line_cancel_description;
	
	/**
	 * 安全提醒
	 */
	private String line_security_reminding;

	public Long getLine_id() {
		return line_id;
	}

	public void setLine_id(Long line_id) {
		this.line_id = line_id;
	}

	public Long getBureau_id() {
		return bureau_id;
	}

	public void setBureau_id(Long bureau_id) {
		this.bureau_id = bureau_id;
	}

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public String getLine_cover() {
		return line_cover;
	}

	public void setLine_cover(String line_cover) {
		this.line_cover = line_cover;
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

	public Long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}

	public Long getLine_type() {
		return line_type;
	}

	public void setLine_type(Long line_type) {
		this.line_type = line_type;
	}

	public String getLine_service() {
		return line_service;
	}

	public void setLine_service(String line_service) {
		this.line_service = line_service;
	}

	public String getLine_images() {
		return line_images;
	}

	public void setLine_images(String line_images) {
		this.line_images = line_images;
	}

	public String getLine_info() {
		return line_info;
	}

	public void setLine_info(String line_info) {
		this.line_info = line_info;
	}

	public String getScenics() {
		return scenics;
	}

	public void setScenics(String scenics) {
		this.scenics = scenics;
	}

	public String getLine_traffic() {
		return line_traffic;
	}

	public void setLine_traffic(String line_traffic) {
		this.line_traffic = line_traffic;
	}

	public String getLine_hotel() {
		return line_hotel;
	}

	public void setLine_hotel(String line_hotel) {
		this.line_hotel = line_hotel;
	}

	public String getLine_trip() {
		return line_trip;
	}

	public void setLine_trip(String line_trip) {
		this.line_trip = line_trip;
	}

	public String getLine_restaurant() {
		return line_restaurant;
	}

	public void setLine_restaurant(String line_restaurant) {
		this.line_restaurant = line_restaurant;
	}

	public String getLine_cost_info() {
		return line_cost_info;
	}

	public void setLine_cost_info(String line_cost_info) {
		this.line_cost_info = line_cost_info;
	}

	public String getLine_notcost_info() {
		return line_notcost_info;
	}

	public void setLine_notcost_info(String line_notcost_info) {
		this.line_notcost_info = line_notcost_info;
	}

	public String getLine_self_expense() {
		return line_self_expense;
	}

	public void setLine_self_expense(String line_self_expense) {
		this.line_self_expense = line_self_expense;
	}

	public String getLine_predetermined_instructions() {
		return line_predetermined_instructions;
	}

	public void setLine_predetermined_instructions(String line_predetermined_instructions) {
		this.line_predetermined_instructions = line_predetermined_instructions;
	}

	public String getLine_insurance_description() {
		return line_insurance_description;
	}

	public void setLine_insurance_description(String line_insurance_description) {
		this.line_insurance_description = line_insurance_description;
	}

	public String getLine_cancel_description() {
		return line_cancel_description;
	}

	public void setLine_cancel_description(String line_cancel_description) {
		this.line_cancel_description = line_cancel_description;
	}

	public String getLine_security_reminding() {
		return line_security_reminding;
	}

	public void setLine_security_reminding(String line_security_reminding) {
		this.line_security_reminding = line_security_reminding;
	}

	public Integer getSell_num() {
		return sell_num;
	}

	public void setSell_num(Integer sell_num) {
		this.sell_num = sell_num;
	}

	public String getLine_price() {
		return line_price;
	}

	public void setLine_price(String line_price) {
		this.line_price = line_price;
	}

	public Integer getDay_num() {
		return day_num;
	}

	public void setDay_num(Integer day_num) {
		this.day_num = day_num;
	}

	public String getScenics_info() {
		return scenics_info;
	}

	public void setScenics_info(String scenics_info) {
		this.scenics_info = scenics_info;
	}

	public Integer getMatch_score() {
		return match_score;
	}

	public void setMatch_score(Integer match_score) {
		this.match_score = match_score;
	}

	public Integer getTrip_score() {
		return trip_score;
	}

	public void setTrip_score(Integer trip_score) {
		this.trip_score = trip_score;
	}

	public Integer getService_score() {
		return service_score;
	}

	public void setService_score(Integer service_score) {
		this.service_score = service_score;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getScore_num() {
		return score_num;
	}

	public void setScore_num(Long score_num) {
		this.score_num = score_num;
	}

	public BigDecimal getMax_price() {
		return max_price;
	}

	public void setMax_price(BigDecimal max_price) {
		this.max_price = max_price;
	}

	public BigDecimal getMin_price() {
		return min_price;
	}

	public void setMin_price(BigDecimal min_price) {
		this.min_price = min_price;
	}
	
	
}
