package com.hwt.domain.mapper.information;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.information.HwInformationGood;

/**
 * 资讯点赞
 * @author Administrator
 *
 */
@Mapper
public interface HwInformationGoodMapper {

    int insert(HwInformationGood record);

    int insertSelective(HwInformationGood record);



    int updateByPrimaryKeySelective(HwInformationGood record);

    int updateByPrimaryKey(HwInformationGood record);

    /**
     * 根据键查询
     * @param user_id
     * @param information_id
     * @return
     */
    @Select("select user_id, information_id, create_time from hw_information_good where user_id = #{user_id} and information_id = #{information_id}")
	HwInformationGood selectByUser_idAndInformation_id(@Param("user_id")Long user_id, @Param("information_id")Long information_id);

    /**
     * 添加
     * @param user_id
     * @param information_id
     * @param create_time
     */
    @Insert("insert into hw_information_good (user_id, information_id, create_time) VALUES (#{user_id} ,#{information_id},#{create_time})")
	void insertGood(@Param("user_id")Long user_id, @Param("information_id")Long information_id, @Param("create_time")Long create_time);

    @Delete("delete  from hw_information_good where user_id = #{user_id} and information_id = #{information_id}")
	int deleteGood(@Param("user_id")Long user_id, @Param("information_id")Long information_id);
    
}