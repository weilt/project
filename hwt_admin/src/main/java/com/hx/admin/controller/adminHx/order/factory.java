package com.hx.admin.controller.adminHx.order;

import com.hx.adminHx.service.account.AdminHxAccountService;
import com.hx.adminHx.service.account.impl.AdminHxAccountServiceImpl;
import com.hx.adminHx.service.homePage.AdminHomeSesrvice;
import com.hx.adminHx.service.homePage.impl.AdminHomeServiceImpl;
import com.hx.adminHx.service.salesList.AdminHxSalesListService;
import com.hx.adminHx.service.salesList.impl.AdminHxSalesListServiceImpl;
import com.hx.adminHx.service.video.AdminHxVideoManagementService;
import com.hx.adminHx.service.video.impl.AdminHxVideoManagementServiceImpl;
import com.hx.order.service.admin.AdminOrderService;
import com.hx.order.service.admin.AdminRefundOrderService;
import com.hx.order.service.admin.impl.AdminOrderServiceImpl;
import com.hx.order.service.admin.impl.AdminRefundOrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class factory {
    @Bean
    public AdminOrderService adminOrderService() {
        return new AdminOrderServiceImpl();
    }

    @Bean
    public AdminRefundOrderService adminRefundOrderService() {
        return new AdminRefundOrderServiceImpl();
    }

    @Bean
    public AdminHxVideoManagementService adminHxVideoManagementService(){
        return new AdminHxVideoManagementServiceImpl();
    }
    @Bean
    public AdminHxAccountService adminHxAccountService(){
        return new AdminHxAccountServiceImpl();
    }

    @Bean
    public AdminHxSalesListService adminHxSalesListService(){
        return new AdminHxSalesListServiceImpl();
    }

    @Bean
    public AdminHomeSesrvice adminHomeSesrvice(){
        return new AdminHomeServiceImpl();
    }
}
