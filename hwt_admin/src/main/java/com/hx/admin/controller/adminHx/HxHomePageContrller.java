package com.hx.admin.controller.adminHx;

import com.hx.admin.base.ResultEntity;
import com.hx.adminHx.service.homePage.AdminHomeSesrvice;
import com.hx.adminHx.service.vo.AdminHome;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@Api(value = "API - HxHomePageContrller", description = "首页")
public class HxHomePageContrller {

    @Autowired
    private AdminHomeSesrvice adminHomeSesrvice;
    /**
     * 跳转到首页管理界面
     *
     * @returns
     */
    @RequestMapping("admin/index")
    public ModelAndView refundList() throws Exception{
        AdminHome adminHome  = adminHomeSesrvice.selectByAll();

        return new ModelAndView("adminHx/index/index").addObject("adminHome",adminHome);
    }
    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/adminhomePagequery")
    public ResultEntity homePage() throws Exception {

        AdminHome adminHome  = adminHomeSesrvice.selectByAll();

        return new ResultEntity(adminHome);
    }
}
