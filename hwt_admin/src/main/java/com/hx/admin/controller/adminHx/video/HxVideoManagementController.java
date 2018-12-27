package com.hx.admin.controller.adminHx.video;

import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoManagementVo;
import com.hx.admin.base.ResultEntity;

import com.hx.admin.service.cache.AdminUserCache;
import com.hx.admin.service.cache.AdminUserCacheTools;
import com.hx.admin.validate.ValidateParam;
import com.hx.adminHx.service.video.AdminHxVideoManagementService;
import com.hx.adminHx.service.vo.HxUserVideoPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(value = "API - VideoManagement", description = "小视频管理")
public class HxVideoManagementController {

    @Autowired
    private AdminHxVideoManagementService adminHxVideoManagementService;

    /**
     *查询小视频
     *
     * @return
     */
    @RequestMapping("admin/video/list")
    public ModelAndView refundList() {
        return new ModelAndView("adminHx/video/video-list");
    }
    /**
     * 查询小视频
     *
     * @param pageSize  分页大小
     * @param startNum  当前哪一条
     * @param orderBy   排序
     * @param status   0-查询全部  默认1 未审核 2-通过 3-未通过
     * @param video_id  id
     * @param account  名称
     * @return
     */
    @RequestMapping("admin/video/query")
    public ResultEntity query(@RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "0") Integer startNum,
                              @RequestParam(defaultValue = "status ASC,a.create_time DESC") String orderBy,
                              @RequestParam(defaultValue = "0",required = false,name = "paramMap[status]") Integer status,
                              @RequestParam(required = false) Long video_id,
                              @RequestParam(required = false,name = "paramMap[time]") String time,
                              @RequestParam(required = false,name = "paramMap[account]") String account) throws Exception {
        Date date = null;
        if (time != null){
            System.out.println(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(time);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageSize", pageSize);
        map.put("startNum", startNum);
        map.put("orderBy", orderBy);
        map.put("date", date);
        map.put("video_id", video_id);
        map.put("status", status);
        map.put("account", account);
        HxUserVideoPage hxUserVideoPage = adminHxVideoManagementService.selectQuery(map);
        return new ResultEntity(hxUserVideoPage);
    }

    /**
     * 小视频详情
     */
    @ValidateParam(field={"video_id"}, message={"video_id不能为空"})
    @RequestMapping("admin/video/details")
    public ResultEntity details(@RequestParam(required = true)Long video_id){

        HxUserVideo hxUserVideo = adminHxVideoManagementService.selectDetails(video_id);

         return new ResultEntity(hxUserVideo);
    }
    /**
     * 审核
     * @param video_id  视频id
     * @param status '审核状态 1-未审核 2-通过 3-未通过',
     * @param reason    备注 如果拒绝小视频 ((((备注必填 ))))对应MYSQL中的未通过原因
     * @ session.getAdminUserAccount() 操作人
     * @return
     * @throws Exception
     */
    @ValidateParam(field={"video_id","status"}, message={"video_id不能为空","status不能为空"})
    @RequestMapping("admin/video/consent")
    public ResultEntity consent(@RequestParam(required = true)Long video_id,
                                @RequestParam(required = true)Integer status,
                                @RequestParam(required = false)String reason ,
                                HttpServletRequest request)  throws Exception {
        AdminUserCache session = AdminUserCacheTools.getSession(request);
        if (status == 2){
            adminHxVideoManagementService.consent(video_id,status,reason,session.getAdminUserAccount());
        }
        if (status == 3){
            adminHxVideoManagementService.refuse(video_id,status,reason ,session.getAdminUserAccount());
        }
        return new ResultEntity("200","操作成功");

    }

    /**
     * 假删除 小视频
     */
    @ValidateParam(field={"video_id"}, message={"video_id不能为空"})
    @RequestMapping("admin/video/delete")
    public ResultEntity delete(@RequestParam(required = true)Long video_id, HttpServletRequest request){

        AdminUserCache session = AdminUserCacheTools.getSession(request);

        adminHxVideoManagementService.delete(video_id,session.getAdminUserAccount());

        return new ResultEntity("200","操作成功");
    }

    /**
     * 批量审核
     */
    @RequestMapping("admin/video/batchReview")
    public ResultEntity batchReview(@RequestParam(name="video_ids",required = false)String video_ids,
                                HttpServletRequest request) {
        //备注
        String reason = "无";
        AdminUserCache session = AdminUserCacheTools.getSession(request);

        String[] ids = video_ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String id : ids) {
            if (!"".equals(id)) {
                list.add(Long.parseLong(id));
            }
        }
        if (list == null || list.size() == 0){
            return new ResultEntity("200","没有选中视频编号");
        }
        Integer integer = adminHxVideoManagementService.batchReview(list,reason,session.getAdminUserAccount());
        if (integer == 1 ){
            return new ResultEntity("200","你选者的小视频中有已操作过的小视频 请重新选择");
        }

        return new ResultEntity("200","操作成功");

    }


}
