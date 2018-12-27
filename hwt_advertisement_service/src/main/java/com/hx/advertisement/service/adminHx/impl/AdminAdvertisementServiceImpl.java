package com.hx.advertisement.service.adminHx.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.advertisement.HwAdvertisement;
import com.hwt.domain.entity.system.HwAttribute;
import com.hwt.domain.mapper.advertisement.HwAdvertisementMapper;
import com.hwt.domain.mapper.system.HwAttributeMapper;
import com.hx.advertisement.service.adminHx.AdminAdvertisementService;
import com.hx.core.page.asyn.PageResult;

@Service
public class AdminAdvertisementServiceImpl implements AdminAdvertisementService {

	@Autowired
	private HwAdvertisementMapper hwAdvertisementMapper;
	
	@Autowired
	private HwAttributeMapper hwAttributeMapper;

	@Override
	public PageResult<Map<String, Object>> queryByMap(Map<String, Object> map) {
		
		//查询总条数
		long count = hwAdvertisementMapper.queryCountAdminByMap(map);
		//查询数据
		List<Map<String, Object>> list = hwAdvertisementMapper.queryAdminByMap(map);
		
		PageResult<Map<String, Object>> pageResult = new PageResult<>();
		pageResult.setCount(count);
		pageResult.setDataList(list);
		return pageResult;
	}

	@Override
	@Transactional
	public void insert(HwAdvertisement advertisement) {
		advertisement.setCreate_time(System.currentTimeMillis());
		hwAdvertisementMapper.insertSelective(advertisement);
		
	}

	@Override
	public Map<String, Object> queryAdvertisementAttribute(Map<String, Object> map) {
		//查询广告的分类
		List<HwAttribute> carousel_image_types = hwAttributeMapper.queryAll("carousel_image_type");
		map.put("carousel_image_types", carousel_image_types);
		//查询广告的位置
		List<HwAttribute> advertising_positions = hwAttributeMapper.queryAll("advertising_position");
		map.put("advertising_positions", advertising_positions);
		return map;
	}

	@Override
	public HwAdvertisement queryInfo(Long ad_id) {
		HwAdvertisement advertisement = hwAdvertisementMapper.selectByPrimaryKey(ad_id);
		return advertisement;
	}

	@Override
	@Transactional
	public void update(HwAdvertisement advertisement) {
		hwAdvertisementMapper.updateByPrimaryKeySelective(advertisement);
		
	}

	@Override
	public void online(Long ad_id) {
		hwAdvertisementMapper.online(ad_id);
	}

	@Override
	public void not_online(Long ad_id) {
		hwAdvertisementMapper.not_online(ad_id);
	}

	@Override
	public void is_hide(Long ad_id) {
		hwAdvertisementMapper.is_hide(ad_id);
		
	}

	@Override
	public void is_not_hide(Long ad_id) {
		hwAdvertisementMapper.is_not_hide(ad_id);
		
	}
}
