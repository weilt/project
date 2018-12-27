package com.hx.bureau.service.hx.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwt.domain.entity.bureau.HwTravelBureau;
import com.hwt.domain.entity.es.ESQuery;
import com.hwt.domain.entity.mg.scenic.ScenicSpot;
import com.hwt.domain.entity.mg.travel.line.HwTravelLine;
import com.hwt.domain.entity.mg.travel.line.LinePrice;
import com.hwt.domain.entity.mg.travel.line.vo.HwTravelLineDetailsVo;
import com.hwt.domain.entity.mg.travel.line.vo.HwTravelLineOtherVo;
import com.hwt.domain.entity.mg.travel.line.vo.HwTravelLineUpdateVo;
import com.hwt.domain.entity.order.vo.HwOrderCommentVo;
import com.hwt.domain.entity.system.HwAttribute;
import com.hwt.domain.entity.user.HxUser;
import com.hwt.domain.entity.user.collect.HwUserCollect;
import com.hwt.domain.mapper.bureau.HwTravelBureauMapper;
import com.hwt.domain.mapper.order.HwOrderCommentMapper;
import com.hwt.domain.mapper.system.HwAttributeMapper;
import com.hwt.domain.mapper.user.collect.HwUserCollectMapper;
import com.hx.bureau.service.Vo.PageResultHxLine;
import com.hx.bureau.service.hx.HxLineService;
import com.hx.core.es.entity.SearchRequestParam;
import com.hx.core.es.utils.ElasticsearchUtils;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryCondition;
import com.hx.core.mongo.value.MongoQueryCondition.LinkKey;
import com.hx.core.redis.RedisCache;
import com.hx.core.mongo.value.MongoQueryProjections;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.mongo.value.Sort;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;

@Service
public class HxLineServiceImpl implements HxLineService{
	
	@Autowired
	private HwAttributeMapper hwAttributeMapper;
	
	@Autowired
	private HwUserCollectMapper hwUserCollectMapper;
	
	@Autowired
	private HwOrderCommentMapper hwOrderCommentMapper;
	
	@Autowired
	private HwTravelBureauMapper hwTravelBureauMapper;
	
	@Override
	public PageResultHxLine queryByMap(Map<String, Object> map) {

		Integer pageSize =  (Integer) map.get("pageSize");
		
		Integer currentPage =  (Integer) map.get("currentPage");
		
		Long bureau_id =  (Long) map.get("bureau_id");
		
		String orderBy = (String) map.get("orderBy");
		
		String line_name = (String) map.get("line_name");
		
		//排序
		String[] split = orderBy.split(" ");
		Sort sort = null;
		if (split[1].equals("asc")){
			sort = new Sort(split[0],Sort.ASC);
		}else{
			sort = new Sort(split[0],Sort.DESC);
		}
		
		
		MongoQueryProjections projection = null;
		//projection.put("is_hide", 1);
		List<Map<String, Object>> listAll = MongoService.findAnd(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine,new MongoQueryValue(MongoOperator.eq, "bureau_id", bureau_id) , sort, projection);
		
		Long online = 0l;//上线数

		Long downline = 0l;//下线数
		if (!ObjectHelper.isEmpty(listAll)){
			for (Map<String, Object> map2 : listAll) {
				if ((int)map2.get("is_hide") == 0){
					online++;
				}else{
					downline++;
				}
			}
		}
		
		
		MongoQueryProjections projection1 = new MongoQueryProjections();
		projection1.put("line_id", 1);
		projection1.put("line_name", 1);
		projection1.put("line_price", 1);
		projection1.put("create_time", 1);
		projection1.put("is_hide", 1);
		projection1.put("line_type", 1);
		projection1.put("sell_num", 1);
		MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
		
		condition.add(new MongoQueryValue(MongoOperator.eq, "bureau_id", bureau_id));
		
		
		
		if (!ObjectHelper.isEmpty(line_name)){
			condition.add(new MongoQueryValue(MongoOperator.regex, "line_name", line_name));
		}
		
		//查询数据
		List<Map<String,Object>> list = MongoService.findByPage(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, projection1, condition, sort, currentPage, pageSize);
		
		//总条数
		long count = MongoService.findPageCount(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, condition );
		
		//封装分类
		if (count>0){
			Map<String, String> type = getAttributeByKeyAtMap("line_type");
			for (Map<String, Object> map2 : list) {
				if (type==null){
					map2.put("line_type", null);
				}else{
					String line_type = map2.get("line_type").toString();
					map2.put("line_type", type.get(line_type));
				}
				
			}
		}
		
		PageResultHxLine pageResultHxLine = new PageResultHxLine();
		pageResultHxLine.setCount(count);
		pageResultHxLine.setDownline(downline);
		pageResultHxLine.setOnline(online);
		pageResultHxLine.setDataList(list);
		return pageResultHxLine;
	}
	
