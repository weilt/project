package com.hwt.domain.entity.advertisement;

import java.io.Serializable;

/**
 * @author 
 */
public class HwAdvertisement implements Serializable {
    /**
     * 广告id
     */
    private Long ad_id;

    /**
     * 广告类型  
     */
    private Long ad_type;

    /**
     * 广告位置
     */
    private Long ad_position;

    /**
     * 标题
     */
    private String title;

    /**
     * 真实id 0-不是我们平台的
     */
    private Long real_id;

    /**
     * 链接
     */
    private String ad_url;

    /**
     * 图片
     */
    private String image;

    /**
     * 排序，越大越靠后
     */
    private Integer sort;

    /**
     * 点击次数
     */
    private Long click_num;

    /**
     * 创建时间
     */
    private Long create_time;

    /**
     * 开始时间
     */
    private Long start_time;

    /**
     * 结束时间
     */
    private Long end_time;

    /**
     * 是否隐藏  0-否 1-是
     */
    private Integer is_hide;

    /**
     * 是否上线   0-否 1-是
     */
    private Integer is_online;

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

    public Long getAd_position() {
        return ad_position;
    }

    public void setAd_position(Long ad_position) {
        this.ad_position = ad_position;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getClick_num() {
        return click_num;
    }

    public void setClick_num(Long click_num) {
        this.click_num = click_num;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Integer getIs_hide() {
        return is_hide;
    }

    public void setIs_hide(Integer is_hide) {
        this.is_hide = is_hide;
    }

    public Integer getIs_online() {
        return is_online;
    }

    public void setIs_online(Integer is_online) {
        this.is_online = is_online;
    }
}