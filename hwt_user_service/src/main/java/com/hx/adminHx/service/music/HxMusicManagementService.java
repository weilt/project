package com.hx.adminHx.service.music;

import com.hwt.domain.entity.user.video.vo.HxMusicVo;
import com.hx.core.page.asyn.PageResult;

import java.util.List;
import java.util.Map;

public interface HxMusicManagementService {

    /**
     * 查询所有数据
     * @param map
     * @return
     */
    PageResult selectQuery(Map<String, Object> map);

    /**
     * 批量删除操作
     * @param list
     * @return
     */
    boolean musicDelete(List<Integer> list);


    /**
     * 批量修改音频
     * @param
     */
    void updateByMusic(List<HxMusicVo> hxMusicVos);

    /**
     * 修改-隐藏-删除 功能
     * @param map
     * @return
     */
    HxMusicVo selectModifyHideDelete(Map<String, Object> map);

    /**
     * 批量查询
     * @param ids
     * @return
     */
    List<HxMusicVo> batchSelectDetails(Integer[] ids);
}
