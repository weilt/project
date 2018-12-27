package com.hx.adminHx.service.salesList.impl;

import com.hx.adminHx.service.vo.HxUserSalesList;
import com.hwt.domain.mapper.user.salesList.HxUserSalesListMapper;
import com.hx.adminHx.service.salesList.AdminHxSalesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class AdminHxSalesListServiceImpl implements AdminHxSalesListService {
    @Autowired
    private HxUserSalesListMapper hxUserSalesListMapper;

    // date要转换的long类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
    @Override
    public HxUserSalesList selectQuery(Map<String, Object> map) {
        //当前时间
        Date date = new Date();

        List<Map<String,Object>> list = null;
        //旅行社
        Long cicerone_id = Long.parseLong(map.get("cicerone_id").toString());
        if (cicerone_id==0){

            if(map.get("bill_type").equals(0)){//查询全部数据 可以加 date
                if (map.get("date1") != null && map.get("date1") != ""){
                long date1 = dateToLong((Date) map.get("date1"));
                long date2 = dateToLong((Date) map.get("date2"));
                map.put("date1",date1);
                map.put("date2",date2);
                }
                list =  hxUserSalesListMapper.selectQuery(map);
            }
            if (map.get("bill_type").equals(1)){//查询近30天数据 不能加date
                //86400000一天的毫秒值  2592000000  30天的毫秒值
                long time_mini = dateToLong(date)-2592000000L;
                long time_big = dateToLong(date)+ 86400000L;
                map.put("date1",time_mini);
                map.put("date2",time_big);
                list =  hxUserSalesListMapper.selectQuery(map);
            }
            if (map.get("bill_type").equals(2)){//查询近90天数据 不能加date
                //86400000一天的毫秒值  7776000000L  90天的毫秒值
                long time_mini = dateToLong(date)-7776000000L;
                long time_big = dateToLong(date)+ 86400000L;
                map.put("date1",time_mini);
                map.put("date2",time_big);
                list =  hxUserSalesListMapper.selectQuery(map);
            }

        }else{
            //导游
            if(map.get("bill_type").equals(0)){//查询全部数据 可以加 date
                if (map.get("date1") != null && map.get("date1") != ""){
                    long date1 = dateToLong((Date) map.get("date1"));
                    long date2 = dateToLong((Date) map.get("date2"));
                    map.put("date1",date1);
                    map.put("date2",date2);
                }
                list =  hxUserSalesListMapper.selectQueryTour(map);
            }
            if (map.get("bill_type").equals(1)){//查询近30天数据 不能加date
                //86400000一天的毫秒值  2592000000  30天的毫秒值
                long time_mini = dateToLong(date)-2592000000L;
                long time_big = dateToLong(date)+ 86400000L;
                map.put("date1",time_mini);
                map.put("date2",time_big);
                list =  hxUserSalesListMapper.selectQueryTour(map);
            }
            if (map.get("bill_type").equals(2)){//查询近90天数据 不能加date
                //86400000一天的毫秒值  7776000000L  90天的毫秒值
                long time_mini = dateToLong(date)-7776000000L;
                long time_big = dateToLong(date)+ 86400000L;
                map.put("date1",time_mini);
                map.put("date2",time_big);
                list =  hxUserSalesListMapper.selectQueryTour(map);
            }
        }
        HxUserSalesList hxUserSalesList = new HxUserSalesList();
        hxUserSalesList.setCount(list.size());
        hxUserSalesList.setDataList(list);
        return hxUserSalesList;

    }
}
