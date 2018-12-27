package com.hwt.domain.mapper.user.video;

import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoManagementVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface HxUserVideoManagementMapper {
    /**
     * 查询所有 (基本信息)
     * @return
     */
    @Select("SELECT b.account,a.* FROM hx_user_video a , hx_user b WHERE a.user_id = b.user_id and video_type =1")
    List<HxUserVideoManagementVo> selectQuery();

    @Select("SELECT b.account,a.* FROM hx_user_video a , hx_user b WHERE a.user_id=b.user_id and a.video_id = #{video_id}")
    HxUserVideoManagementVo selectQueryAll(@Param("video_id") Long video_id);

    //满足条件的数据 (条件查询)status不为空
    List<Map<String, Object>> selectQueryByMap(Map<String, Object> map);

    //满足条件的数据 (条件查询)status为空
    @Select("select b.account,a.* from  hx_user_video a, hx_user b where  a.user_id = b.user_id order by ${orderBy} limit #{startNum},#{pageSize}")
    List<Map<String, Object>> selectByMap(Map<String, Object> map);

    //满足条件的总条数
    int selectQueryCountByMap(Map<String, Object> map);

    //小视频审核通过
    @Update("UPDATE hx_user_video SET status = #{status} WHERE video_id = #{video_id}")
    void updateConsent(@Param("video_id") Long video_id, @Param("status")Integer status);

    //插入审核表
    @Insert("INSERT INTO hx_user_video_audit (video_id, handlers ,operation_time,remark) VALUES ( #{video_id}, #{handlers},#{time_long},#{reason})")
    void insert(@Param("video_id")Long video_id, @Param("handlers")String handlers,@Param("reason")String reason, @Param("time_long")long time_long);

    //拒绝小视频
    @Update("UPDATE hx_user_video SET status = #{status},reason = #{reason} WHERE video_id = #{video_id}")
    void updateRefuse(@Param("video_id")Long video_id,@Param("reason")String reason, @Param("status")Integer status);

    //查询status 是否为1-未审核
    @Select("select count(0) from hx_user_video a LEFT JOIN hx_user_video_audit b ON a.video_id = b.video_id WHERE a.status=1 and a.video_id =#{video_id} ")
    Long selectStatus(@Param("video_id")Long video_id);

    //假删除 修改状态
    @Update("UPDATE hx_user_video SET status = 4 WHERE video_id = #{video_id}")
    void delete(@Param("video_id")Long video_id);
    //假删除 修改审核表
    @Update("UPDATE hx_user_video_audit SET operation_time = #{time_long},handlers = #{handlers} WHERE video_id = #{video_id}")
    void deleteAudit(@Param("video_id")Long video_id ,@Param("time_long")Long time_long,@Param("handlers")String handlers);

    @Select("select * from hx_user_video a LEFT JOIN hx_user_video_audit b ON a.video_id = b.video_id WHERE a.video_id = #{video_id}")
    HxUserVideo selectDetails(@Param("video_id")Long video_id);

    @Select("select count(0) from hx_user_video_audit where video_id = #{video_id}")
    Long selectByAudit(@Param("video_id")Long video_id);

    /**
     * 根据音频id 修改音频的状态
     * @param music_id
     */
    @Update("update hx_music set is_open = 1 where music_id = #{music_id}")
    void updateMusicStatus(@Param("music_id")Long music_id);
}
