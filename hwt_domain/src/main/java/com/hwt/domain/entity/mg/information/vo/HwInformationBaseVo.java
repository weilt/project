package com.hwt.domain.entity.mg.information.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 资讯
 * @author Administrator
 *
 */
@ApiModel(value="资讯列表")
public class HwInformationBaseVo {

	
	/**
	 * 资讯 id
	 */
	@ApiModelProperty(value = "资讯 id")
	private Long information_id;
	
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String tilte;
	
	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	private String author;
	
	
	/**
	 * 列表图片展示
	 */
	@ApiModelProperty(value = "列表图片展示")
	private String images;
	
	
	/**
	 * 评论数
	 */
	@ApiModelProperty(value = "评论数")
	private Long comment_num;
	
	/**
	 * 获赞数
	 */
	@ApiModelProperty(value = "获赞数")
	private Long good_num;
	
	/**
	 * 阅读数
	 */
	@ApiModelProperty(value = "阅读数")
	private Long look_num;
	
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Long create_time;


	public Long getInformation_id() {
		return information_id;
	}


	public void setInformation_id(Long information_id) {
		this.information_id = information_id;
	}


	public String getTilte() {
		return tilte;
	}


	public void setTilte(String tilte) {
		this.tilte = tilte;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getImages() {
		return images;
	}


	public void setImages(String images) {
		this.images = images;
	}


	public Long getComment_num() {
		return comment_num;
	}


	public void setComment_num(Long comment_num) {
		this.comment_num = comment_num;
	}


	public Long getGood_num() {
		return good_num;
	}


	public void setGood_num(Long good_num) {
		this.good_num = good_num;
	}


	public Long getLook_num() {
		return look_num;
	}


	public void setLook_num(Long look_num) {
		this.look_num = look_num;
	}


	public Long getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	
}
