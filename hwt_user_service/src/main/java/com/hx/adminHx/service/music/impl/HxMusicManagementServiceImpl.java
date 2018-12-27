package com.hx.adminHx.service.music.impl;

import com.hwt.domain.entity.user.video.vo.HxMusicVo;
import com.hwt.domain.mapper.user.music.HxUserMusicMapper;
import com.hx.adminHx.service.music.HxMusicManagementService;
import com.hx.core.exception.BaseException;
import com.hx.core.page.asyn.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HxMusicManagementServiceImpl implements HxMusicManagementService {
    @Autowired
    HxUserMusicMapper hxUserMusicMapper;

    @Override
    public PageResult selectQuery(Map<String, Object> map) {
//        if (Integer.parseInt(map.get("music_tag").toString()) == 0){
//                map.put("music_tag",null);
//        }
        PageResult pageResult = new PageResult();
        List<HxMusicVo> list = new ArrayList<>();
        //总记录数
        Integer count = 0;
        //1-歌手名搜索
        if (Integer.parseInt(map.get("song").toString()) == 1){
            if (map.get("music_writer2") == null){
                throw new RuntimeException("请输入歌手名称");
            }
            list= hxUserMusicMapper.selectSingerQuery(map);
            count = hxUserMusicMapper.selectSingerCount(map);

        }
        //2-歌曲名搜索
        if (Integer.parseInt(map.get("song").toString()) == 2){
            if (map.get("music_title2") == null){
                throw new RuntimeException("请输入歌曲名称");
            }
            list= hxUserMusicMapper.selectBySongQuery(map);
            count = hxUserMusicMapper.selectBySongCount(map);

        }
        // 3-用户ID搜索
        if (Integer.parseInt(map.get("song").toString()) == 3){
            if (map.get("user_id") == null){
                throw new RuntimeException("请输入用户id");
            }
            list= hxUserMusicMapper.selectByUserQuery(map);
            count = hxUserMusicMapper.selectByUserCount(map);

        }
        // 0-没有搜索 为初始化搜索 和小条件搜索
        if (Integer.parseInt(map.get("song").toString()) == 0){
            list= hxUserMusicMapper.selectQuery(map);
            count = hxUserMusicMapper.selectCount(map);
        }

        pageResult.setDataList(list);
        pageResult.setCount(count);


        return pageResult;
    }

    @Override
    @Transactional
    public boolean musicDelete(List<Integer> list) {
       Integer count = hxUserMusicMapper.selectByMusicId(list);
       if (list.size() != count){
           throw new BaseException("你选中的音频有不存在参数 请刷新页面重试");
       }
        //for (Integer music_id:list){
            //删除music
            try {
                hxUserMusicMapper.deleteByMusic(list);
            }catch (Exception e){
                throw new BaseException("调试错误(请重试)!!");
            }
      //  }

        return true;
    }

    /**
     * 批量修改音频
     * @param hxMusicVos
     */
    @Override
    @Transactional
    public void updateByMusic(List<HxMusicVo> hxMusicVos) {
        for (HxMusicVo hxMusicVo : hxMusicVos) {
            if (hxMusicVo.getMusic_id() == null){
                throw new BaseException("请选中音频");
            }
            HxMusicVo Music = hxUserMusicMapper.selectByMusic(hxMusicVo.getMusic_id());
            if (Music.getMusic_id() == null){
                throw new BaseException("你选中的音频有不存在参数 请刷新页面重新修改");
            }
        }
        for (HxMusicVo hxMusicVo : hxMusicVos) {
           Long music_id = hxMusicVo.getMusic_id();
           Integer music_tag = hxMusicVo.getMusic_tag();
           String music_title = hxMusicVo.getMusic_title();
           String music_writer = hxMusicVo.getMusic_writer();
           String music_cover = hxMusicVo.getMusic_cover();
        hxUserMusicMapper.updateByMusic(music_id,music_tag,music_title,music_writer,music_cover);
        }
    }

    /**
     * 修改-隐藏-删除 功能
     * @param map
     * @return
     */
    @Override
    @Transactional
    public HxMusicVo selectModifyHideDelete(Map<String, Object> map) {
        if (map.get("mhd") != null){
            Long music_id = Long.parseLong(map.get("music_id").toString());
            if (Integer.parseInt(map.get("mhd").toString()) == 1){//修改
                hxUserMusicMapper.updateByOneMusic(map);
                return null;
            }
            if (Integer.parseInt(map.get("mhd").toString()) == 2){//隐藏
                HxMusicVo hxMusicVo =  hxUserMusicMapper.selectByMusic(music_id);
                if (hxMusicVo.getIs_open() == 0){//公开状态
                    hxUserMusicMapper.updateByOneIsopen(music_id,1);
                }else if (hxMusicVo.getIs_open() == 1){//隐藏状态
                    hxUserMusicMapper.updateByOneIsopen(music_id,0);
                }else{
                    throw new BaseException("当前音频处于未知状态请重新选择");
                }
                return null;
            }
            if (Integer.parseInt(map.get("mhd").toString()) == 3){//删除
                hxUserMusicMapper.updateByOneDelete(music_id);
                return null;
            }
            if (Integer.parseInt(map.get("mhd").toString()) == 4){//查询单挑数据
                HxMusicVo hxMusicVo = hxUserMusicMapper.selectByMusic(music_id);
                return hxMusicVo;
            }
        }else {
            throw new BaseException("你的参数mhd 或 id 为null 请重新选择");
        }


        return null;
    }

    @Override
    public List<HxMusicVo> batchSelectDetails(Integer[] ids) {
        List<HxMusicVo> hxMusicVos= hxUserMusicMapper.batchSelectDetails(ids);
        return hxMusicVos;
    }
}