	/**
	 * 根据属性键 获取线路的属性 通过 id - name 的形式返回
	 */
	private  Map<String, String> getAttributeByKeyAtMap(String key){
		List<HwAttribute> attributes = hwAttributeMapper.queryAll(key);
		Map<String, String> map = new HashMap<>();
		if (!ObjectHelper.isEmpty(attributes)){
			for (HwAttribute hwAttribute : attributes) {
				map.put(hwAttribute.getAttribute_id()+"", hwAttribute.getAttribute_name());
			}
		}else {
			return null;
		}
		return map;
	}
	/**
	 * 根据属性键 获取线路的属性 通过 id - name 的形式返回
	 */
	private  List<HwAttribute> getAttributeByKey(String key){
		List<HwAttribute> attributes = hwAttributeMapper.queryAll(key);
		return attributes;
		
	}

	@Override
	public void insert(HwTravelLine hwTravelLine) throws Exception{
		
		Long create_time = System.currentTimeMillis();
		hwTravelLine.setCreate_time(create_time );
		hwTravelLine.setUpdate_time(create_time);
		hwTravelLine.setSell_num(0);
		hwTravelLine.setScore(0.00F);
		hwTravelLine.setScore_num(0l);
		hwTravelLine.setService_score(0);
		hwTravelLine.setMatch_score(0);
		hwTravelLine.setTrip_score(0);
		
		String line_price = hwTravelLine.getLine_price();
		Map<String, Object> json2Map = JsonUtils.json2Map(line_price);
		if (!json2Map.isEmpty()){
			for (String str : json2Map.keySet()) {
				Map<String, Object> map = JsonUtils.json2Map(json2Map.get(str).toString());
				if (map.isEmpty()){
					json2Map.remove(str);
				} else {
					if (ObjectHelper.isEmpty(map.get("adultPrice"))){
						json2Map.remove(str);
					} else {
//						if (ObjectHelper.isEmpty(map.get("childPrice"))){
//							map.remove("childPrice");
//						}
						json2Map.put(str, JsonUtils.Bean2Json(map));
					}
				}
				
			}
		}
		
		hwTravelLine.setLine_price(JsonUtils.Bean2Json(json2Map));
		MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, hwTravelLine, "line_id");
		esQueryLineInsert(hwTravelLine);
	}

	@Override
	public Map<String, Object> queryByLineIdForUpdate(Long line_id) {
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "line_id", line_id);
		MongoQueryProjections projections = null;
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, queryValue, projections );
		Map<String, Object> map = new HashMap<>();
		map.put("hwTravelLine", findOne);
		List<HwAttribute> line_types = getAttributeByKey("line_type");
		map.put("line_types", line_types);
		
		List<HwAttribute> line_services = getAttributeByKey("line_service");
		map.put("line_services", line_services);
		
		
		return map;
	}

	@Override
	public void update(HwTravelLineUpdateVo hwTravelLine) {
		hwTravelLine.setUpdate_time(System.currentTimeMillis());
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("line_id", hwTravelLine.getLine_id());
		
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("line_name", hwTravelLine.getLine_name());
		updateMap.put("day_num", hwTravelLine.getDay_num());
		updateMap.put("line_cover", hwTravelLine.getLine_cover());
		updateMap.put("is_hide", hwTravelLine.getIs_hide());
		updateMap.put("update_time", System.currentTimeMillis());
		updateMap.put("line_type", hwTravelLine.getLine_type());
		updateMap.put("line_service", hwTravelLine.getLine_service());
		updateMap.put("line_images", hwTravelLine.getLine_images());
		updateMap.put("line_info", hwTravelLine.getLine_info());
		updateMap.put("scenics", hwTravelLine.getScenics());
		updateMap.put("scenics_info", hwTravelLine.getScenics_info());
		updateMap.put("line_traffic", hwTravelLine.getLine_traffic());
		updateMap.put("line_hotel", hwTravelLine.getLine_hotel());
		updateMap.put("line_trip", hwTravelLine.getLine_trip());
		updateMap.put("line_restaurant", hwTravelLine.getLine_restaurant());
		updateMap.put("line_cost_info", hwTravelLine.getLine_cost_info());
		updateMap.put("line_notcost_info", hwTravelLine.getLine_notcost_info());
		updateMap.put("line_self_expense", hwTravelLine.getLine_self_expense());
		updateMap.put("line_predetermined_instructions", hwTravelLine.getLine_predetermined_instructions());
		updateMap.put("line_insurance_description", hwTravelLine.getLine_insurance_description());
		updateMap.put("line_cancel_description", hwTravelLine.getLine_cancel_description());
		updateMap.put("line_security_reminding", hwTravelLine.getLine_security_reminding());
		updateMap.put("max_price", hwTravelLine.getMax_price()==null?null:hwTravelLine.getMax_price().toString());
		updateMap.put("min_price", hwTravelLine.getMin_price()==null?null:hwTravelLine.getMin_price().toString());
		
		
		String line_price = hwTravelLine.getLine_price();
		List<String> list = new ArrayList<>();
		Map<String, Object> json2Map = JsonUtils.json2Map(line_price);
		if (!json2Map.isEmpty()){
			Set<String> keySet = json2Map.keySet();
			
			for (String str : keySet) {
				Map<String, Object> map = JsonUtils.json2Map(json2Map.get(str).toString());
				if (map.isEmpty()){
					list.add(str);
				} else {
					if (ObjectHelper.isEmpty(map.get("adultPrice"))){
						list.add(str);
					} 
				}
				
			}
		}
		if (ObjectHelper.isEmpty(list)){
			for (String str : list) {
				json2Map.remove(str);
			}
		}
		updateMap.put("line_price", JsonUtils.Bean2Json(json2Map));
		//数据库中获取，替换更改的字段
//		Map<String, String> priceMap = new HashMap<>(); 
//		for (int i = 0; i < 7; i++) {
//			LinePrice linePrice = new LinePrice();
//			linePrice.setDate("2018-10-2"+(1+i));
//			linePrice.setChildPrice("0.01");
//			linePrice.setAdultPrice("0.01");
//			priceMap.put(linePrice.getDate(), JsonUtils.Bean2Json(linePrice));
//		}
//		findOne.put("line_price", JsonUtils.Bean2Json(priceMap));
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, filterMap, updateMap);
		esQueryLineUpdate(hwTravelLine);
	}

	@Override
	public void deleteById(Long id, Integer type) {
		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put("line_id", id);
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("is_hide", type);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, filterMap, updateMap);
		
		
		Map<String, Object> filterMap1 = new HashMap<>();
		filterMap1.put("name_id", id);
		filterMap1.put("type", 3);
		Map<String, Object> updateMap1 = new HashMap<String, Object>();
		updateMap1.put("is_hide", type);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap1, updateMap1);
	}

	@Override
	public Map<String, Object> qureyAttribute() {
		List<HwAttribute> line_types = hwAttributeMapper.queryAll("line_type");
		List<HwAttribute> line_services = hwAttributeMapper.queryAll("line_service");
		Map<String, Object> map = new HashMap<>();
		map.put("line_types", line_types);
		map.put("line_services", line_services);
		return map;
	}
	
	/**
	 *c添加 es数据导入
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public static void esQueryLineInsert(HwTravelLine hwTravelLine) throws Exception {
		ESQuery esQuery = new ESQuery();
		esQuery.setArea_code(null);
		esQuery.setName_id(hwTravelLine.getLine_id());
		esQuery.setName(hwTravelLine.getLine_name());
		esQuery.setType(3);
		String image = hwTravelLine.getLine_cover();
		
		esQuery.setImage(image);
		esQuery.setIs_hot(0);
		esQuery.setSales(0);
		esQuery.setPrice(hwTravelLine.getMin_price()==null?null:hwTravelLine.getMin_price().toString());
		esQuery.setComment_count(0l);
		esQuery.setComment_score(0F);
		esQuery.setLocation(null);
		esQuery.setTar(null);
		esQuery.setGeo_point(null);
		esQuery.setCreate_time(hwTravelLine.getCreate_time());
		esQuery.setIs_hide(hwTravelLine.getIs_hide());
		MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, esQuery, "id");
	}
	
	/**
	 *  线路修改    es数据导入
	 */
	public static void esQueryLineUpdate(HwTravelLineUpdateVo hwTravelLine){
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("name", hwTravelLine.getLine_name());
		updateMap.put("image", hwTravelLine.getLine_cover());
		updateMap.put("price", hwTravelLine.getMin_price()==null?null:hwTravelLine.getMin_price().toString());
		updateMap.put("is_hide", hwTravelLine.getIs_hide());
		
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("name_id", hwTravelLine.getLine_id());
		filterMap.put("type", 3);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap, updateMap);
	}

	@Override
	public List<Map<String, Object>> qurey_scenic_spot(String scenic) {
		SearchRequestParam param = new SearchRequestParam();
		param.setCurrentPage(1);
		param.setPageSize(10);
		param.setIndexes(new String[]{HXSystemConfig.ES_Collection_NAME_hwt});
		param.setTypes(new String[]{HXSystemConfig.ES_Collection_NAME_ESQuery});
		
		param.setSortField((Map<String, SortOrder>) new HashMap<String, SortOrder>().put("create_time",SortOrder.DESC));
		//高亮显示字段
		//param.setHighlightField(new String[]{"spotName","description","brief","tags","location","ticketInfo","city"});
		Map<String, Object> shouldStr = new HashMap<>();
		shouldStr.put("name", scenic);
		param.setShouldStr(shouldStr);
		
		
		Map<String, Object> mustStr = new HashMap<>();
		mustStr.put("is_hide", 0);
		
		param.setMustStr(mustStr);
		param.setMatchPhrase(false);
	
		//查询景点
		mustStr.put("type", 1);
		List<Map<String, Object>> list = ElasticsearchUtils.searchDataPage(param).getRecordList();
		
		return list;
	}

	@Override
	public Map<String, Object> details(Long line_id, Long user_id) {
		Map<String, Object> findOne = null;
		
		String str = RedisCache.get(HwPrefix.LinePrefix+line_id,3);
		if (ObjectHelper.isEmpty(str)){
			MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "line_id", line_id);
			MongoQueryProjections projections = new MongoQueryProjections();
			projections.put("create_time", 0);
			projections.put("update_time", 0);
			projections.put("serialVersionUID", 0);
			findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, queryValue, projections );
		
			//数据缓存5小时
			RedisCache.setpar(HwPrefix.LinePrefix+line_id, JsonUtils.Bean2Json(findOne), 5*24*60*60*1000,3);
		} else {
			findOne = JsonUtils.json2Map(str);
		}
	
		
		Map<String, Object> map = new HashMap<>();
		map.put("hwTravelLineVo", findOne);
		List<HwAttribute> line_types = getAttributeByKey("line_type");
		map.put("line_types", line_types);
		List<HwAttribute> line_services = getAttributeByKey("line_service");
		map.put("line_services", line_services);
		
		//判断收藏
		if (!ObjectHelper.isEmpty(user_id)){
			HwUserCollect queryIsCollect = hwUserCollectMapper.queryIsCollect(user_id, line_id, 3);
			if (queryIsCollect!=null){
				map.put("is_collection", 1);
				map.put("collect_id", queryIsCollect.getCollect_id());
			}else {
				map.put("is_collection", 0);
			}
		}else {
			map.put("is_collection", 0);
		}
		
		//查询线路最近的一次好评
		List<HwOrderCommentVo> list = hwOrderCommentMapper.query_good_one_comment(line_id,3);
		if (!ObjectHelper.isEmpty(list)){
			map.put("hwOrderCommentVo", list.get(0));
		}else {
			map.put("hwOrderCommentVo", null);
		}
		return map;
	}

	@Override
	public Map<String, Object> getLineAttribute() {
		Map<String, Object> map = new HashMap<>();
		List<HwAttribute> line_types = getAttributeByKey("line_type");
		map.put("line_types", line_types);
		List<HwAttribute> line_services = getAttributeByKey("line_service");
		map.put("line_services", line_services);
		return map;
	}

	@Override
	public HwTravelLineOtherVo details_other(Long user_id, Long line_id) {
		
		HwTravelLineOtherVo hwTravelLineOtherVo = new HwTravelLineOtherVo();
		HwUserCollect queryIsCollect = hwUserCollectMapper.queryIsCollect(user_id, line_id, 3);
		if (queryIsCollect!=null){
			hwTravelLineOtherVo.setIs_collection(1);
			hwTravelLineOtherVo.setCollect_id(queryIsCollect.getCollect_id());
		}else {
			hwTravelLineOtherVo.setIs_collection(0);
		}
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "line_id", line_id);
		MongoQueryProjections projections = new MongoQueryProjections();
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, queryValue, projections );
		Long bureau_id = Long.parseLong(findOne.get("bureau_id").toString());
		HwTravelBureau selectByPrimaryKey = hwTravelBureauMapper.selectByPrimaryKey(bureau_id );
		hwTravelLineOtherVo.setBureau_id(bureau_id);
		hwTravelLineOtherVo.setLine_id(line_id);
		hwTravelLineOtherVo.setPhone(selectByPrimaryKey.getContacts_phome());
		return hwTravelLineOtherVo;
	}

	@Override
	public Map<String, Object> line_price(Long line_id) {
		MongoQueryProjections projections = new MongoQueryProjections();
		projections.put("line_price", MongoQueryProjections.display);
		
		MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
		condition.add(new MongoQueryValue(MongoOperator.eq, "line_id", line_id));
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, condition, projections );
		if (findOne!=null){
			findOne.put("price", findOne.get("line_price"));
			findOne.remove("line_price");
		}
		
		return findOne;
	}


}
