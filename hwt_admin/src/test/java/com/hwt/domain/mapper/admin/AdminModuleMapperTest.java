//package com.hwt.domain.mapper.admin;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.hwt.domain.entity.es.ESQuery;
//import com.hx.core.mongo.service.MongoService;
//import com.hx.core.systemconfig.HXSystemConfig;
//import com.hx.core.utils.JsonUtils;
//import com.hx.core.utils.ObjectHelper;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AdminModuleMapperTest {
//
//	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//		//List<Map<String,Object>> findAll = MongoService.findAll("hwt","scenic_spot", null, null);
////		for (Map<String, Object> map : findAll) {
////			map.put("dataTime",map.get("create_time"));
////			Map<String, Object> filterMap = new HashMap<>();
////			filterMap.put("spotId", map.get("spotId"));
////			MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, filterMap, map);
////		}
////		for (Map<String, Object> map : findAll) {
////			ESQuery esQuery = new ESQuery();
////			esQuery.setArea_code(map.get("area_code") == null?null:map.get("area_code").toString());
////			esQuery.setName_id(map.get("spotId") == null?null:Long.parseLong(map.get("spotId").toString()));
////			esQuery.setName(map.get("spotName") == null?null:map.get("spotName").toString());
////			esQuery.setType(1);
////			String image = null;
////			if (map.get("imageUrls")!=null){
////				String imageUrls = map.get("imageUrls").toString();
////				String replace = imageUrls.replace("[", "");
////				String replace2 = replace.replace("]", "");
////				image = replace2.split(",")[0];
////			}
////			esQuery.setImage(image);
////			esQuery.setIs_hot(0);
////			esQuery.setSales(0);
////			esQuery.setPrice(0F);
////			esQuery.setComment_count(1);
////			esQuery.setComment_score(map.get("rating") == null?null:Float.parseFloat(map.get("rating").toString()));
////			esQuery.setLocation(map.get("location") == null?null:map.get("location").toString());
////			esQuery.setTar(map.get("tags") == null?null:map.get("tags").toString());
////			esQuery.setGeo_point(map.get("geoPoint") == null?null:map.get("geoPoint").toString());
////			esQuery.setCreate_time(map.get("create_time") == null?null:Long.parseLong(map.get("create_time").toString()));
////			System.out.println(JsonUtils.Bean2Json(esQuery));
////			int v= 1/0;
////			MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, esQuery, "id");
////		}
//		String aa = "D_640_360.jpg";
//		List<Map<String,Object>> findAll = MongoService.findAll(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, null, null);
//		for (Map<String, Object> map : findAll) {
//			Map<String, Object> filterMap = new HashMap<String, Object>();
//			Long id = Long.parseLong(map.get("spotId").toString());
//			filterMap.put("spotId", map.get("spotId"));
//			Map<String, Object> updateMap = new HashMap<String, Object>();
//			
//			
//			String imageUrls = map.get("imageUrls").toString();
//			if (imageUrls.contains(aa)){
//				
//				updateMap.put("isHide", 1);
//				MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, filterMap, updateMap);
//				
//				Map<String, Object> filterMap1 = new HashMap<String, Object>();
//				filterMap1.put("name_id", id);
//				filterMap1.put("type", 1);
//				Map<String, Object> updateMap1 = new HashMap<String, Object>();
//				updateMap1.put("is_hide", 1);
//				MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap1, updateMap1);
//			}
//			
//		}
//	}
//
//}
