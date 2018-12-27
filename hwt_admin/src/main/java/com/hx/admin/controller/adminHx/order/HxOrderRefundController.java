package com.hx.admin.controller.adminHx.order;

import com.hwt.domain.entity.admin.AdminUser;
import com.hwt.domain.entity.order.vo.OrderDetailsVo;
import com.hwt.domain.mapper.order.HxOrderMapper;
import com.hx.admin.base.ResultEntity;
import com.hx.admin.service.cache.AdminUserCache;
import com.hx.admin.service.cache.AdminUserCacheTools;
import com.hx.admin.validate.ValidateParam;
import com.hx.core.logs.annotation.Log;
import com.hx.core.utils.ObjectHelper;
import com.hx.order.service.admin.AdminOrderService;
import com.hx.order.service.admin.AdminRefundOrderService;
import com.hx.order.service.admin.vo.AdminPageOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "API - OrderController", description = "退款订单管理")
public class HxOrderRefundController {
    @Autowired
    private AdminRefundOrderService adminRefundOrderService;
    /**
     * 跳转到退款订单管理界面
     *
     * @return
     */
    @RequestMapping("admin/order/refund/list")
    public ModelAndView refundList() {
        return new ModelAndView("adminHx/order/order-refundList");
    }
    /**
     * 查询退款订单
     *
     * @param pageSize  分页大小
     * @param startNum  当前哪一条
     * @param orderBy   排序
     * @param status   4-发起退款 5-退款完成 6-拒绝退款
     * @param order_num 订单编号
     * @param cicerone_id         订单选择(线路或导游),默认为0(线路查询)
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize" , value = "展示条数10" ,dataType = "int" ,defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name = "starNum" , value = "开始页 默认第0条开始查询" , dataType = "int" , defaultValue = "0" ,paramType = "query"),
            @ApiImplicitParam(name = "orderBy" , value = "根据什么排序 默认apply_sales_time ASC" ,defaultValue = "apply_sales_time ASC", dataType = "string" , paramType = "query"),
            @ApiImplicitParam(name = "cicerone_id" , value = "订单选择(线路或导游),默认为0(线路查询)" ,defaultValue = "0",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name = "status" , value = "状态 4-发起退款 5-退款完成 6-拒绝退款", required = false,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "order_num",value = "订单编号",required = false , dataType = "string" , paramType = "query")
    })
    @RequestMapping("admin/order/refundQuery")
    public ResultEntity query(@RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "0") Integer startNum,
                              @RequestParam(defaultValue = "apply_sales_time ASC") String orderBy,
                              @RequestParam(defaultValue = "0",name = "paramMap[cicerone_id]")Long cicerone_id,
                              @RequestParam(required = false,name = "paramMap[status]") Integer status,
                              @RequestParam(required = false,name = "paramMap[order_num]") String order_num) throws Exception {
        if (cicerone_id == 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageSize", pageSize);
            map.put("startNum", startNum);
            map.put("orderBy", orderBy);
            map.put("order_num", order_num);
            map.put("status", status);
            map.put("cicerone_id", cicerone_id);
            AdminPageOrder adminPageOrder = adminRefundOrderService.selectQuery(map);
            return new ResultEntity(adminPageOrder);
        }else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageSize", pageSize);
            map.put("startNum", startNum);
            map.put("orderBy", orderBy);
            map.put("order_num", order_num);
            map.put("status", status);
            map.put("cicerone_id", cicerone_id);
            AdminPageOrder adminPageOrder = adminRefundOrderService.selectGuideQuery(map);
            return new ResultEntity(adminPageOrder);
        }
    }

    /**
     * 退款详情
     */
    @ApiOperation(value = "订单详情", notes = "订单详情", response = OrderDetailsVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_id", value = "订单id", dataType = "string", required = true, paramType = "query"),
    })
    @ValidateParam(field = {"order_id"}, message = {"order_id不能为空"})
    @RequestMapping("admin/order/refundDetails")
    public ResultEntity details(Long order_id ) {
        Map<String,Object> orderDetailsVo = adminRefundOrderService.selectQureyDetails(order_id);
        return new ResultEntity(orderDetailsVo);

    }

    /**
     * 同意退款订单
     * contract_money 违约金 (默认为无)
     * operator 操作人
     */
    @ValidateParam(field={"order_id"}, message={"order_id不能为空"})
    @RequestMapping("admin/order/consentOrderRefund")
    public ResultEntity consent(Long order_id,
                                @RequestParam(defaultValue = "0" , required = false) BigDecimal contract_money,
                                HttpServletRequest request)  throws Exception {

        AdminUserCache session = AdminUserCacheTools.getSession(request);

        List list = adminRefundOrderService.consent(order_id,contract_money,session.getAdminUserAccount());

       if (list==null||list.size()==0){
        return new ResultEntity("200","操作成功");
       }
        return new ResultEntity("100","商家余额不足,请电话跟踪ID为"+ list.get(0) +"的商家,违约金为"+
                contract_money+"元,已扣余额为"+
                list.get(1)+"元");
    }
    /**
     * 拒绝退款订单
     * @param refund_cause 拒绝原因
     * session.getAdminUserAccount() 操作人
     */
    @ValidateParam(field={"order_id","refund_cause"}, message={"order_id不能为空","refund_cause拒绝原因不能为空"})
    @RequestMapping("admin/order/refusedOrderRefund")
    public ResultEntity refused(Long order_id,String refund_cause ,HttpServletRequest request)  throws Exception {
        //获取session 的操作人
        AdminUserCache session = AdminUserCacheTools.getSession(request);

        adminRefundOrderService.refused(order_id,refund_cause , session.getAdminUserAccount());
        return new ResultEntity("200","操作成功");

    }
}
