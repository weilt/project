package com.hwt.domain.entity.user.video.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="音乐库")
public class HxMusicVo implements Serializable {
    /**
     * 音乐id
     */
    @ApiModelProperty(value="音乐id")
    private Long music_id;

    /**
     * 音乐地址
     */
    @ApiModelProperty(value="音乐地址")
    private String music_url;

    /**
     * 音乐标题
     */
    @ApiModelProperty(value="音乐标题")
    private String music_title;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long create_time;
    /**
     * 引用次数
     */
    @ApiModelProperty(value="引用次数")
    private Long use_account;
    /**
     * 上传人id
     */
    @ApiModelProperty(value="上传人id")
    private Long user_id;
    /**
     * 是否公开 0-公开 1-隐藏 2-删除
     */
    @ApiModelProperty(value="是否公开 0-公开 1-隐藏 2-删除")
    private Integer is_open;

    /**
     * 音乐标签 1-影视原声,2-日韩,3-生活,4-搞怪,5-流行,6-说唱,7-民谣,8-欧美
     */
    @ApiModelProperty(value="音乐标签 1-影视原声,2-日韩,3-生活,4-搞怪,5-流行,6-说唱,7-民谣,8-欧美,null-其他(无标签)")
    private Integer music_tag;

    /**
     * 音乐封面
     */
    @ApiModelProperty(value="音乐封面")
    private String music_cover;

    /**
     * 音乐时长
     */
    @ApiModelProperty(value="音乐时长")
    private Long music_time;
    /**
     * 音乐作者
     */
    @ApiModelProperty(value="音乐作者")
    private String music_writer;

    private static final long serialVersionUID = 1L;

    public Integer getMusic_tag() {
        return music_tag;
    }

    public void setMusic_tag(Integer music_tag) {
        this.music_tag = music_tag;
    }

    public String getMusic_writer() {
        return music_writer;
    }

    public void setMusic_writer(String music_writer) {
        this.music_writer = music_writer;
    }

    public Long getMusic_time() {
        return music_time;
    }

    public void setMusic_time(Long music_time) {
        this.music_time = music_time;
    }

    public String getMusic_cover() {
        return music_cover;
    }

    public void setMusic_cover(String music_cover) {
        this.music_cover = music_cover;
    }

    public Long getMusic_id() {
        return music_id;
    }

    public void setMusic_id(Long music_id) {
        this.music_id = music_id;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getMusic_title() {
        return music_title;
    }

    public void setMusic_title(String music_title) {
        this.music_title = music_title;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getUse_account() {
        return use_account;
    }

    public void setUse_account(Long use_account) {
        this.use_account = use_account;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getIs_open() {
        return is_open;
    }

    public void setIs_open(Integer is_open) {
        this.is_open = is_open;
    }
}
