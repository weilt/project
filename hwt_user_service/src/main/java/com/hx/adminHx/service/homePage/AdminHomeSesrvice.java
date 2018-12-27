package com.hx.adminHx.service.homePage;


import com.hx.adminHx.service.vo.AdminHome;

import java.text.ParseException;

public interface AdminHomeSesrvice {
    //查询首页所有数据
    AdminHome selectByAll() throws ParseException;
}
