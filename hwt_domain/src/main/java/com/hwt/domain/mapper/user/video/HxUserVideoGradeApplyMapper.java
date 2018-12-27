package com.hwt.domain.mapper.user.video;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.user.video.HxUserVideoGradeApply;

@Mapper
public interface HxUserVideoGradeApplyMapper {

    int deleteByPrimaryKey(Long grade_apply_id);

    int insert(HxUserVideoGradeApply record);

    int insertSelective(HxUserVideoGradeApply record);


    HxUserVideoGradeApply selectByPrimaryKey(Long grade_apply_id);


    int updateByPrimaryKeySelective(HxUserVideoGradeApply record);

    int updateByPrimaryKey(HxUserVideoGradeApply record);

    /**
     * 查询最近的一条
     * @param user_id
     * @return
     */
    @Select("select  grade_apply_id, user_id, apply_grade, status, admin_user_id, fail_reason, create_time,"
    		+ " update_time from hx_user_video_grade_apply where user_id = #{user_id} order by grade_apply_id desc limit 0,1")
	HxUserVideoGradeApply selectLatelyByUserId(@Param("user_id")Long user_id);

}