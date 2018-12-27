package com.hx.admin.controller.adminHx.account;


import com.hx.admin.base.ResultEntity;
import com.hx.admin.service.cache.AdminUserCache;
import com.hx.admin.service.cache.AdminUserCacheTools;
import com.hx.admin.validate.ValidateParam;
import com.hx.adminHx.service.account.AdminHxAccountService;
import com.hx.adminHx.service.vo.HxUserWallAccountPageVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "API - HxAccountController", description = "资金管理-对账列表")
public class HxAccountController {
    @Resource(name = "adminHxAccountService")
    private AdminHxAccountService adminHxAccountService;


    /**
     * 跳转到导游资金管理界面
     *
     * @return
     */
    @RequestMapping("admin/account/list")
    public ModelAndView guideList() {
        return new ModelAndView("adminHx/account/account-list");
    }

    /**
     * /**
     * 资金管理
     *
     * @param pageSize  分页大小
     * @param startNum  当前哪一条
     * @param order_num 订单编号
     * @param trans_num 交易单号
     * @param bill_type 0-订单 1-充值  5-提现
     * @return
     */
    @RequestMapping("admin/account/query")
    public ResultEntity account(@RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "0") Integer startNum,
                                @RequestParam(name = "paramMap[order_num]", required = false) String order_num,
                                @RequestParam(required = false) String order_id,
                                @RequestParam(defaultValue = "status", required = false) String orderBy,
                                @RequestParam(required = false, name = "paramMap[order_num]") String trans_num,
                                @RequestParam(defaultValue = "0", name = "paramMap[status]") Integer bill_type) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageSize", pageSize);
        map.put("startNum", startNum);
        map.put("order_id", order_id);
        System.out.println(trans_num);
        if (bill_type == 0) {
            map.put("order_num", order_num);
            map.put("trans_num", null);
        } else {
            map.put("order_num", null);
            map.put("trans_num", trans_num);
        }
        map.put("bill_type", bill_type);
        map.put("orderBy", orderBy);

        HxUserWallAccountPageVo hxUserWallAccountPageVo = adminHxAccountService.selectQuery(map);
        return new ResultEntity(hxUserWallAccountPageVo);
    }

    /**
     * 批量审核
     *
     * @param transfer 交易单号
     *                 session.getAdminUserAccount() 对账人(后台用户)
     * @return
     */
    @ValidateParam(field = {"transfer"}, message = {"transfer不能为空"})
    @RequestMapping("admin/account/batchReconciliation")
    public ResultEntity batchReview(@RequestParam(required = true) String transfer,
                                    HttpServletRequest request,
                                    @RequestParam(name = "paramMap[status]") Integer bill_type) {

        List<String> list = new ArrayList<>();

        String[] split = transfer.split(",");
        for (String s : split) {
            if (!"".equals(s)) {
                list.add(s);
            }
        }

        AdminUserCache session = AdminUserCacheTools.getSession(request);
        if (list == null || list.size() == 0) {
            throw new RuntimeException("没有选中交易单号");
        }
        adminHxAccountService.batchReconciliation(list, session.getAdminUserAccount(), bill_type);

        return new ResultEntity("200", "操作成功");

    }

    /**
     * 批量导出excel
     *
     * @param transfer  交易单号
     * @param bill_type 0-订单 1-充值  5-提现
     * @return
     */
    @ValidateParam(field = {"transfer"}, message = {"transfer不能为空"})
    @RequestMapping("admin/account/excel")
    public ResultEntity excel(@RequestParam(required = true) String transfer,
                              @RequestParam(name = "paramMap[status]", required = false) Integer bill_type,
                              HttpServletResponse response) {
        List<String> list = new ArrayList<>();

        String[] split = transfer.split(",");
        for (String s : split) {
            if (!"".equals(s)) {
                list.add(s);
            }
        }
        if (list == null || list.size() == 0) {
            throw new RuntimeException("没有选中交易单号");
        }
        adminHxAccountService.excel(list, bill_type, response);

        return new ResultEntity("200", "操作成功");
    }
}
