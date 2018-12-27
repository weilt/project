package com.hx.adminHx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwt.domain.entity.user.Vo.adminHxVo.AdminHxUserFriendVo;
import com.hwt.domain.entity.user.Vo.adminHxVo.AdminHxUserInfo;
import com.hwt.domain.entity.user.Vo.adminHxVo.AdminHxUserVo;
import com.hwt.domain.mapper.order.HwOrderMapper;
import com.hwt.domain.mapper.order.HxOrderInfoMapper;
import com.hwt.domain.mapper.order.HxOrderMapper;
import com.hwt.domain.mapper.user.HxUserFriendMapper;
import com.hwt.domain.mapper.user.HxUserInfoMapper;
import com.hwt.domain.mapper.user.HxUserMapper;
import com.hx.adminHx.service.AdminHxUserService;
import com.hx.core.page.asyn.PageResult;


@Service
public class AdminHxUserServiceImpl implements AdminHxUserService{
	
	@Autowired
	private HxUserMapper hxUserMapper;
	
	@Autowired
	private HxUserInfoMapper hxUserInfoMapper;
	
	@Autowired
	private HxUserFriendMapper hxUserFriendMapper;
	
	@Autowired
	private HwOrderMapper hwOrderMapper;
	
	@Autowired
	private HxOrderInfoMapper hxOrderInfoMapper;

	@Override
	public PageResult<Map<String, Object>> queryByMap(Map<String, Object> map) {
		long count  = hxUserMapper.queryCountByMap(map);
		List<Map<String, Object>> adminHxUserVos = hxUserMapper.queryListByMap(map);
		
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String, Object>>();
		pageResult.setCount(count);
		pageResult.setDataList(adminHxUserVos);
		
		return pageResult;
	}

	@Override
	public AdminHxUserInfo queryUserInfoByUserId(Long user_id) {
		AdminHxUserInfo adminHxUserInfo = hxUserInfoMapper.queryAdminHxUserInfoByUserId(user_id);
		return adminHxUserInfo;
	}

	@Override
	public int frozen(Long user_id, Integer type) {
		return hxUserMapper.frozen(user_id,type);
	}

	@Override
	public PageResult<AdminHxUserFriendVo> userFriend_queryByMap(Map<String, Object> map) {
		
		//总条数
		long count  = hxUserFriendMapper.queryCountUserFriendByMapToAdmin(map);
		
		//数据
		List<AdminHxUserFriendVo> adminHxUserVos = hxUserFriendMapper.queryUserFriendListByMapToAdmin(map);
		
		PageResult<AdminHxUserFriendVo> pageResult = new PageResult<AdminHxUserFriendVo>();
		pageResult.setCount(count);
		pageResult.setDataList(adminHxUserVos);
		
		return pageResult;
	}

	@Override
	public PageResult<Map<String, Object>> order_query(Map<String, Object> map) {
		//总条数
		long count  = hwOrderMapper.queryCountUserOrderByMapToAdmin(map);
		
		//查询数据
		List<Map<String, Object>> list = hwOrderMapper.queryUserOrderByMapToAdmin(map);
		
		PageResult<Map<String, Object>> pageResult = new PageResult<>();
		pageResult.setCount(count);
		
		pageResult.setDataList(list);
		return pageResult;
	}

}
