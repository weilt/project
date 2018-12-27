package com.hwt.domain.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.user.HxUseReal;
import com.hwt.domain.entity.user.Vo.HxUseRealVo;

@Mapper
public interface HxUseRealMapper {

    int deleteByPrimaryKey(Long user_id);

    int insert(HxUseReal record);

    int insertSelective(HxUseReal record);


    HxUseReal selectByPrimaryKey(Long user_id);


    int updateByPrimaryKeySelective(HxUseReal record);

    int updateByPrimaryKey(HxUseReal record);

    
    /**
     * 根据用户id查询真实信息
     * @param user_id
     * @return
     */
    @Select("select user_id ,real_name, identity_no,identity_positive,identity_negative, identity_hold, present_address ,often_phone from"
    		+ " hx_use_real where user_id = #{user_id}")
	HxUseRealVo queryHxUseRealVoByUserId(Long user_id);
}