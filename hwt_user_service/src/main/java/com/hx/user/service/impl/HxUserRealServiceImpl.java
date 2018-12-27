package com.hx.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwt.domain.entity.user.HxUseReal;
import com.hwt.domain.entity.user.Vo.HxUseRealVo;
import com.hwt.domain.mapper.user.HxUseRealMapper;
import com.hx.user.service.HxUserRealService;

@Service
public class HxUserRealServiceImpl implements HxUserRealService {

	@Autowired
	private HxUseRealMapper hxUseRealMapper;
	
	@Override
	public HxUseRealVo queryOwn(Long user_id) {
		
		HxUseRealVo hxUseRealVo = hxUseRealMapper.queryHxUseRealVoByUserId(user_id);
		
		return hxUseRealVo;
	}

	@Override
	public void insertOrUpdate(Long user_id, HxUseReal hxUseReal) {
		HxUseReal selectByPrimaryKey = hxUseRealMapper.selectByPrimaryKey(user_id);
		
		if (selectByPrimaryKey!=null){
			//修改
			hxUseRealMapper.updateByPrimaryKeySelective(hxUseReal);
		} else {
			//添加
			hxUseRealMapper.insertSelective(hxUseReal);
		}
		
	}

}
