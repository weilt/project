package com.hx.admin.controller.adminHx.salesList;

import com.hx.admin.base.ResultEntity;
import com.hx.adminHx.service.vo.HxUserSalesList;
import com.hx.adminHx.service.salesList.AdminHxSalesListService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "API - HxAccountController", description = "商家销售排行")
public class HxsalesListController {
    @Autowired
    private AdminHxSalesListService adminHxSalesListService;

    /**
     * 商家销售排行管理界面
     *
     * @return
     */
    @RequestMapping("admin/sales/list")
    public ModelAndView refundList() {
        return new ModelAndView("adminHx/salesList/salesList");
    }
    /**
     * 商家销售排行
     *
     * @param pageSize  分页大小
     * @param startNum  当前哪一条
     * @param cicerone_id 0-为没有选择导游   就是bureau_id旅行社不为null  0-旅行社  !0-导游
     * @param bill_type 0-全部 1-近30天 2-近90天
     * @param date_time1 第一个时间   date1<date2
     * @param date_time2 第二个时间
     *
     * @return
     */
    @RequestMapping("admin/salesList/query")
    public ResultEntity salesList(@RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "0") Integer startNum,
                                  @RequestParam(name = "paramMap[cicerone_id]",required = false,defaultValue = "0") Long cicerone_id,
                                  @RequestParam(name = "paramMap[bill_type]",defaultValue = "0",required = false) Integer bill_type,
                                  @RequestParam(name = "paramMap[date_time1]",required = false)String date_time1,
                                  @RequestParam(name = "paramMap[date_time2]",required = false)String date_time2) throws Exception {

        if (date_time1 != null && date_time2 == null){
            throw new RuntimeException("第二个时间必须有值");
        }
        if (date_time2 != null && date_time1 == null){
            throw new RuntimeException("第一个时间必须有值");
        }
        if (date_time1 != null && date_time2 != null){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(date_time1);
        Date date2 = sdf.parse(date_time2);
            if (dateToLong(date1) >= dateToLong(date2) ){
                throw new RuntimeException("你输入的时间必须符合规则");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageSize", pageSize);
            map.put("startNum", startNum);
            map.put("cicerone_id", cicerone_id);
            map.put("date1", date1);
            map.put("date2", date2);
            map.put("bill_type", bill_type);
            HxUserSalesList hxUserSalesList = adminHxSalesListService.selectQuery(map);
            return new ResultEntity(hxUserSalesList);
        }else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageSize", pageSize);
            map.put("startNum", startNum);
            map.put("cicerone_id", cicerone_id);
            map.put("bill_type", bill_type);

            HxUserSalesList hxUserSalesList = adminHxSalesListService.selectQuery(map);
            return new ResultEntity(hxUserSalesList);
        }
    }

    // date要转换的long类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
