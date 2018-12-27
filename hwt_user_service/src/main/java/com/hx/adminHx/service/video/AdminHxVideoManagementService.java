package com.hx.adminHx.service.video;

import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoManagementVo;
import com.hx.adminHx.service.vo.HxUserVideoPage;

import java.util.List;
import java.util.Map;

public interface AdminHxVideoManagementService {

    //查询全部
    HxUserVideoPage selectQuery(Map<String, Object> map);
    //同意小视频上传
    void consent(Long video_id, Integer status,String reason ,String handlers);
    //拒绝小视频上传
    void refuse(Long video_id, Integer status, String reason , String handlers) throws Exception;
    //批量审核小视频上传
    Integer batchReview(List<Long> list,String reason ,String handlers);
    //假删除小视频
    void delete(Long video_id ,String handlers);
    //小视频详情
    HxUserVideo selectDetails(Long video_id);
}
