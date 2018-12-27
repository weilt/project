
package com.hx.advertisement.service.hx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.mapper.advertisement.HwAdvertisementMapper;
import com.hx.advertisement.service.hx.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{

	@Autowired
	private HwAdvertisementMapper hwAdvertisementMapper;

	@Override
	@Transactional
	public void lookAdd(Long ad_id) {
		hwAdvertisementMapper.lookAdd(ad_id);
		
	}
}
