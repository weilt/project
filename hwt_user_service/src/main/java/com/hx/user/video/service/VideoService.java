package com.hx.user.video.service;

import java.util.List;
import java.util.Map;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoComment;
import com.hwt.domain.entity.user.video.vo.*;

public interface VideoService {

	/**
	 * 发布小视频 
	 * @param loginVo 
	 */
	void insert(LoginVo loginVo, HxUserVideo hxUserVideo,HxMusicVo hxMusicVo)  throws Exception;

	
	/**
	 * 看客查看
	 */
	List<HxVideoVo> onlooker_query(Map<String, Object> map);


	/**
	 * 查看自己的小视频
	 * @param user_id
	 * @param last_video_id
	 * @param pageSize
	 * @return
	 */
	List<HxUserVideo> own_query(Long user_id, Integer last_video_id, Integer pageSize);

	/**
	 * 添加喜欢
	 * @param loginVo
	 * @param video_id
	 * @param received_im_id 
	 */
	void add_like(LoginVo loginVo, Long video_id, String received_im_id) throws Exception ;


	/**
	 * 删除喜欢
	 * @param user_id
	 * @param video_id
	 */
	void delete_like(Long user_id, Long video_id);

	/**
	 * 查询喜欢
	 * @param map
	 * @param pageSize 
	 * @param last_video_id 
	 * @return
	 */
	List<HxUserVideoLikeListVo> like_query(Map<String, Object> map);


	/**
	 * 删除
	 * @param user_id
	 * @param video_id
	 */
	void delete(Long user_id, Long video_id);


	/**
	 * 添加关注
	 * @param user_id
	 * @param follow_user_id
	 */
	void add_follow(Long user_id, Long follow_user_id);


	/**
	 * 删除关注
	 * @param user_id
	 * @param follow_user_id
	 */
	void delete_follow(Long user_id, Long follow_user_id);

	/**
	 * 查询关注列表
	 * @param map
	 * @return
	 */
	List<VideoFollowVo> query_follow(Map<String, Object> map);


	/**
	 * 评论
	 * @param loginVo
	 * @param video_id
	 * @param content
	 * @param type
	 * @param parent_id
	 * @param parent_user_id
	 * @param received_im_id 
	 */
	HxUserVideoComment comment(LoginVo loginVo, Long video_id, String content, Integer type, Long parent_id, Long parent_user_id,String received_im_id ) throws Exception ;


	/**
	 * 删除评论
	 * @param user_id
	 * @param video_comment_id
	 */
	void delete_comment(Long user_id, Long video_comment_id);


	/**
	 * 查看评论
	 * @param map
	 * @return
	 */
	List<HxUserVideoCommentVo> query_comment(Map<String, Object> map);


	/**
	 * 小视频个人首页数据返回
	 * @param user_id
	 * @return
	 */
	HxUserVideoBasicNumShow query_own_basicNum(Long user_id);


	/**
	 * 小视频个人首页数据返回
	 * @param other_user_id
	 */
	HxUserVideoOtherBasicNumShow query_other_basicNum(Long user_id, Long other_user_id);


	/**
	 * 通过关联id查询
	 * @param map
	 * @return
	 */
	List<HxVideoVo> name_query(Map<String, Object> map);


	/**
	 * 根据小视频id查询
	 * @param user_id
	 * @param video_id
	 * @return
	 */
	HxVideoVo query_by_id(Long user_id, Long video_id);


	/**
	 * 查询关注人列表的小视频
	 * @param map
	 * @return
	 */
	List<HxVideoVo> query_follow_video(Map<String, Object> map);


	/**
	 * 根据video_id 查询小视频
	 * @param user_id
	 * @param video_id
	 * @return
	 */
	HxVideoVo msg_like_video(Long user_id, Long video_id);

	/**
	 * 根据评论id查询
	 * @param user_id
	 * @param comment_id
	 * @return
	 */
	MsgCommentVideoVo msg_comment_video(Long user_id, Long comment_id);

	/**
	 * 分页查看 音乐库中的数据
	 * @param pageSize
	 * @return
	 */
	List<HxMusicVo> music_query(Integer pageSize,Integer page);

	/**
	 * 分页查询 音乐库和视频详情的log
	 * @param map
	 * @return
	 */
	HxMusicVo music_shinee(Map<String, Object> map);

	/**
	 * 同款小视频
	 * @param map
	 * @return
	 */
	List<HxVideoVo> music_video(Map<String, Object> map);
}
