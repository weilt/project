package com.hwt.domain.mapper.user.video;

import java.util.List;
import java.util.Map;

import com.hwt.domain.entity.user.video.vo.HxMusicVo;
import org.apache.ibatis.annotations.*;

import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.vo.HxBigVideo;
import com.hwt.domain.entity.user.video.vo.HxUserVideoVo;
import com.hwt.domain.entity.user.video.vo.HxVideoVo;

@Mapper
public interface HxUserVideoMapper {

    int deleteByPrimaryKey(Long video_id);

    int insert(HxUserVideo record);

    int insertSelective(HxUserVideo record);


    HxUserVideo selectByPrimaryKey(Long video_id);


    int updateByPrimaryKeySelective(HxUserVideo record);
    
    int updateByPrimaryKey(HxUserVideo record);

	/**
	 * 游客查看
	 * @param video_user_id
	 * @param last_video_id
	 * @param pageSize
	 * @return
	 */
	List<HxVideoVo> onlooker_query(Map<String, Object> map);

	/**
	 * 查看自己的
	 * @param user_id
	 * @param startNum
	 * @param pageSize
	 * @return
	 */
	@Select("select * from hx_user_video where user_id = #{user_id} and video_id < #{last_video_id} order by create_time desc limit 0 , #{pageSize}")
	List<HxUserVideo> own_query(@Param("user_id")Long user_id, @Param("last_video_id")Integer last_video_id, @Param("pageSize")Integer pageSize);
	
	/**
	 * 查看自己的第一页
	 * @param user_id
	 * @param startNum
	 * @param pageSize
	 * @return
	 */
	@Select("select * from hx_user_video where user_id = #{user_id} order by create_time desc limit 0 , #{pageSize}")
	List<HxUserVideo> own_query_1(@Param("user_id")Long user_id, @Param("pageSize")Integer pageSize);

	/**
	 * 评论次数加1
	 * @param video_id
	 */
	@Update("update hx_user_video set comment_num = comment_num + 1 where video_id = #{video_id}")
	void commentAdd1(@Param("video_id")Long video_id);
	
	/**
	 * 评论次数减1
	 * @param video_id
	 */
	@Update("update hx_user_video set comment_num = comment_num - 1 where video_id = #{video_id} and comment_num > 0")
	void commentReduce1(@Param("video_id")Long video_id);
	
	/**
	 * 喜欢次数加1
	 * @param video_id
	 */
	@Update("update hx_user_video set good_num = good_num + 1 where video_id = #{video_id}")
	void likeAdd1(@Param("video_id")Long video_id);
	
	/**
	 * 喜欢次数减1
	 * @param video_id
	 */
	@Update("update hx_user_video set good_num = good_num - 1 where video_id = #{video_id} and good_num > 0")
	void likeReduce1(@Param("video_id")Long video_id);

	/**
	 * 查询自己发的小视频数量
	 * @param user_id
	 * @return
	 */
	@Select("select count(video_id) from hx_user_video where user_id = #{user_id} and status = 2 and is_hide = 0")
	Long total_my(Long user_id);

	/**
	 * 关联查询，不存在用户id
	 * @param map
	 * @return
	 */
	List<HxVideoVo> name_query_not_user_id(Map<String, Object> map);

	/**
	 * 关联查询，存在用户id
	 * @param map
	 * @return
	 */
	List<HxVideoVo> name_query(Map<String, Object> map);

	/**
	 * 返回主键添加
	 * @param hxUserVideo
	 * @return 
	 */
	void insertSelectiveBcakId(HxUserVideo hxUserVideo);

	/**
	 * 根据小视频id查询
	 * @param user_id
	 * @param video_id
	 * @return
	 */
	HxVideoVo query_by_id(@Param("user_id")Long user_id, @Param("video_id")Long video_id);

	/**
	 * 查询关注人列表的小视频
	 * @param map
	 * @return
	 */
	List<HxVideoVo> query_follow_video(Map<String, Object> map);

	/**
	 * 添加多个
	 * @param list
	 */
	void addList(@Param("list")List<HxUserVideo> list);

	/**
	 * 删除所有长视频 根据类型和id
	 * @param spotId
	 * @param i
	 */
	@Delete("delete from hx_user_video where name_id = #{name_id} and name_type = #{name_type}")
	void deleteAllBig(@Param("name_id")Long spotId, @Param("name_type")int i);

	/**
	 * 通过id和type查询长视频
	 * @param spotId
	 * @param i
	 * @return
	 */
	@Select("select image,content,file_id,`dec` from hx_user_video where name_id = #{name_id} and name_type = #{name_type}")
	List<HxUserVideo> selectBigVideoByIdAndType(@Param("name_id")Long spotId, @Param("name_type")int i);

	
	/**
	 * 根据类型和类型id查询长视频
	 * @param map
	 * @return
	 */
	List<HxBigVideo> searchBigVideo(Map<String, Object> map);

	/**
	 * 查询音乐库中 音频是否存在
	 * @param musics
	 */
	@Select("select count(music_id) from hx_music where music_id = #{musics}")
	Integer selectMusic(@Param("musics")Long musics);

	/**
	 * 查询小视频的log
	 * @param map
	 * @return
	 */
	HxMusicVo selectVideo(Map<String, Object> map);

	/**
	 * 小视频的同款列表
	 * @param map
	 * @return
	 */
	List<HxVideoVo> selectDetails(Map<String, Object> map);

	/**
	 * 查询Music_id对应的音乐
	 * @param music_id
	 * @return
	 */
	@Select("select * from hx_music where music_id = #{music_id}")
	HxMusicVo selectMusicID(@Param("music_id")Long music_id);

	/**
	 * 修改音乐的id
	 * @param music_id
	 */
	@Update("update hx_user_video set music_id = #{music_id} where video_id=#{video_id}")
	void UpdateByMusicId(@Param("music_id")Long music_id,@Param("video_id")Long video_id);
}