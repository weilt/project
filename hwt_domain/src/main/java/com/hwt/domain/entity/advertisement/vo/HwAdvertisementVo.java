package com.hwt.domain.entity.advertisement.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="轮播图")
public class HwAdvertisementVo implements Serializable {
    /**
     * 广告id
     */
	@ApiModelProperty(value = "广告id")
    private Long ad_id;

    /**
     * 广告类型  
     */
	@ApiModelProperty(value = " 广告类型  ")
    private Long ad_type;

   

    /**
     * 标题
     */
	@ApiModelProperty(value = "标题")
    private String title;

    /**
     * 真实id 0-不是我们平台的
     */
	@ApiModelProperty(value = "真实id 0-不是我们平台的")
    private Long real_id;

    /**
     * 链接
     */
	@ApiModelProperty(value = "链接")
    private String ad_url;

    /**
     * 图片
     */
	@ApiModelProperty(value = "图片")
    private String image;

   


    private static final long serialVersionUID = 1L;


	public Long getAd_id() {
		return ad_id;
	}


	public void setAd_id(Long ad_id) {
		this.ad_id = ad_id;
	}


	public Long getAd_type() {
		return ad_type;
	}


	public void setAd_type(Long ad_type) {
		this.ad_type = ad_type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Long getReal_id() {
		return real_id;
	}


	public void setReal_id(Long real_id) {
		this.real_id = real_id;
	}


	public String getAd_url() {
		return ad_url;
	}


	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	

   
}