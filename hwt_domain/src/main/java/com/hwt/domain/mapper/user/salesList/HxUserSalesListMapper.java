package com.hwt.domain.mapper.user.salesList;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HxUserSalesListMapper {

    List<Map<String,Object>> selectQuery(Map<String, Object> map);


    List<Map<String, Object>> selectQueryTour(Map<String, Object> map);
}
