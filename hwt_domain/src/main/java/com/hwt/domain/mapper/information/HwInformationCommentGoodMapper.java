package com.hwt.domain.mapper.information;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hwt.domain.entity.information.HwInformationCommentGood;

/**
 * 资讯评论点赞
 * @author Administrator
 *
 */
@Mapper
public interface HwInformationCommentGoodMapper {

    int insert(HwInformationCommentGood record);

    int insertSelective(HwInformationCommentGood record);



    int updateByPrimaryKeySelective(HwInformationCommentGood record);

    int updateByPrimaryKey(HwInformationCommentGood record);

    /**
     * 添加点赞
     * @param user_id
     * @param comment_id
     * @param create_time
     */
    @Insert("insert into hw_information_comment_good (user_id, comment_id, create_time) VALUES (#{user_id} ,#{comment_id},#{create_time})")
   	void insertGood(@Param("user_id")Long user_id, @Param("comment_id")Long comment_id, @Param("create_time")Long create_time);

    /**
     * 点赞取消
     * @param user_id
     * @param comment_id
     * @return
     */
    @Delete("delete from hw_information_comment_good where user_id = #{user_id} and comment_id = #{comment_id}")
	int information_comment_not_good(@Param("user_id")Long user_id, @Param("comment_id")Long comment_id);
}