package com.hx.scenic.adminHx.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.es.ESQuery;
import com.hwt.domain.entity.mg.scenic.ScenicSpot;
import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.mapper.user.video.HxUserVideoMapper;
import com.hx.core.exception.BaseException;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryCondition;
import com.hx.core.mongo.value.MongoQueryCondition.LinkKey;
import com.hx.core.mongo.value.MongoQueryProjections;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.mongo.value.Sort;
import com.hx.core.page.asyn.PageResult;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.ZZLocationUtils;
import com.hx.scenic.adminHx.AdminScenicSpotService;

@Service
public class AdminScenicSpotServiceImpl implements AdminScenicSpotService{
	
	@Autowired
	private HxUserVideoMapper hxUserVideoMapper;

	@Override
	public PageResult<Map<String,Object>> queryByMap(Map<String, Object> map) {
		
		
		Integer pageSize =  (Integer) map.get("pageSize");
		
		Integer currentPage =  (Integer) map.get("currentPage");
		Integer complete =  (Integer) map.get("complete");
		
		String orderBy = (String) map.get("orderBy");
		
		String filed = (String) map.get("filed");
		String city = (String) map.get("city");
		
		//MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
		MongoQueryCondition condition = null;
		if (complete!=0){
			MongoQueryCondition condition1 = new MongoQueryCondition(LinkKey.or);
			String aa = "D_640_360.jpg";
			condition1.add(new MongoQueryValue(MongoOperator.regex, "imageUrls", aa));
			condition1.add(new MongoQueryValue(MongoOperator.eq, "description", ""));
			condition = new MongoQueryCondition(LinkKey.and,condition1);
		} else {
			condition = new MongoQueryCondition(LinkKey.and);
		}
		
		List<MongoQueryCondition> list = new ArrayList<>();
			if (StringUtils.isNotBlank(filed)){
				MongoQueryCondition condition1 = new MongoQueryCondition(LinkKey.or);
				condition1.add(new MongoQueryValue(MongoOperator.regex, "spotName", filed));
				condition1.add(new MongoQueryValue(MongoOperator.regex, "description", filed));
				condition1.add(new MongoQueryValue(MongoOperator.regex, "brief", filed));
				condition1.add(new MongoQueryValue(MongoOperator.regex, "subtitle", filed));
				list.add(condition1);
			}
			
		
		
		
		if (StringUtils.isNoneBlank(city)){
			MongoQueryCondition condition2 = new MongoQueryCondition(LinkKey.and);
			
			condition2.add(new MongoQueryValue(MongoOperator.eq, "city", city));
			list.add(condition2);
		}
		if(list!=null&&list.size()>0){
			MongoQueryCondition[] conditions = new MongoQueryCondition[list.size()];
			for (int i = 0; i < conditions.length; i++) {
				conditions[i] = list.get(i);
			}
			condition.setCondtion(conditions);
		}
		
		
		
		//查询总条数
		long count = MongoService.findPageCount(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, condition );
		
		String[] split = orderBy.split(" ");
		
		//排序
		Sort sort = null;
		if (split[1].equals("asc")){
			sort = new Sort(split[0],Sort.ASC);
		}else{
			sort = new Sort(split[0],Sort.DESC);
		}
		
		//不显示字段 
		MongoQueryProjections projection = new MongoQueryProjections();
		projection.put("description", 0);
		projection.put("brief", 0);
		projection.put("subtitle", 0);
		projection.put("geoPoint", 0);
		projection.put("tags", 0);
		projection.put("rating", 0);
		projection.put("imageUrls", 0);
		projection.put("rank", 0);
		projection.put("location", 0);
		projection.put("ticketInfo", 0);
		projection.put("dataUrl", 0);
		projection.put("dataSources", 0);
		
		List<Map<String,Object>> findByPage = MongoService.findByPage(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, projection , condition, sort , currentPage, pageSize);
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		
		pageResult.setCount(count);
		pageResult.setDataList(findByPage);
		return pageResult;
	}

	@Override
	public boolean deleteById(Long spotId, Integer type) {
		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put("spotId", spotId);
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("isHide", type);
		boolean updateOne = MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, filterMap, updateMap);
		
