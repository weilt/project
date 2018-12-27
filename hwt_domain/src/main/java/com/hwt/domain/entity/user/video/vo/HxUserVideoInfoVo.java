package com.hwt.domain.entity.user.video.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@ApiModel(value="小视频用户基本信息")
public class HxUserVideoInfoVo implements Serializable {
    /**
     * 用户id
     */
	@ApiModelProperty(value="用户id")
    private Long user_id;

    /**
     * 等级 1-初等（15秒） 2-中等（60秒） 3-高级（能直播）
     */
	@ApiModelProperty(value="等级 1-初等（15秒） 2-中等（60秒） 3-高级（能直播）")
    private Integer viode_grade;

    /**
     * 小视频封面
     */
	@ApiModelProperty(value="小视频封面")
    private String video_cover;


    private static final long serialVersionUID = 1L;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getViode_grade() {
		return viode_grade;
	}

	public void setViode_grade(Integer viode_grade) {
		this.viode_grade = viode_grade;
	}

	public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

}