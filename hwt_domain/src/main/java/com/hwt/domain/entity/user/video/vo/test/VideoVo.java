package com.hwt.domain.entity.user.video.vo.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="小视频")
public class VideoVo {
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
	@ApiModelProperty(value="音频")
    private MusicVo musicVo;

    /**
     * 转发次数
     */
    @ApiModelProperty(value="转发次数")
    private Integer transmit_number;
    
    

}
