
package com.hx.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hwt.domain.mapper.user.wallet.HxUserWalletMapper;
import com.hx.admin.file.service.OSSService;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.PropertiesUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HwtAdminApplication.class)
public class HwtAdminApplicationTests {
	
	@Autowired
	private HxUserWalletMapper hxUserWalletMapper;
	

	@Resource
	private OSSService ossService;
	
//	public static void main(String[] args) {
//		try {
//			List<Map<String,Object>> findAll = MongoService.findAll(HXSystemConfig.MONGO_Collection_NAME_hwt,HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, null, null);
//			for (Map<String, Object> map : findAll) {
//				Long line_id = Long.parseLong(map.get("line_id").toString());
//				
//				Map<String, Object> filterMap = new HashMap<>();
//				filterMap.put("line_id", line_id);
//				
//				Map<String, Object> updateMap = new HashMap<>();
//				updateMap.put("scenics", "");
//				updateMap.put("scenics_info", "");
//				
//				MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt,HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, filterMap, updateMap);
//			}
//		} catch (Exception e) {
//				e.printStackTrace();
//		}
//		
//	}
	
//	@Test
//	public void a(){
//		
//		Map<String, Object> map = new HashMap<>();
//		//String url = "/"+UUIDHelper.createUUId()+uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf('.'));
//		String url = "xitongxiaoxi_icon02.png";
//		
//		try {
//			
//			File file = new File("C:/Users/Administrator/Desktop/xitongxiaoxi_icon02.png");
//			ossService.saveFileAsyn(file , url);
//			String EXTERNAL_ENDPOINT = PropertiesUtils.getValue("EXTERNAL_ENDPOINT", "aliyun.properties");
//			String IMG_OSS_BUCKET_NAME = PropertiesUtils.getValue("IMG_OSS_BUCKET_NAME", "aliyun.properties");
//			
//			url = EXTERNAL_ENDPOINT.replace("http://", "https://"+IMG_OSS_BUCKET_NAME+".") + "/" + url;
//			
//			map.put("error", 0);
//			map.put("url", url);
//		} catch (Exception e) {
//			map.put("error", 1);
//			map.put("message", "上传失败");
//			e.printStackTrace();
//		}
//		String json = JsonUtils.Bean2Json(map);
//		System.out.println(json);
//		
//		
//	}
	
	/**
	 * 将经纬度存入es
	 */
	@Test
	public void sfsdfsdf(){
		List<Map<String,Object>> findAll = MongoService.findAll(HXSystemConfig.MONGO_Collection_NAME_hwt,HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, null, null);
		for (Map<String, Object> map : findAll) {
			if (!ObjectHelper.isEmpty(map.get("geoPoint"))){
				String str = map.get("geoPoint").toString();
				str = str.replace("{", "");
				str = str.replace("}", "");
				str = str.replace(" ", "");
				String[] split = str.split(",");
				
				String lon = split[0].substring(4);
				String lat =split[1].substring(4,split[1].length());
				
				Map<String, Object> filterMap = new HashMap<>();
				filterMap.put("name_id", map.get("spotId"));
				filterMap.put("type", 1);
				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("point", lat+","+lon);
				MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt,HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap, updateMap);
			
			}
			
			
		}
	}
}
