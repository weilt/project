package com.hx.api.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.user.HxUserInfo;
import com.hwt.domain.mapper.user.HxUserInfoMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoFollowMapper;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.wyim.notice.SentNotice;

@Component
@EnableScheduling
public class VideoScheduler {

	@Autowired
	private HxUserInfoMapper hxUserInfoMapper;
	
	@Autowired
	private HxUserVideoFollowMapper hxUserVideoFollowMapper;
	
	@Autowired
	private SentNotice sentNotice;
	
	/**
	 * 判断是否有人可以升到中级 
	 * 20分钟执行一次
	 * @throws Exception 
	 */
	@Transactional
	@Scheduled(cron = " 0 0/20 * * * ?")
	public void isgrade() throws Exception{
	
		List<HxUserInfo> list = hxUserInfoMapper.queryIsGrade();
		if (ObjectHelper.isNotEmpty(list)){
			for (HxUserInfo hxUserInfo : list) {
				hxUserInfoMapper.upIntermediateGrade(hxUserInfo.getUser_info_id());
				
				String title = "小视频通知";
				String description = "由于你粉丝破5000，恭喜你可以发60s的小视频了";
				//发消息
				sentNotice.sendSystem(hxUserInfo.getIm_id(), title, description, null, 9, null, "60");
			}
		}
	}
}
