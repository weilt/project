package com.hwt.domain.entity.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author 
 */
public class HxUserInfo implements Serializable {
    private Long user_info_id;

    /**
     * 外键
     */
    private Long user_id;

    /**
     * 推荐人账号 - 预留的 便于后面的推广信息
     */
    private String referrer_account_id;

    /**
     * 账号
     */
    private String account;

    /**
     * 云信id
     */
    private String im_id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户类型 - （0-普通用户，1-VIP用户，2-vip2用户...）
     */
    private Integer userType;

    /**
     * 1-初级（15秒） 2-中等（60秒） 3-高级（能直播）
     */
    private Integer viode_grade;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户实名
     */
    private String username;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
   	@DateTimeFormat( pattern = "yyyy-MM-dd")
    private Date user_birthday;

    /**
     * 用户性别 0-人妖 1-男 2-女
     */
    private Integer user_sex;

    /**
     * 个性签名
     */
    private String user_autograph;

    /**
     * 职业
     */
    private String user_profession;

    /**
     * 用户头像地址
     */
    private String user_icon;

    /**
     * 用户情感 0-保密 1-单身2-离异 3-热恋 4-已婚
     */
    private Integer user_emotion;

    /**
     * 小视频封面
     */
    private String video_cover;

    /**
     * 朋友圈封面
     */
    private String friend_circle_cover;

    /**
     * 用户地区 - 国
     */
    private String user_area_state;

    /**
     * 用户地区 - 国 - 名称
     */
    private String user_area_state_name;

    /**
     * 用户地区 - 省
     */
    private String user_area_province;

    /**
     * 用户地区 - 省 - 名称
     */
    private String user_area_province_name;

    /**
     * 用户地区 - 市
     */
    private String user_area_city;

    /**
     * 用户地区 - 市 - 名称
     */
    private String user_area_city_name;

    /**
     * 用户地区 - 区
     */
    private String user_area_district;

    /**
     * 用户地区 - 区 - 名称
     */
    private String user_area_district_name;

    /**
     * 最后一次所在位置的经度
     */
    private String last_longitude;

    /**
     * 最后一次所在位置的维度
     */
    private String last_latitude;

    private static final long serialVersionUID = 1L;

    public Long getUser_info_id() {
        return user_info_id;
    }

    public void setUser_info_id(Long user_info_id) {
        this.user_info_id = user_info_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getReferrer_account_id() {
        return referrer_account_id;
    }

    public void setReferrer_account_id(String referrer_account_id) {
        this.referrer_account_id = referrer_account_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIm_id() {
        return im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getViode_grade() {
        return viode_grade;
    }

    public void setViode_grade(Integer viode_grade) {
        this.viode_grade = viode_grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(Date user_birthday) {
        this.user_birthday = user_birthday;
    }

    public Integer getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(Integer user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_autograph() {
        return user_autograph;
    }

    public void setUser_autograph(String user_autograph) {
        this.user_autograph = user_autograph;
    }

    public String getUser_profession() {
        return user_profession;
    }

    public void setUser_profession(String user_profession) {
        this.user_profession = user_profession;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public Integer getUser_emotion() {
        return user_emotion;
    }

    public void setUser_emotion(Integer user_emotion) {
        this.user_emotion = user_emotion;
    }

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public String getFriend_circle_cover() {
        return friend_circle_cover;
    }

    public void setFriend_circle_cover(String friend_circle_cover) {
        this.friend_circle_cover = friend_circle_cover;
    }

    public String getUser_area_state() {
        return user_area_state;
    }

    public void setUser_area_state(String user_area_state) {
        this.user_area_state = user_area_state;
    }

    public String getUser_area_state_name() {
        return user_area_state_name;
    }

    public void setUser_area_state_name(String user_area_state_name) {
        this.user_area_state_name = user_area_state_name;
    }

    public String getUser_area_province() {
        return user_area_province;
    }

    public void setUser_area_province(String user_area_province) {
        this.user_area_province = user_area_province;
    }

    public String getUser_area_province_name() {
        return user_area_province_name;
    }

    public void setUser_area_province_name(String user_area_province_name) {
        this.user_area_province_name = user_area_province_name;
    }

    public String getUser_area_city() {
        return user_area_city;
    }

    public void setUser_area_city(String user_area_city) {
        this.user_area_city = user_area_city;
    }

    public String getUser_area_city_name() {
        return user_area_city_name;
    }

    public void setUser_area_city_name(String user_area_city_name) {
        this.user_area_city_name = user_area_city_name;
    }

    public String getUser_area_district() {
        return user_area_district;
    }

    public void setUser_area_district(String user_area_district) {
        this.user_area_district = user_area_district;
    }

    public String getUser_area_district_name() {
        return user_area_district_name;
    }

    public void setUser_area_district_name(String user_area_district_name) {
        this.user_area_district_name = user_area_district_name;
    }

    public String getLast_longitude() {
        return last_longitude;
    }

    public void setLast_longitude(String last_longitude) {
        this.last_longitude = last_longitude;
    }

    public String getLast_latitude() {
        return last_latitude;
    }

    public void setLast_latitude(String last_latitude) {
        this.last_latitude = last_latitude;
    }
}