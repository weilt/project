package com.hwt.domain.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface AdminHomeMapper {

    @Select("SELECT count(0) FROM hx_user WHERE create_time BETWEEN #{present} AND #{twenty_three}")
    Integer selectToday(@Param("present")Date present,@Param("twenty_three")Date twenty_three);

    @Select("SELECT count(0) FROM hx_user ")
    Integer userCount();

    @Select("SELECT count(0) FROM hw_travel_bureau WHERE state=0")
    Integer selectBureau();

    @Select("SELECT count(0) FROM hx_cicerone_info WHERE status=1")
    Integer selectInfo();

    @Select("SELECT count(0) FROM hw_order WHERE status=4 AND cicerone_id=0")
    Integer selectRefund();

    @Select("SELECT count(0) FROM hx_user_video WHERE status=1")
    Integer selectVideo();

    @Select("SELECT count(0) FROM hx_user_wallet_bill WHERE status=0 AND bill_type=5")
    Integer selectAccount();

    //导游订单
    @Select("SELECT count(0) FROM hw_order WHERE accept_time BETWEEN #{present} AND #{twenty_three} and bureau_id=0 AND status=2")
    Integer selectGuideCount(@Param("present")Long present,@Param("twenty_three")Long twenty_three);
    //旅行社订单
    @Select("SELECT count(0) FROM hw_order WHERE accept_time BETWEEN #{present} AND #{twenty_three} and cicerone_id=0 AND status=2")
    Integer selectCircuitCount(@Param("present")Long present, @Param("twenty_three")Long twenty_three);

    @Select("SELECT SUM(payment) as payment FROM hw_order WHERE completion_time BETWEEN #{present} AND #{twenty_three} AND status=3")
    Double selectOrderSum(@Param("present")Long present, @Param("twenty_three")Long twenty_three);
}
