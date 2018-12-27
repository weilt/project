package com.hx.user.video.service;

import com.hwt.domain.entity.user.video.vo.HxUserVideoInfoVo;

public interface VideoUserService {

	/**
	 * 查询自己小视频中的基本信息
	 * @param user_id
	 * @return
	 */
	HxUserVideoInfoVo query_own(Long user_id);


	/**
	 * 申请发小视频的等级
	 * @param user_id
	 * @param apply_grade
	 */
	void grade_apply(Long user_id, Integer apply_grade);
	
}

