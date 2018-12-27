package com.hx.adminHx.service.salesList;

import com.hx.adminHx.service.vo.HxUserSalesList;
import java.util.Map;

public interface AdminHxSalesListService {

    /**
     * 查询所有
     * @param map
     * @return
     */
    HxUserSalesList selectQuery(Map<String, Object> map);


}
