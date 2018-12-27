package com.hwt.domain.mapper.user.music;

import com.hwt.domain.entity.user.video.vo.HxMusicVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface HxUserMusicMapper {

    void insertByMusics(HxMusicVo hxMusicVo);

    @Update("update hx_music set use_account =(use_account + #{i}) where music_id = #{musics}")
    void updateByUse_account(@Param("musics")Long musics, @Param("i")int i);

//    @Select("select * from hx_music where user_admin=1 order by create_time asc limit #{page},#{pageSize}")
    List<HxMusicVo> selectMusicALL(@Param("pageSize")Integer pageSize,@Param("page")Integer page);



    /**
     * 查询音乐被引用的次数
     * @param musicid
     * @return
     */
    @Select("select use_account from hx_music where music_id = #{musicid}")
    Integer selectMusicId(@Param("musicid") Long musicid);

    @Update("update hx_music set use_account =(use_account - #{i}) where music_id = #{musics}")
    void updateOrUse_account(@Param("musics")Long musics, @Param("i")int i);

    @Update("update hx_music set is_open = 3,use_account =0 where music_id = #{musicid}")
    void updateByDelete(@Param("musicid") Long musicid);

    /**
     * 初始化搜索 和小条件搜索
     * @param map
     * @return
     */
    List<HxMusicVo> selectQuery(Map<String, Object> map);
    Integer selectCount(Map<String, Object> map);
    /**
     * 歌手名搜索 和小条件搜索
     * @param map
     * @return
     */
    List<HxMusicVo> selectSingerQuery(Map<String, Object> map);
    Integer selectSingerCount(Map<String, Object> map);//个数
    /**
     * 歌曲名搜索 和小条件搜索
     * @param map
     * @return
     */
    List<HxMusicVo> selectBySongQuery(Map<String, Object> map);
    Integer selectBySongCount(Map<String, Object> map);
    /**
     * 用户id搜索 和小条件搜索
     * @param map
     * @return
     */
    List<HxMusicVo> selectByUserQuery(Map<String, Object> map);
    Integer selectByUserCount(Map<String, Object> map);

    /**
     * 批量删除音频
     * @param list
     */
    void deleteByMusic(@Param("list") List<Integer> list);

    /**
     * 查询选中音频是否存在
     * @param list
     * @return
     */
    Integer selectByMusicId(@Param("list")List<Integer> list);
    /**
     * 查询选中音频是否存在
     * @param music_id
     * @return
     */
    HxMusicVo selectByMusic(@Param("music_id")Long music_id);

    /**
     * 批量修改音频
     * @param music_id
     * @param music_tag
     * @param music_title
     * @param music_writer
     * @param music_cover
     */
    void updateByMusic(@Param("music_id")Long music_id,@Param("music_tag")Integer music_tag,
                       @Param("music_title")String music_title,@Param("music_writer") String music_writer,
                       @Param("music_cover")String music_cover);

    /**
     * 修改单个音频
     * @param map
     */
    void updateByOneMusic(Map<String, Object> map);

    /**
     * 隐藏音频
     * @param music_id
     */
    void updateByOneIsopen(@Param("music_id")Long music_id,@Param("is_open")Integer is_open);

    /**
     * 删除音频
     * @param music_id
     */
    void updateByOneDelete(@Param("music_id")Long music_id);

    List<HxMusicVo> batchSelectDetails(@Param("ids") Integer[] ids);



}
