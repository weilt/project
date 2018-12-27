package com.hwt.domain.mapper.information;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hwt.domain.entity.information.HwInformationComment;
import com.hwt.domain.entity.information.vo.HwInformationCommentAdminVo;

@Mapper
public interface HwInformationCommentMapper {

    int deleteByPrimaryKey(Long comment_id);

    int insert(HwInformationComment record);

    int insertSelective(HwInformationComment record);


    HwInformationComment selectByPrimaryKey(Long comment_id);


    int updateByPrimaryKeySelective(HwInformationComment record);

    int updateByPrimaryKey(HwInformationComment record);

    
    /**
     * 查询评论总条数根据资讯id  返回给后台
     * @param information_id
     * @return
     */
    @Select("select count(comment_id) from hw_information_comment where information_id = #{information_id} and parent_comment_id = 0")
	Long query_comment_count_admin(@Param("information_id")Long information_id);

	/**
	 * 查询数据   返回给后台
	 * @param map
	 * @return
	 */
	List<HwInformationCommentAdminVo> query_comment_admin(Map<String, Object> map);
	
	/**
	 * 返回主键添加
	 * @param record
	 * @return
	 */
	int insertBackId(HwInformationComment record);

	/**
	 * 隐藏评论
	 * @param comment_id
	 */
	@Update("update hw_information_comment set is_hide = 1 where comment_id = #{comment_id}")
	void comment_hide(@Param("comment_id")Long comment_id);

	/**
	 * 恢复评论
	 * @param comment_id
	 */
	@Update("update hw_information_comment set is_hide = 0 where comment_id = #{comment_id}")
	void comment_not_hide(@Param("comment_id")Long comment_id);

	@Delete("delete from hw_information_comment where relation_comment_id = #{comment_id}")
	void deleteByRelation(@Param("comment_id") Long comment_id);

	/**
	 * 顶级回复加1
	 * @param i  1或-1
	 * @param parent_comment_id
	 */
	@Update("update hw_information_comment set comment_num = comment_num + #{num} where comment_id = #{relation_comment_id}")
	void parent_comment_num_add(@Param("relation_comment_id")Long relation_comment_id, @Param("num")int i);

	/**
	 * 点赞次数 加减
	 * @param comment_id
	 * @param num   1或者-1
	 */
	@Update("update hw_information_comment set good_num = good_num + #{num} where comment_id = #{comment_id}")
	void addGood(@Param("comment_id")Long comment_id, @Param("num")int num);
}