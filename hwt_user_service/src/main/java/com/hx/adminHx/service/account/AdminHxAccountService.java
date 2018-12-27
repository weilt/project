package com.hx.adminHx.service.account;


import com.hx.adminHx.service.vo.HxUserWallAccountPageVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface AdminHxAccountService {
    /**
     * 查询所有 + 数据个数
     * @param map
     * @return
     */
    HxUserWallAccountPageVo selectQuery(Map<String, Object> map);

    /**
     * 批量对账
     * @param list 交易单号
     * @param handlers
     */
    void batchReconciliation(List<String> list, String handlers,Integer bill_type);
    /**
     * 批量导出excel
     * @param list 交易单号
     */
    void excel(List<String> list,Integer bill_type, HttpServletResponse response);
}
