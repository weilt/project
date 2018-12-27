package com.hwt.domain.entity.user.video;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="小视频")
public class HxUserVideo implements Serializable {
    /**
     * id
     */
	@ApiModelProperty(value="小视频id")
    private Long video_id;

    /**
     * 用户id
     */
	@ApiModelProperty(value="用户id")
    private Long user_id;

    /**
     * 分类
     */
	@ApiModelProperty(value="分类  0-默认")
    private Long attribute_id;
	
	 /**
     * 1-小视频，2-长视频
     */
	@ApiModelProperty(value="1-小视频，2-长视频")
    private Integer video_type;
	
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
     * 云视频id
     */
	@ApiModelProperty(value="云视频id")
    private String file_id;
    /**
     * 内容
     */
	@ApiModelProperty(value=" 内容")
    private String content;

    /**
     * 是否删除  0-否   1-是
     */
	@ApiModelProperty(value="是否删除  0-否   1-是")
    private Integer is_hide;

    /**
     * 是否公开 1-是 0-否
     */
	@ApiModelProperty(value="是否公开 1-是 0-否")
    private Integer is_open;

    /**
     * 审核状态 默认1 未审核 2-通过 3-未通过
     */
	@ApiModelProperty(value="审核状态 默认1 未审核 2-通过 3-未通过")
    private Integer status;

    /**
     * 未通过原因
     */
	@ApiModelProperty(value="未通过原因")
    private String reason;

    /**
     * 申请时间
     */
	@ApiModelProperty(value="申请时间")
    private Long create_time;

    /**
     * 点赞次数
     */
	@ApiModelProperty(value="点赞次数")
    private Long good_num;

    /**
     * 播放次数
     */
	@ApiModelProperty(value="播放次数")
    private Long look_num;

    /**
     * 评论次数
     */
	@ApiModelProperty(value="评论次数")
    private Long comment_num;

    /**
     * 经度
     */
	@ApiModelProperty(value="经度")
    private String longitude;

    /**
     * 纬度
     */
	@ApiModelProperty(value="纬度")
    private String latitude;

    /**
     * 城市名称
     */
	@ApiModelProperty(value="城市名称")
    private String city;

    /**
     * 区号
     */
	@ApiModelProperty(value="区号")
    private String area_code;
	

    /**
     * 所在地址
     */
	@ApiModelProperty(value="所在地址")
    private String location;

    /**
     * 所在地址
     */
	@ApiModelProperty(value="音频id")
    private Long music_id;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 操作人
     */
    @ApiModelProperty(value="操作人")
    private String handlers;
    /**
     * 转发次数
     */
    @ApiModelProperty(value="转发次数")
    private Integer transmit_number;

    /**
     * 操作时间
     */
    @ApiModelProperty(value="操作时间")
    private Long operation_time;


    private static final long serialVersionUID = 1L;

    public Integer getTransmit_number() {
        return transmit_number;
    }

    public void setTransmit_number(Integer transmit_number) {
        this.transmit_number = transmit_number;
    }

    public Long getMusic_id() {
        return music_id;
    }

    public void setMusic_id(Long music_id) {
        this.music_id = music_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }

    public Long getOperation_time() {
        return operation_time;
    }

    public void setOperation_time(Long operation_time) {
        this.operation_time = operation_time;
    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(Long attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIs_hide() {
        return is_hide;
    }

    public void setIs_hide(Integer is_hide) {
        this.is_hide = is_hide;
    }

    public Integer getIs_open() {
        return is_open;
    }

    public void setIs_open(Integer is_open) {
        this.is_open = is_open;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
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

    public Long getComment_num() {
        return comment_num;
    }

    public void setComment_num(Long comment_num) {
        this.comment_num = comment_num;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Integer getVideo_type() {
		return video_type;
	}

	public void setVideo_type(Integer video_type) {
		this.video_type = video_type;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
    
    
}