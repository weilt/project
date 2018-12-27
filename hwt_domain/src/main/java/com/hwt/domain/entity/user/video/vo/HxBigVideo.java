package com.hwt.domain.entity.user.video.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="长视频")
public class HxBigVideo {

	@ApiModelProperty(value="视频id")
	private Long video_id;
	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
    private String dec;
	
	
	 /**
     * 封面图
     */
	@ApiModelProperty(value="封面图")
    private String image;
	
	
    /**
     * 内容
     */
	@ApiModelProperty(value=" 内容")
    private String content;
	
	/**
     * 关联的id
     */
	@ApiModelProperty(value="关联的id")
    private Long name_id;

    /**
     * 关联的类型   1-景点 2-导游  3- 线路 4-资讯
     */
	@ApiModelProperty(value="关联的类型   1-景点 2-导游  3- 线路 4-资讯")
    private Integer name_type;

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getName_id() {
		return name_id;
	}

	public void setName_id(Long name_id) {
		this.name_id = name_id;
	}

	public Integer getName_type() {
		return name_type;
	}

	public void setName_type(Integer name_type) {
		this.name_type = name_type;
	}

	public Long getVideo_id() {
		return video_id;
	}

	public void setVideo_id(Long video_id) {
		this.video_id = video_id;
	}
	
	

}
