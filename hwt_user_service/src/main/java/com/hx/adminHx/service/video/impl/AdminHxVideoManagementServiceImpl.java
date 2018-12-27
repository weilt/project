package com.hx.adminHx.service.video.impl;

import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoManagementVo;
import com.hwt.domain.mapper.user.HxUserMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoManagementMapper;
import com.hx.adminHx.service.video.AdminHxVideoManagementService;
import com.hx.adminHx.service.vo.HxUserVideoPage;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.wyim.notice.SentNotice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class AdminHxVideoManagementServiceImpl implements AdminHxVideoManagementService {

    @Autowired
    private HxUserMapper hxUserMapper;
    @Autowired
    private HxUserVideoManagementMapper hxUserVideoManagementMapper;
    @Autowired
    private SentNotice sentNotice;

    // date要转换的long类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    @Override
    public HxUserVideoPage selectQuery(Map<String, Object> map) {
        map.put("date1",null);
        if (map.get("date") != null && map.get("date") != ""){
            long date = dateToLong((Date) map.get("date"));
            long date1 = dateToLong((Date) map.get("date"));
            date1+=86399999l;//一天减去1毫秒的时间
            map.put("date",date);
            map.put("date1",date1);
        }

        //待确认
        Integer status1 = 0;

        //待开始
        Integer status2 = 0;

        //已完成
        Integer status3 = 0;

        //总数
        Integer status0 = status1+status2+status3;
        //查询所有 (基本信息)
        List<HxUserVideoManagementVo> hwOrders = hxUserVideoManagementMapper.selectQuery();

//        System.out.println(hwOrders.toString());
        if (!ObjectHelper.isEmpty(hwOrders)) {
            status0 = hwOrders.size();
            for (HxUserVideoManagementVo hwOrder : hwOrders) {
                if (hwOrder.getStatus() == 1) {
                    status1++;
                }
                if (hwOrder.getStatus() == 2) {
                    status2++;
                }
                if (hwOrder.getStatus() == 3) {
                    status3++;
                }
            }
        }
        List<Map<String, Object>> list ;
        //满足条件的总条数
       int count = hxUserVideoManagementMapper.selectQueryCountByMap(map);
        //满足条件的数据
        list = hxUserVideoManagementMapper.selectQueryByMap(map);
//            for(Map<String, Object> map1: list){
//                System.out.println(map1.get("account"));
//                System.out.println(map1.get("user_id"));
//                System.out.println(map1.get("create_time"));
//                System.out.println(map1.get("status"));
//                System.out.println(map1.get("order_id"));
//                System.out.println(map1.get("location"));
//            }

        HxUserVideoPage hxUserVideoPage = new HxUserVideoPage();
        hxUserVideoPage.setCount(count);
        hxUserVideoPage.setDataList(list);
        hxUserVideoPage.setStatus1(status1);
        hxUserVideoPage.setStatus2(status2);
        hxUserVideoPage.setStatus3(status3);
        hxUserVideoPage.setStatus0(status0);
        return hxUserVideoPage;
    }


    @Override
    @Transactional
    public void consent(Long video_id, Integer status,String reason, String handlers) {
        Long ls = hxUserVideoManagementMapper.selectStatus(video_id);
        Long ls2 = hxUserVideoManagementMapper.selectByAudit(video_id);
        if (ls == 0l || ls2 != 0l){
            //每个order_id 的status 不等于 2 or 3
            throw new RuntimeException("你选者的小视频中有已操作过的小视频 请重新选择");
        }

        //查询所有 (基本信息)
        HxUserVideoManagementVo hwOrders = hxUserVideoManagementMapper.selectQueryAll(video_id);

        if (StringUtils.isBlank(hwOrders.toString())) {
                throw new RuntimeException("你的视频不存在");
        }
        //当前时间戳
        Date date = new Date();
        long time_long=dateToLong(date);

            //修改
            //审核通过
            hxUserVideoManagementMapper.updateConsent(video_id,status);
            hxUserVideoManagementMapper.insert(video_id,handlers,reason,time_long);



    }

    @Override
    public void refuse(Long video_id, Integer status, String reason, String handlers) throws Exception {
        Long ls = hxUserVideoManagementMapper.selectStatus(video_id);
        Long ls2 = hxUserVideoManagementMapper.selectByAudit(video_id);
        if (ls == 0l || ls2 != 0l){
            //每个order_id 的status 不等于 2 or 3
            throw new RuntimeException("你选者的小视频中有已操作过的小视频 请重新选择");
        }
        //查询所有 (基本信息)
        HxUserVideoManagementVo hwOrders = hxUserVideoManagementMapper.selectQueryAll(video_id);
        if (StringUtils.isBlank(hwOrders.toString())) {
            throw new RuntimeException("你的视频不存在");
        }
        //当前时间戳
        Date date = new Date();
        long time_long=dateToLong(date);
        //审核被拒绝
        hxUserVideoManagementMapper.updateRefuse(video_id,reason,status);
        hxUserVideoManagementMapper.updateMusicStatus(hwOrders.getMusic_id());
        hxUserVideoManagementMapper.insert(video_id,handlers ,reason,time_long);

        sentNotice.sendSystem(getUserImId(hwOrders.getUser_id()),"您的小视频被拒绝",reason,null,1,null,null);
    }

    @Override
    @Transactional
    public Integer batchReview(List<Long> list,String reason ,String handlers) {

        //当前时间戳
        Date date = new Date();
        long time_long=dateToLong(date);
        //查询所有 (小视频状态是否为 1-未审核)
        for (Long video_id:list){
            Long ls = hxUserVideoManagementMapper.selectStatus(video_id);
            Long ls2 = hxUserVideoManagementMapper.selectByAudit(video_id);
            if (ls == 0l || ls2 != 0l){//每个order_id 的status 不等于 2 or 3
                return 1; //你选者的小视频中有已操作过的小视频 请重新选择
            }
        }

        for (Long video_id:list){
            //修改
            //审核通过
            try {
                hxUserVideoManagementMapper.updateConsent(video_id,2);
                hxUserVideoManagementMapper.insert(video_id,handlers,reason,time_long);
            }catch (Exception e){
                throw new RuntimeException("调试错误(请重试)!!");
            }
        }

        return 0;
    }
    //假删除小视频
    @Override
    @Transactional
    public void delete(Long video_id ,String handlers) {
        //当前时间戳
        Date date = new Date();
        long time_long=dateToLong(date);
        //查询所有 (基本信息)
        HxUserVideoManagementVo hwOrders = hxUserVideoManagementMapper.selectQueryAll(video_id);
        if (StringUtils.isBlank(hwOrders.toString())) {
            throw new RuntimeException("你的视频不存在");
        }
        //修改删除
        hxUserVideoManagementMapper.updateMusicStatus(hwOrders.getMusic_id());
        hxUserVideoManagementMapper.delete(video_id);
        hxUserVideoManagementMapper.deleteAudit(video_id , time_long ,handlers);
    }
    //查询小视频详情
    @Override
    public HxUserVideo selectDetails(Long video_id) {

        HxUserVideo hxUserVideo = hxUserVideoManagementMapper.selectDetails(video_id);
        if (hxUserVideo == null){
            throw new RuntimeException("你查询的小视频不存在 请联系管理员");
        }
        return hxUserVideo;
    }

    /**
     * 通过用户id查询im_id
     * @param user_id
     * @return
     */
    private String getUserImId(Long user_id) {
        String im_id = hxUserMapper.queryImIdByUserId(user_id);
        return im_id;
    }
}
