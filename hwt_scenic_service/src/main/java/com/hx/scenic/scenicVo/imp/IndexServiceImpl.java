package com.hx.scenic.scenicVo.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwt.domain.entity.advertisement.vo.HwAdvertisementVo;
import com.hwt.domain.mapper.advertisement.HwAdvertisementMapper;
import com.hx.core.es.entity.SearchRequestParam;
import com.hx.core.es.utils.ElasticsearchUtils;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryCondition;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.mongo.value.Sort;
import com.hx.core.mongo.value.MongoQueryCondition.LinkKey;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HXAttributeId;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.scenic.scenicVo.IndexService;

@Service
public class IndexServiceImpl implements IndexService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);
	
	@Autowired
	private HwAdvertisementMapper hwAdvertisementMapper;
	
	@Override
	public Map<String, Object> searchIndex(String area_code) {
		
		area_code = area_code!=null?area_code:"010";
		//获取app首页的缓存数据
		String index = RedisCache.get(HwPrefix.APPIndexPrefix+area_code,2);
		if (ObjectHelper.isEmpty(index)){
			SearchRequestParam param = new SearchRequestParam();
			param.setCurrentPage(1);
			param.setPageSize(10);
			param.setIndexes(new String[]{HXSystemConfig.ES_Collection_NAME_hwt});
			param.setTypes(new String[]{HXSystemConfig.ES_Collection_NAME_ESQuery});
			
			Map<String, SortOrder> sortMap = new HashMap<String, SortOrder>();
			sortMap.put("is_official_recommend",SortOrder.DESC);
			param.setSortField(sortMap);
			//高亮显示字段
			//param.setHighlightField(new String[]{"spotName","description","brief","tags","location","ticketInfo","city"});
			Map<String, Object> mustStr = new HashMap<>();
			mustStr.put("is_hide", 0);
			
			param.setMustStr(mustStr);
			param.setMatchPhrase(false);
		
			//查询导游
			mustStr.put("type", 2);
			if(!ObjectHelper.isEmpty(area_code)){
				mustStr.put("area_code", area_code);
			}
			
			
			List<Map<String, Object>> cicerones = ElasticsearchUtils.searchDataPage(param).getRecordList();
			//查询线路
			mustStr.put("type", 3);
			mustStr.remove("area_code");
			List<Map<String, Object>> lines = ElasticsearchUtils.searchDataPage(param).getRecordList();
			
			Map<String, Object> map = new HashMap<>();
			map.put("cicerones", cicerones);
			map.put("lines", lines);
			
			//查询轮播图
			List<HwAdvertisementVo> advertisementVos = hwAdvertisementMapper.selectByPosition(HXAttributeId.advertising_position_app_banner_id,System.currentTimeMillis());
			map.put("advertisements", advertisementVos);
			
			//数据缓存5小时
			RedisCache.setpar(HwPrefix.APPIndexPrefix+area_code, JsonUtils.Bean2Json(map), 5*24*60*60*1000,2);
			return map;
		} else {
			return JsonUtils.json2Map(index);
		}
	}

	@Override
	public List<Map<String, Object>> search(String searchKey, Integer type, Integer pageIndex, Integer pageSize, String area_code) {
		List<Map<String,Object>> queryList = null;
		try {
			SearchRequestParam param = new SearchRequestParam();
			param.setCurrentPage(pageIndex);
			param.setPageSize(pageSize);
			param.setIndexes(new String[]{HXSystemConfig.ES_Collection_NAME_hwt});
			param.setTypes(new String[]{HXSystemConfig.ES_Collection_NAME_ESQuery});
			
//			Map<String, SortOrder> sortMap = new HashMap<String, SortOrder>();
//			sortMap.put("is_official_recommend",SortOrder.DESC);
//			param.setSortField(sortMap);
			Map<String, Float> boots = new HashMap<>();
			boots.put("name", 6.0F);
			boots.put("location", 5.0F);
			boots.put("tar", 4.0F);
			param.setBoots(boots );
			//高亮显示字段
			//param.setHighlightField(new String[]{"spotName","description","brief","tags","location","ticketInfo","city"});
			Map<String, Object> shouldStr = new HashMap<>();
			shouldStr.put("name", searchKey);
			shouldStr.put("location", searchKey);
			shouldStr.put("tar", searchKey);
			shouldStr.put("area_code_name", searchKey);
			shouldStr.put("is_official_recommend", 1);
			if (!ObjectHelper.isEmpty(area_code)){
				shouldStr.put("area_code", area_code);
			}
			
			param.setShouldStr(shouldStr);
			
			Map<String, Object> mustStr = new HashMap<>();
			if (type!=null && type>0){
				mustStr.put("type", type);
			}
			mustStr.put("is_hide", 0);
			
			
			param.setMustStr(mustStr);
			param.setMatchPhrase(false);
			
			queryList = ElasticsearchUtils.searchDataPage(param).getRecordList();
		} catch (Exception e) {
			LOGGER.error("ES查询出错：%m",e.getMessage());
			queryList =	queryData(searchKey,type, null, null, null, pageIndex, pageSize);
		}
		return queryList;
	}
	
	
	//MongoDB查询
	private List<Map<String,Object>> queryData (String str, Integer type, Boolean isHot, Boolean isRecommend, String city, Integer pageIndex, Integer pageSize) {
		MongoQueryCondition conditionOr = new MongoQueryCondition(LinkKey.or);
		MongoQueryCondition conditionAnd = new MongoQueryCondition(LinkKey.and);
		if (!StringUtils.isEmpty(str)){
			conditionOr.add(new MongoQueryValue(MongoOperator.regex, "name",str));
			conditionOr.add(new MongoQueryValue(MongoOperator.regex, "tar",str));
			conditionOr.add(new MongoQueryValue(MongoOperator.regex, "location",str));
		}
//	        conditionAnd.add(new MongoQueryValue(MongoOperator.eq, "city",city));
		conditionAnd.add(new MongoQueryValue(MongoOperator.eq, "is_hide",0));
		if (type!=null && type>0){
			conditionAnd.add(new MongoQueryValue(MongoOperator.eq, "type",type));
		}
		if ( null != isHot && isHot)
			conditionAnd.add(new MongoQueryValue(MongoOperator.eq, "is_hot",1));
		if ( null != isRecommend && isRecommend)
			conditionAnd.add(new MongoQueryValue(MongoOperator.eq, "is_recommend",1));
		//复合条件查询
		MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and,conditionOr,conditionAnd);

		List<Map<String, Object>> list = MongoService.findByPage(HXSystemConfig.MONGO_Collection_NAME_hwt,HXSystemConfig.MONGO_Collection_NAME_ESQuery, null, condition, new Sort("id", Sort.DESC), pageIndex, pageSize);
		return list;
	}

	@Override
	public List<Map<String, Object>> like(String like_field, Integer pageIndex, Integer pageSize) {
		List<Map<String,Object>> queryList = null;
		List<Map<String, Object>> recordList = null;
		try {
			SearchRequestParam param = new SearchRequestParam();
			param.setCurrentPage(pageIndex);
			param.setPageSize(pageSize);
			param.setIndexes(new String[]{HXSystemConfig.ES_Collection_NAME_hwt});
			param.setTypes(new String[]{HXSystemConfig.ES_Collection_NAME_ESQuery});
			
//			Map<String, SortOrder> sortMap = new HashMap<String, SortOrder>();
//			sortMap.put("is_official_recommend",SortOrder.DESC);
//			param.setSortField(sortMap);
			//高亮显示字段
			//param.setHighlightField(new String[]{"spotName","description","brief","tags","location","ticketInfo","city"});
			Map<String, Object> shouldStr = new HashMap<>();
			shouldStr.put("name", like_field);
			shouldStr.put("location", like_field);
			shouldStr.put("tar", like_field);
			shouldStr.put("area_code_name", like_field);	
			shouldStr.put("is_official_recommend",1);
			param.setShouldStr(shouldStr);
			
			Map<String, Float> boots = new HashMap<>();
			boots.put("name", 6.0F);
			boots.put("location", 5.0F);
			boots.put("tar", 4.0F);
			param.setBoots(boots );
			Map<String, Object> mustStr = new HashMap<>();
			mustStr.put("is_hide", 0);
			param.setMustStr(mustStr);
			mustStr.put("type", 1);
			param.setMatchPhrase(false);
			queryList = ElasticsearchUtils.searchDataPage(param).getRecordList();
			mustStr.put("type", 3);
			recordList = ElasticsearchUtils.searchDataPage(param).getRecordList();
		} catch (Exception e) {
			LOGGER.error("ES查询出错：%m",e.getMessage());
			queryList =	queryData(like_field,1, null, null, null, pageIndex, pageSize);
			recordList = queryData(like_field,3, null, null, null, pageIndex, pageSize);
		}
		queryList.addAll(recordList);
		return queryList;
	}

	@Override
	public Map<String, Object> indexNew(String area_code, Integer pageIndex) {
		area_code = area_code!=null?area_code:"010";
		//获取app首页的缓存数据
		
		Map<String, Object> map =null;
		pageIndex = pageIndex<1?1:pageIndex;
		if (pageIndex>3){
			return null;
		}
		String index = RedisCache.get(HwPrefix.APPIndexPrefix+area_code+"_"+pageIndex,2);
		index= null;
		if (ObjectHelper.isEmpty(index)){
			SearchRequestParam param = new SearchRequestParam();
			param.setCurrentPage(pageIndex);
			param.setPageSize(3);
			param.setIndexes(new String[]{HXSystemConfig.ES_Collection_NAME_hwt});
			param.setTypes(new String[]{HXSystemConfig.ES_Collection_NAME_ESQuery});
			
//			Map<String, SortOrder> sortMap = new HashMap<String, SortOrder>();
//			sortMap.put("is_official_recommend",SortOrder.DESC);
//			param.setSortField(sortMap);
			//高亮显示字段
			//param.setHighlightField(new String[]{"spotName","description","brief","tags","location","ticketInfo","city"});
			
			Map<String, Object> shouldStr = new HashMap<>();
			shouldStr.put("is_official_recommend",1);
			param.setShouldStr(shouldStr);
			
			
			Map<String, Object> mustStr = new HashMap<>();
			mustStr.put("is_hide", 0);
			mustStr.put("is_display", 1);
			
			param.setMustStr(mustStr);
			param.setMatchPhrase(false);
			
			//排序
			
			//查询导游
			mustStr.put("type", 2);
			
			mustStr.put("area_code", area_code);
			
			List<Map<String, Object>> cicerones = ElasticsearchUtils.searchDataPage(param).getRecordList();
			//查询线路
			mustStr.put("type", 3);
			mustStr.remove("area_code");
			List<Map<String, Object>> lines = ElasticsearchUtils.searchDataPage(param).getRecordList();
			
			
			//查询资讯
			mustStr.put("type",4);
			map = new HashMap<>();
			List<Map<String, Object>> information = ElasticsearchUtils.searchDataPage(param).getRecordList();
			
			//景点
			mustStr.put("type", 1);
			mustStr.put("area_code", area_code);
			List<Map<String, Object>> scenics = ElasticsearchUtils.searchDataPage(param).getRecordList();
			
			List<Map<String, Object>> recommends = new ArrayList<Map<String,Object>>();
			recommends.addAll(information);
			recommends.addAll(cicerones);
			recommends.addAll(lines);
			recommends.addAll(scenics);
			
			map.put("recommends", recommends);
			
			if (pageIndex==1){
				//查询轮播图
				List<HwAdvertisementVo> advertisementVos = hwAdvertisementMapper.selectByPosition(HXAttributeId.advertising_position_app_banner_id,System.currentTimeMillis());
				map.put("advertisements", advertisementVos);
			}
			
			//数据缓存5小时
			RedisCache.setpar(HwPrefix.APPIndexPrefix+area_code+"_"+pageIndex, JsonUtils.Bean2Json(map), 5*24*60*60*1000,2);
			return map;
		} else {
			map = JsonUtils.json2Map(index);
			return map;
		}
		
		
		
	}


}
