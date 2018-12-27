package com.hx.user.service;

import com.hwt.domain.entity.user.HxUseReal;
import com.hwt.domain.entity.user.Vo.HxUseRealVo;


/**
 * 用户真实信息
 * @author Administrator
 *
 */
public interface HxUserRealService {

	/**
	 * 查询自己的真实信息
	 * @param user_id
	 * @return
	 */
	HxUseRealVo queryOwn(Long user_id);

	
	/**
	 * 用户真实信息添加修改
	 * @param user_id
	 * @param hxUseReal
	 */
	void insertOrUpdate(Long user_id, HxUseReal hxUseReal);
	
	
}
