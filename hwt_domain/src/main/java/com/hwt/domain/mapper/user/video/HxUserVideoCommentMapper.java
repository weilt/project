package com.hwt.domain.mapper.user.video;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.user.video.HxUserVideoComment;
import com.hwt.domain.entity.user.video.vo.HxUserVideoCommentVo;
import com.hwt.domain.entity.user.video.vo.VideoUserVo;

@Mapper
public interface HxUserVideoCommentMapper {

    int deleteByPrimaryKey(Long video_comment_id);

    int insert(HxUserVideoComment record);

    int insertSelective(HxUserVideoComment record);


    HxUserVideoComment selectByPrimaryKey(Long video_comment_id);


    int updateByPrimaryKeySelective(HxUserVideoComment record);

    int updateByPrimaryKey(HxUserVideoComment record);

    /**
     * 查看评论
     * @param map
     * @return
     */
	List<HxUserVideoCommentVo> query_comment(Map<String, Object> map);

	/**
	 * 返回主键添加
	 * @param hxUserVideoComment
	 */
	void insertBackId(HxUserVideoComment hxUserVideoComment);

	/**
	 * 获取评论次数
	 * @param user_id
	 * @return
	 */
	@Select("select count(a.video_comment_id) from hx_user_video_comment a , hx_user_video b where a.video_id = b.video_id and  b.user_id = #{user_id} and a.user_id!=#{user_id}")
	Long total_comment(@Param("user_id")Long user_id);

	
	/**
	 * 查询小视频用户信息
	 * @param user_id
	 * @return
	 */
	@Select("select user_id, user_account, user_icon, nickname, im_id from hx_user_info where user_id = #{user_id}")
	VideoUserVo queryVideoUserVo(@Param("user_id")Long user_id);

	
	/**
	 * 通过id查询
	 * @param comment_id
	 * @return
	 */
	HxUserVideoCommentVo query_comment_by_id(@Param("comment_id")Long comment_id);
}