		Map<String, Object> filterMap1 = new HashMap<>();
		filterMap1.put("name_id", spotId);
		filterMap1.put("type", 1);
		Map<String, Object> updateMap1 = new HashMap<String, Object>();
		updateMap1.put("is_hide", type);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap1, updateMap1);
		return updateOne;
	}

	@Override
	public Map<String, Object> queryScenicInfoBySpotId(Long spotId) {
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "spotId", spotId);
		Map<String, Object> map = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, queryValue , null);
		List<HxUserVideo> list = hxUserVideoMapper.selectBigVideoByIdAndType(spotId,1);
		if (ObjectHelper.isEmpty(list)){
			map.put("hxUserVideos", null);
		} else {
			map.put("hxUserVideos", list);
		}
		
		return map;
	}

	@Transactional
	@Override
	public void update(ScenicSpot scenicSpot, String big_videos) {
		String geoPoint = scenicSpot.getGeoPoint();
		if (StringUtils.isNoneBlank(geoPoint)){
			String[] split = geoPoint.split(",");
			Map<String, String> map = ZZLocationUtils.get_province_city_district_by_gaode_log_lat(split[0].replace("{", "").split("=")[1], split[1].replace("}", "").split("=")[1]);
			scenicSpot.setCountry(map.get("country"));
			scenicSpot.setArea_code(map.get("area_code"));
			scenicSpot.setCity(map.get("city"));
			scenicSpot.setCity_code(map.get("city_code"));
			scenicSpot.setDistrict(map.get("district"));
			scenicSpot.setProvince(map.get("province"));
		
		}
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "spotId", scenicSpot.getSpotId());
		Map<String, Object> map = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, queryValue , null);
		//高德获取到城市说明是台湾省 或者海域 ，所以保留数据库中的
		if (ObjectHelper.isEmpty(scenicSpot.getCity())||"[]".equals(scenicSpot.getCity())){
			
			scenicSpot.setCity(map.get("city") + "");
        }
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("spotId", scenicSpot.getSpotId());
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("spotName", scenicSpot.getSpotName());
		updateMap.put("country", scenicSpot.getCountry());
		updateMap.put("province", scenicSpot.getProvince());
		updateMap.put("district", scenicSpot.getDistrict());
		updateMap.put("city", scenicSpot.getCity());
		updateMap.put("city_code", scenicSpot.getCity_code());
		updateMap.put("city_code", scenicSpot.getArea_code());
		updateMap.put("description", scenicSpot.getDescription());
		updateMap.put("brief", scenicSpot.getBrief());
		updateMap.put("subtitle", scenicSpot.getSubtitle());
		updateMap.put("rating", scenicSpot.getRating());
		updateMap.put("telephones", scenicSpot.getTelephones());
		updateMap.put("geoPoint", scenicSpot.getGeoPoint());
		updateMap.put("tags", scenicSpot.getTags());
		updateMap.put("imageUrls", scenicSpot.getImageUrls());
		updateMap.put("rank", scenicSpot.getRank());
		updateMap.put("openingHours", scenicSpot.getOpeningHours());
		updateMap.put("location", scenicSpot.getLocation());
		updateMap.put("ticketInfo", scenicSpot.getTicketInfo());
		updateMap.put("dataUrl", scenicSpot.getDataUrl());
		updateMap.put("banner_videos", scenicSpot.getBanner_videos());
		updateMap.put("cover_type", scenicSpot.getCover_type());
		
		
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, filterMap, updateMap);
		esQueryScenicSpotUpdate(scenicSpot);
		
		//删除长视频
		hxUserVideoMapper.deleteAllBig(scenicSpot.getSpotId(),1);
		//添加长视频
		List<HxUserVideo> list = addBigVideo(big_videos,scenicSpot.getSpotId());
		if (!ObjectHelper.isEmpty(list)){
			hxUserVideoMapper.addList(list);
		}
	}

	@Transactional
	@Override
	public void insert(ScenicSpot scenicSpot, String big_videos) throws Exception {
		String geoPoint = scenicSpot.getGeoPoint();
		if (StringUtils.isNoneBlank(geoPoint)){
			String[] split = geoPoint.split(",");
			Map<String, String> map = ZZLocationUtils.get_province_city_district_by_gaode_log_lat(split[0].replace("{", "").split("=")[1], split[1].replace("}", "").split("=")[1]);
			scenicSpot.setCountry(ObjectHelper.isEmpty(map.get("country"))?scenicSpot.getCountry():map.get("country"));
			scenicSpot.setArea_code(ObjectHelper.isEmpty(map.get("area_code"))?scenicSpot.getArea_code():map.get("area_code"));
			scenicSpot.setCity(ObjectHelper.isEmpty(map.get("city"))?scenicSpot.getCity():map.get("city"));
			scenicSpot.setCity_code(ObjectHelper.isEmpty(map.get("city_code"))?scenicSpot.getCity_code():map.get("city_code"));
			scenicSpot.setDistrict(ObjectHelper.isEmpty(map.get("district"))?scenicSpot.getDistrict():map.get("district"));
			scenicSpot.setProvince(ObjectHelper.isEmpty(map.get("province"))?scenicSpot.getProvince():map.get("province"));
		}
		scenicSpot.setDataSources("淮信");
		scenicSpot.setVisitsNum(0l);
		long currentTimeMillis = System.currentTimeMillis();
		scenicSpot.setDataTime(currentTimeMillis);
		scenicSpot.setCreate_time(currentTimeMillis);
		//MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, scenicSpot, "spotId");
		//MongoService.f
		MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, scenicSpot,"spotId");
		esQueryScenicSpotInsert(scenicSpot);
		
		//添加长视频
		List<HxUserVideo> list = addBigVideo(big_videos,scenicSpot.getSpotId());
		if (!ObjectHelper.isEmpty(list)){
			hxUserVideoMapper.addList(list);
		}
		
	}

	/**
	 * 封装长视频
	 * @param big_videos
	 * @param spotId 
	 * @return
	 */
	private List<HxUserVideo> addBigVideo(String big_videos, Long spotId) {
		if(ObjectHelper.isEmpty(big_videos)){
			return null;
		}
		
		List<Map> json2List = JsonUtils.json2List(big_videos, Map.class);
		if(ObjectHelper.isEmpty(json2List)){
			return null;
		}
		List<HxUserVideo> hxUserVideos = new ArrayList<>();
		for (Map map : json2List) {
			String coverUrl = map.get("coverUrl").toString();
			String videoUrl = map.get("videoUrl").toString();
			String fileId = map.get("fileId").toString();
			String dec = map.get("dec").toString();
			HxUserVideo hxUserVideo = new HxUserVideo();
			hxUserVideo.setContent(videoUrl);
			hxUserVideo.setCreate_time(System.currentTimeMillis());
			hxUserVideo.setDec(dec);
			hxUserVideo.setFile_id(fileId);
			hxUserVideo.setImage(coverUrl);
			hxUserVideo.setUser_id(0L);
			hxUserVideo.setName_id(spotId);
			hxUserVideo.setName_type(1);
			hxUserVideo.setStatus(2);
			hxUserVideo.setVideo_type(2);
			hxUserVideos.add(hxUserVideo);
		}
		return hxUserVideos;
	}

	@Override
	public boolean recommendById(Long spotId, Integer type) {
		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put("spotId", spotId);
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("isRecommend", type);
		boolean updateOne = MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, filterMap, updateMap);
		
		
		Map<String, Object> filterMap1 = new HashMap<>();
		filterMap1.put("name_id", spotId);
		filterMap1.put("type", 1);
		Map<String, Object> updateMap1 = new HashMap<String, Object>();
		updateMap1.put("is_official_recommend", type);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap1, updateMap1);
		return updateOne;
		
	}

	@Override
	public boolean isHotById(Long spotId, Integer type) {
		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put("spotId", spotId);
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("isHot", type);
		boolean updateOne = MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ScenicSpot, filterMap, updateMap);
		
		Map<String, Object> filterMap1 = new HashMap<>();
		filterMap1.put("name_id", spotId);
		filterMap1.put("type", 1);
		Map<String, Object> updateMap1 = new HashMap<String, Object>();
		updateMap1.put("is_hot", type);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap1, updateMap1);
		return updateOne;
		
	}
	
	/**
	 * 景点添加 es数据导入
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public static void esQueryScenicSpotInsert(ScenicSpot scenicSpot) throws Exception {
		ESQuery esQuery = new ESQuery();
		esQuery.setArea_code(scenicSpot.getArea_code());
		esQuery.setName_id(scenicSpot.getSpotId());
		esQuery.setName(scenicSpot.getSpotName());
		esQuery.setType(1);
		Integer cover_type = scenicSpot.getCover_type();
		if (cover_type==2){
			esQuery.setCover_type(2);
			String banner_videos = scenicSpot.getBanner_videos();
			List<Map> json2List = JsonUtils.json2List(banner_videos, Map.class);
			if (ObjectHelper.isEmpty(json2List)){
				throw new BaseException("轮播图异常！");
			}
			esQuery.setImage(json2List.get(0).get("coverUrl").toString());
			esQuery.setCover_video(json2List.get(0).get("videoUrl").toString());
		} else {
			String image = null;
			if (scenicSpot.getImageUrls()!=null){
				String imageUrls = scenicSpot.getImageUrls().toString();
				String replace = imageUrls.replace("[", "");
				String replace2 = replace.replace("]", "");
				image = replace2.split(",")[0];
			}
			
			esQuery.setImage(image);
			esQuery.setCover_type(1);
		}
		
		
		esQuery.setIs_hot(0);
		esQuery.setSales(0);
		esQuery.setPrice(null);
		esQuery.setComment_count(0l);
		esQuery.setComment_score(0F);
		esQuery.setLocation(scenicSpot.getLocation());
		esQuery.setTar(scenicSpot.getTags());
		esQuery.setGeo_point(scenicSpot.getGeoPoint());
		esQuery.setCreate_time(scenicSpot.getCreate_time());
		esQuery.setIs_hide(scenicSpot.getIsHide());
		esQuery.setArea_code_name(scenicSpot.getCity());
		//整合经纬度
		if(!ObjectHelper.isEmpty(scenicSpot.getGeoPoint())){
			String str = scenicSpot.getGeoPoint();
			esQuery.setPoint(point(str));
		}
		MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, esQuery, "id");
	}
	
	/**
	 * 景点修改
	 */
	public static void esQueryScenicSpotUpdate(ScenicSpot scenicSpot){
		
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("name_id", scenicSpot.getSpotId());
		filterMap.put("type", 1);
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("name", scenicSpot.getSpotName());
		updateMap.put("area_code", scenicSpot.getArea_code());
		
		
		//处理封面  2-封面小视频 1-图片
		Integer cover_type = scenicSpot.getCover_type();
		if (cover_type==2){
			updateMap.put("cover_type", 2);
			String banner_videos = scenicSpot.getBanner_videos();
			List<Map> json2List = JsonUtils.json2List(banner_videos, Map.class);
			if (ObjectHelper.isEmpty(json2List)){
				throw new BaseException("轮播图异常！");
			}
			updateMap.put("image", json2List.get(0).get("coverUrl").toString());
			updateMap.put("cover_video", json2List.get(0).get("videoUrl").toString());
		} else {
			String image = null;
			if (scenicSpot.getImageUrls()!=null){
				String imageUrls = scenicSpot.getImageUrls().toString();
				String replace = imageUrls.replace("[", "");
				String replace2 = replace.replace("]", "");
				image = replace2.split(",")[0];
			}
			
			updateMap.put("image", image);
			updateMap.put("cover_type", 1);
			updateMap.put("cover_video",null);
		}
		
	
		updateMap.put("location", scenicSpot.getLocation());
		updateMap.put("tar", scenicSpot.getTags());
		updateMap.put("geo_point", scenicSpot.getGeoPoint());
		if(!ObjectHelper.isEmpty(scenicSpot.getGeoPoint())){
			String str = scenicSpot.getGeoPoint();
			updateMap.put("point",point(str) );
		}
		updateMap.put("area_code_name", scenicSpot.getCity());
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap, updateMap);
	}
	
	/**
	 * 整合经纬度
	 */
	public static String point(String str){
		str = str.replace("{", "");
		str = str.replace("}", "");
		str = str.replace(" ", "");
		String[] split = str.split(",");
		
		String lon = split[0].substring(4);
		String lat =split[1].substring(4,split[1].length());
		
		return lat+","+lon;
	}
	
}
