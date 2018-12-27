package com.hx.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hx.api.scheduler.LineScheduler;
import com.hx.core.es.utils.ElasticsearchUtils;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.SequenceFactoryBean3;
import com.hx.core.wyim.entity.ImUser;
import com.hx.core.wyim.service.ImService;
import com.hx.scenic.scenicVo.ScenicSpotService;
import com.hx.user.service.LoginService;
import com.hx.user.video.service.VideoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HwtApiApplicationTests {
	
	@Autowired
	private ScenicSpotService scenicSpotService ;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LineScheduler lineScheduler;
	
	@Autowired
	private ImService imService;
	
	@Autowired
	private VideoService videoService;
	
//	@Test
//	public void aaaaa() throws Exception{
////		try {
////			ElasticsearchUtils.createIndex(HXSystemConfig.ES_Collection_NAME_hwt);
//////			
//////			boolean createMapping = scenicSpotService.createMapping();
////			boolean createMappingESQ = ElasticsearchUtils.createMappingESQ(HXSystemConfig.ES_Collection_NAME_hwt, HXSystemConfig.ES_Collection_NAME_ESQuery);
////			System.out.println(createMappingESQ);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		lineScheduler.delete_work_time();
//	}
	
//	/**
//	 * 账号创建
//	 */
//	@Test
//	public void creat_account() throws Exception{
//		for (int i = 0; i < 1000; i++) {
//			loginService.imRegister(new SequenceFactoryBean3().getObject(), "测试"+(i+1), "h11111111", null);
//		}
//	}
	
//	@Test
//	public void tetsss() throws Exception{
//		ImUser imUser = new ImUser(HXSystemConfig.HX_OFFICIAL_IM_ID);
//		int count = 0;
//		
//		
//			imUser.setIcon("https://gwstatic.oss-cn-beijing.aliyuncs.com/xitongxiaoxi_icon02.png");
//			imUser.setName("系统消息");
////		if (StringUtils.isNoneBlank(user_icon)){
////			imUser.setIcon(hxUserInfoEdit.getUser_icon());
////			count++;
////		}
//		
//		
//			imService.updateUinfo(imUser);
//		
//		
//	}
	
//	@Test
//	public void tetsss() throws Exception{
//		for (int i = 0; i < 100; i++) {
//			videoService.insert(2108l, 0l,"很好","chengdu.myqcloud.com/cover/TXVideo_20181102_143800.jpg",
//					"chengdu.myqcloud.com/video/TXVideo_20181102_143800.mp4",1,null,null,null);
//		}
//		
//		
//		
//	}
	
//	@Test
//	public void tt() throws Exception{
//		ElasticsearchUtils.createMapping("hwt_video", "hx_user_info");
//		ElasticsearchUtils.createMapping("hwt_video", "hx_user_video");
//	}
}
