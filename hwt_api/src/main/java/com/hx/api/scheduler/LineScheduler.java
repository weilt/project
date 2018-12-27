package com.hx.api.scheduler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hx.core.mongo.service.MongoService;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;

@Component
@EnableScheduling
public class LineScheduler {
	
	/**
	 * 删除线路的接单日期
	 * @throws ParseException 
	 */
	//@Scheduled(cron = " 0 0/5 * * * ?")
	@Scheduled(cron = "0 0 4 * * ? ")
	public void delete_work_time() throws ParseException{
		Long geToday = geToday();
		BigDecimal max = null;
		BigDecimal min = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String,Object>> findAll = MongoService.findAll(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, null, null);
		if (!ObjectHelper.isEmpty(findAll)){
			for (Map<String, Object> map : findAll) {
				Long line_id = Long.parseLong(map.get("line_id").toString());
				if (!ObjectHelper.isEmpty(map.get("line_price"))){
					Map<String, Object> line_price = JsonUtils.json2Map(map.get("line_price").toString());
					
					if (!ObjectHelper.isEmpty(line_price.keySet())){
						List<String> list = new ArrayList<>();
						Set<String> keySet = line_price.keySet();
						for (String str : keySet) {
							Date parse = simpleDateFormat.parse(str);
							//判断是否小于今天 ，小于今天则添加到移除
							if (parse.getTime()<geToday){
								list.add(str);
								continue;
							}
							//判断价格是否存在，不存在， 则移除
							Object object = line_price.get(str);
							if (object!=null){
								Map<String, Object> price = JsonUtils.json2Map(object.toString());
								if (price!=null){
									if (ObjectHelper.isEmpty(price.get("adultPrice"))){
										list.add(str);
									} else {
										max = getMax(max, price.get("adultPrice").toString());
										min = getMin(min, price.get("adultPrice").toString());
									}
								}
							} else {
								list.add(str);
							}
							
						}
						//移除list里面的
						if (!ObjectHelper.isEmpty(list)){
							for (String string : list) {
								line_price.remove(string);
							}
						}
						
						System.out.println(line_price+"----------");
						//修改
						Map<String, Object> updateMap = new HashMap<>();
						Map<String, Object> updateMap1 = new HashMap<>();
						if (line_price.isEmpty()){
							updateMap.put("line_price", "");
							updateMap1.put("can_not_recommended", 1);
						}else {
							updateMap.put("line_price", JsonUtils.Bean2Json(line_price));
							updateMap1.put("can_not_recommended", 0);
						}
				    	if (max!=null){
				    		updateMap.put("max_price", max.toString());
				    	} else {
				    		updateMap.put("max_price", "");
				    	}
						if (min !=null){
							updateMap.put("min_price", min.toString());
							updateMap1.put("price", min.toString());
						} else {
							updateMap.put("min_price", "");
							updateMap1.put("price", "");
						}
				    	
						Map<String, Object> filterMap = new HashMap<>();
						filterMap.put("line_id", line_id);
						MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_HwTravelLine, filterMap , updateMap );
						
						//es修改
						//Map<String, Object> updateMap1 = new HashMap<>();
				    	//updateMap.put("price", JsonUtils.Bean2Json(line_price));
						Map<String, Object> filterMap1 = new HashMap<>();
						filterMap1.put("name_id", line_id);
						filterMap1.put("type", 3);
						MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.ES_Collection_NAME_ESQuery, filterMap1 , updateMap1 );
						//删除线路缓存
						RedisCache.del(HwPrefix.LinePrefix+"_"+map.get("line_id"),3);
						
					}
					
				}
				
				
			}
			//删除首页缓存
			RedisCache.delRegex(HwPrefix.APPIndexPrefix,2);
		}
	}
	public static void main(String[] args) {
		System.out.println(JsonUtils.Bean2Json(new HashMap<>().isEmpty()));
	}
	/**
	 * 获取今日凌晨的时间戳
	 * @return
	 */
	private Long geToday() {
		Long currentTimestamps=System.currentTimeMillis();
        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        return currentTimestamps-(currentTimestamps+60*60*8*1000)%oneDayTimestamps;
	}
	
	/**
	 * 获取最大
	 * 
	 */
	private BigDecimal getMax(BigDecimal max,String str){
		if (max==null){
			return new BigDecimal(str);
		} else {
			if (max.compareTo(new BigDecimal(str))== -1){
				return new BigDecimal(str);
			} else {
				return max;
			}
		}
	}
	/**
	 * 获取最小
	 */
	private BigDecimal getMin(BigDecimal min,String str){
		if (min==null){
			return new BigDecimal(str);
		}else {
			if (min.compareTo(new BigDecimal(str)) == 1){
				return new BigDecimal(str);
			} else {
				return min;
			}
		}
	}
}
