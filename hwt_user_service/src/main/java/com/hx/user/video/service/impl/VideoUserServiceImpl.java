package com.hx.user.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.user.Vo.HxUseRealVo;
import com.hwt.domain.entity.user.video.HxUserVideoGradeApply;
import com.hwt.domain.entity.user.video.vo.HxUserVideoInfoVo;
import com.hwt.domain.mapper.user.HxUseRealMapper;
import com.hwt.domain.mapper.user.HxUserInfoMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoGradeApplyMapper;
import com.hx.core.exception.BaseException;
import com.hx.user.video.service.VideoUserService;

@Service
public class VideoUserServiceImpl implements VideoUserService {


	@Autowired
	private HxUserInfoMapper hxUserInfoMapper;
	
	
	@Autowired
	private HxUserVideoGradeApplyMapper hxUserVideoGradeApplyMapper;
	
	@Override
	@Transactional
	public HxUserVideoInfoVo query_own(Long user_id) {
		HxUserVideoInfoVo hxUserVideoInfoVo = hxUserInfoMapper.queryHxUserVideoInfoVoByUserId(user_id);
		
		return hxUserVideoInfoVo;
	}

	
	

	@Override
	@Transactional
	public void grade_apply(Long user_id, Integer apply_grade) {
		
		//判断是否可以申请
		HxUserVideoInfoVo hxUserVideoInfoVo = hxUserInfoMapper.queryHxUserVideoInfoVoByUserId(user_id);
		if (hxUserVideoInfoVo.getViode_grade().equals(apply_grade)){
			String msg = null;
			if (apply_grade==2){
				msg = "你已是中级！";
			} else {
				msg = "你已是高级！";
			}
			throw new BaseException(msg);
		}
		
		HxUserVideoGradeApply hxUserVideoGradeApply = new HxUserVideoGradeApply();
		hxUserVideoGradeApply.setUser_id(user_id);
		hxUserVideoGradeApply.setApply_grade(apply_grade);
		
		HxUserVideoGradeApply selectByPrimaryKey = hxUserVideoGradeApplyMapper.selectLatelyByUserId(user_id);
		if (selectByPrimaryKey==null){
			//说明不存在，直接提交申请
			hxUserVideoGradeApplyMapper.insertSelective(hxUserVideoGradeApply);
		} else {
			if (selectByPrimaryKey.getStatus()==1){
				throw new BaseException("你已提交申请，正在审核中！");
			} else {
				hxUserVideoGradeApplyMapper.insertSelective(hxUserVideoGradeApply);
			}
		}
		
	}
	
	
}
