package com.hx.user.service.impl;

import com.hwt.domain.entity.cicerone.HxCiceroneInfo;
import com.hwt.domain.entity.cicerone.HxCiceroneRelevance;
import com.hwt.domain.entity.cicerone.HxCiceroneType;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneDetails;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneInfoBaseVo;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneInfoUserInfo;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneInfoVo;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneInfoVoInfo;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneTypeVo;
import com.hwt.domain.entity.cicerone.vo.HxCiceroneWorkTimeVo;
import com.hwt.domain.entity.es.ESQuery;
import com.hwt.domain.entity.mg.cicerone.MgCiceroneInfo;
import com.hwt.domain.entity.price.HwPrice;
import com.hwt.domain.entity.price.vo.HwPriceVo;
import com.hwt.domain.entity.user.Vo.HxYearsVo;
import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.collect.HwUserCollect;
import com.hwt.domain.mapper.cicerone.HxCiceroneInfoMapper;
import com.hwt.domain.mapper.cicerone.HxCiceroneRelevanceMapper;
import com.hwt.domain.mapper.cicerone.HxCiceroneTypeMapper;
import com.hwt.domain.mapper.price.HwPriceMapper;
import com.hwt.domain.mapper.user.HxUserMapper;
import com.hwt.domain.mapper.user.collect.HwUserCollectMapper;
import com.hx.core.consts.HxSystemConst;
import com.hx.core.exception.BaseException;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryCondition;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.mongo.value.MongoQueryCondition.LinkKey;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.*;
import com.hx.user.service.HxCiceroneService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Auther: Zhoudu
 * @Date: 2018/7/9 16:27
 * @Description:
 */
@Service
public class HxCiceroneServiceImpl implements HxCiceroneService {


    @Autowired
    private HxCiceroneInfoMapper mapper;


    @Autowired
    private HxCiceroneTypeMapper typeMapper;

    @Autowired
    private HxCiceroneRelevanceMapper relevanceMapper;
    
    @Autowired
    private HxUserMapper hxUserMapper;
    
    @Autowired
    private HwUserCollectMapper hwUserCollectMapper;
    
    @Autowired
    private HwPriceMapper hwPriceMapper;


    @Transactional
    @Override
    public Boolean apply(LoginVo loginVo, String name, String identityNo, String identityPositive,
                         String identityNegative, String identityHold, String areaCity, String areaCityName) throws Exception {
        Long birthday = IDCardUtil.getBirthDay(identityNo);
        if (null == birthday){
            throw new BaseException("身份证号码错误，请重新填写！");
        }
        long currentTimeMillis = System.currentTimeMillis();
        Long userId = loginVo.getUser_id();
		//存在
        HxCiceroneInfo hxCiceroneInfo = mapper.findByUserId(userId );
        if (ObjectHelper.isNotEmpty(hxCiceroneInfo)){
            //审核通过
            if (hxCiceroneInfo.getStatus() == HxSystemConst.CiceroneStatus.STATUS_PASS.getCode()){
                throw new BaseException("审核已通过，请不要重复申请！");
            }
            hxCiceroneInfo.setReal_name(name);
            hxCiceroneInfo.setIdentity_no(identityNo);
            hxCiceroneInfo.setIdentity_positive(identityPositive);
            hxCiceroneInfo.setIdentity_negative(identityNegative);
            hxCiceroneInfo.setIdentity_hold(identityHold);
            hxCiceroneInfo.setService_area_city(areaCity);
            hxCiceroneInfo.setService_area_city_name(areaCityName);
            hxCiceroneInfo.setBirthday(birthday);
            hxCiceroneInfo.setCreate_time(currentTimeMillis);
            hxCiceroneInfo.setStatus(HxSystemConst.CiceroneStatus.STATUS_WAIT.getCode());

            mapper.updateByPrimaryKeySelective(hxCiceroneInfo);
        }else {
            hxCiceroneInfo = new HxCiceroneInfo();
            hxCiceroneInfo.setUser_id(userId);
            hxCiceroneInfo.setReal_name(name);
            hxCiceroneInfo.setIdentity_no(identityNo);
            hxCiceroneInfo.setIdentity_positive(identityPositive);
            hxCiceroneInfo.setIdentity_negative(identityNegative);
            hxCiceroneInfo.setIdentity_hold(identityHold);
            hxCiceroneInfo.setService_area_city(areaCity);
            hxCiceroneInfo.setService_area_city_name(areaCityName);
            hxCiceroneInfo.setCreate_time(currentTimeMillis);
            hxCiceroneInfo.setBirthday(birthday);
            mapper.insertSelective(hxCiceroneInfo);
        }
        
        //判断mong是否存在
        MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "user_id", loginVo.getUser_id());
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, queryValue, null);
        if (findOne==null){
        	MgCiceroneInfo mgCiceroneInfo = new MgCiceroneInfo();
            mgCiceroneInfo.setUser_id(loginVo.getUser_id());
            MongoService.insert(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, mgCiceroneInfo);
        }
		
        
        //判断ESmong是否存在
        MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
        condition.add(new MongoQueryValue(MongoOperator.eq, "name_id", loginVo.getUser_id()));
        condition.add(new MongoQueryValue(MongoOperator.eq, "type", 2));
        Map<String, Object> findOne2 = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, condition, null);
        if (findOne2==null){
        	 ESQuery esQuery = new ESQuery();
             esQuery.setType(2);
             esQuery.setUser_icon(loginVo.getUser_icon());
             esQuery.setName_id(userId);
             esQuery.setName(name);
             esQuery.setCreate_time(currentTimeMillis);
             esQuery.setArea_code(areaCity);
             esQuery.setArea_code_name(areaCityName);
             esQuery.setIs_hide(1);
             MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, esQuery, "id");
        }else {
        	
        	
        	Map<String, Object> updateMap = new HashMap<>();
        	updateMap.put("user_icon", loginVo.getUser_icon());
        	updateMap.put("name", name);
        	updateMap.put("create_time", currentTimeMillis);
        	updateMap.put("area_code", areaCity);
        	updateMap.put("area_code_name", areaCityName);
        	
			Map<String, Object> filterMap = new HashMap<>();
			filterMap.put("id", findOne2.get("id"));
			MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap , updateMap );
        }
       
        return Boolean.TRUE;
    }

    @Transactional
    @Override
    public Boolean modify(LoginVo loginVo,String autograph, BigDecimal everyday_price, Integer is_open, Integer sex, String phone, String tag,String workTime, String ciceroneType) {
        HxCiceroneInfo hxCicer = mapper.findByUserId(loginVo.getUser_id());
        //Optional<HxCiceroneInfo> optional = Optional.of(hxCicer);
//        if (hxCicer==null){
//        	 throw new BaseException("请先填写导游申请，在申请通过后再添加您的信息！");
//        }
//        if ( hxCicer.getStatus() != HxSystemConst.CiceroneStatus.STATUS_PASS.getCode()){
//            throw new BaseException("正在审核中，请通过后再添加您的信息！");
//        }
        if (hxCicer==null){
       	 throw new BaseException("请先填写导游申请！");
       }
        hxCicer.setAutograph(autograph);
        hxCicer.setEveryday_price(everyday_price);
//        hxCicer.setCover(cover);
        hxCicer.setSex(sex);
        hxCicer.setPhone(phone);
        hxCicer.setTag(tag);
        hxCicer.setIs_open(is_open);
       // hxCicer.setWork_time(workTime);
//        hxCicer.setCicerone_type(ciceronType);
//        hxCicer.setStatus(HxSystemConst.CiceroneStatus.STATUS_WAIT.getCode());
        //删除以前的类型
        relevanceMapper.deleteByCicId(loginVo.getUser_id());
        List<HxCiceroneRelevance> ciceroneRelevances = new ArrayList<>();

        if (ObjectHelper.isNotEmpty(ciceroneType)){
            String[] ciceroneTypes = ciceroneType.split(",");
            if (ciceroneTypes.length > 0){
                Arrays.asList(ciceroneTypes).stream().forEach(
                        l -> {
                            HxCiceroneRelevance hxCiceroneRelevance = new HxCiceroneRelevance();
                            hxCiceroneRelevance.setCic_id(loginVo.getUser_id());
                            hxCiceroneRelevance.setType_id(Long.parseLong(l));
                            hxCiceroneRelevance.setCreate_time(System.currentTimeMillis());
                            ciceroneRelevances.add(hxCiceroneRelevance);
                        }
                );
                relevanceMapper.insertCollection(ciceroneRelevances);
            }
        }
        mapper.updateByPrimaryKeySelective(hxCicer);
        
        //修改ESmong
        MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
        condition.add(new MongoQueryValue(MongoOperator.eq, "name_id", loginVo.getUser_id()));
        condition.add(new MongoQueryValue(MongoOperator.eq, "type", 2));
        Map<String, Object> findOne2 = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, condition, null);
    	
        Map<String, Object> updateMap = new HashMap<>();
    	updateMap.put("autograph", autograph);
    	updateMap.put("is_hide", is_open);
    	updateMap.put("price", everyday_price.toString());
    	
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("id", findOne2.get("id"));
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap , updateMap );
        
		RedisCache.del(HwPrefix.CiceronePrefix+loginVo.getUser_id(),3);
        return Boolean.TRUE;
    }

    @Override
    public List<HxCiceroneTypeVo> getCiceroneType() {
        List<HxCiceroneTypeVo> result = typeMapper.findAll();
        return result;
    }


    @Override
    public List<HxCiceroneInfoVo> searchCicerone(String cityCode, Integer sex, Integer years, Long ciceroneType, Integer orderBy, Integer pageIndex, Integer pageSize) {
        Map<String,Object> param = new HashMap<>();
        param.put("cityCode",cityCode);
        
        param.put("sex",sex);
//        if (null != years && years > 0){
//            Long beginTime = DateUtils.beginYear(years);
//            Long endTime = DateUtils.endYear(years);
//            param.put("startYear",beginTime);
//            param.put("endYear",endTime);
//        }
        if (null != years && years > 0){
            param.put("tag",String.valueOf(years/10).substring(0,1) + "0");
        }
        param.put("ciceroneType",ciceroneType);
        if (null != orderBy && orderBy > 0){
            param.put("orderBy",Enums.OrderBy.getColumn(orderBy));
        }
        param.put("pageIndex",(pageIndex <= 1 ? 0 : pageIndex  - 1) * pageSize);
        param.put("pageSize",pageSize);
        List<HxCiceroneInfoVo> list = mapper.queryByList(param);
        if (null == list || list.isEmpty()){
            return Collections.EMPTY_LIST;
        }
       
        return list;
    }

    /**
     * 判断是否存在
     * @param userId
     * @return
     */
    private boolean exists(final Long userId){
        return mapper.countByUserId(userId) > HxSystemConst.CiceroneStatus.STATUS_WAIT.getCode();
    }

    /**
     * 获取年份
     * @return
     */
    @Override
    public List<HxYearsVo> getYears(){
        String years = RedisCache.get(HxSystemConst.HX_YEAS_KEY);
        List<HxYearsVo> yearsVoList = new ArrayList<>();
        if (StringUtils.isEmpty(years)){
            Map<Integer,String> map = DateUtils.getBeforeYears(4,3);
            List<HxYearsVo> finalYearsVoList = yearsVoList;
            map.keySet().stream().forEach(l -> {
                HxYearsVo vo = new HxYearsVo();
                vo.setYear(l);
                vo.setYearValue(map.get(l));
                finalYearsVoList.add(vo);
            });
            String yearsValue = JsonUtils.Bean2Json(yearsVoList);
            RedisCache.set(HxSystemConst.HX_YEAS_KEY,yearsValue,HxSystemConst.EFFECTIVE_SECONDS);
            return finalYearsVoList;
        }
        yearsVoList = JsonUtils.json2List(years,HxYearsVo.class);

        return yearsVoList;
    }

//    @Override
//    public HxCiceroneInfoVo judge_is_cicerone(Long user_id) {
//
//        HxCiceroneInfoVo hxCiceroneInfoVo = mapper.queryHxCiceroneInfoVoByUserId(user_id);
//        return hxCiceroneInfoVo;
//    }

	@Override
	public HxCiceroneInfoVoInfo cicerone_own_info(Long user_id) {
		HxCiceroneInfoVoInfo hxCiceroneInfoVoInfo = mapper.queryHxCiceroneHxCiceroneInfoVoInfoByUserId(user_id);
		if(hxCiceroneInfoVoInfo!=null){
			List<HxCiceroneTypeVo> hxCiceroneTypeVos = typeMapper.query_by_cicerone_id(user_id);
			if (hxCiceroneTypeVos!=null&&hxCiceroneTypeVos.size()>0){
				hxCiceroneInfoVoInfo.setCiceroneTypeVos(hxCiceroneTypeVos);
			}
		}else{
			
				return null;
			
		}
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "user_id", user_id);
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, queryValue, null);
		hxCiceroneInfoVoInfo.setAbout(findOne.get("about")==null?null:findOne.get("about").toString());
		hxCiceroneInfoVoInfo.setWork_time(findOne.get("work_time")==null?null:findOne.get("work_time").toString());
		hxCiceroneInfoVoInfo.setBanner(findOne.get("banner")==null?null:findOne.get("banner").toString());
		return hxCiceroneInfoVoInfo;
	}

	@Override
	public HxCiceroneDetails cicerone_info(Long id, Long User_id) {
		
		
		HxCiceroneDetails hxCiceroneDetails = new HxCiceroneDetails();
		
		//获取基本信息
		HxCiceroneInfoVoInfo hxCiceroneInfoVoInfo = null;
		String str = RedisCache.get(HwPrefix.CiceronePrefix+id,3);
		if (ObjectHelper.isEmpty(str)){
			hxCiceroneInfoVoInfo = mapper.queryHxCiceroneHxCiceroneInfoVoInfoById(id);
			RedisCache.setpar(HwPrefix.CiceronePrefix+id, JsonUtils.Bean2Json(hxCiceroneInfoVoInfo), 5*24*60*60*1000,3);
		} else {
			hxCiceroneInfoVoInfo = JsonUtils.json2Bean(str, HxCiceroneInfoVoInfo.class);
		}
		
		
		if(hxCiceroneInfoVoInfo!=null){
			List<HxCiceroneTypeVo> hxCiceroneTypeVos = typeMapper.query_by_cicerone_id(id);
			if (hxCiceroneTypeVos!=null&&hxCiceroneTypeVos.size()>0){
				hxCiceroneInfoVoInfo.setCiceroneTypeVos(hxCiceroneTypeVos);
			}
		}else{
			
				return null;
			
		}
		
		//获取简介信息和工作时间
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "user_id", id);
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, queryValue, null);
		hxCiceroneInfoVoInfo.setAbout(findOne.get("about")==null?null:findOne.get("about").toString());
		hxCiceroneInfoVoInfo.setWork_time(findOne.get("work_time")==null?null:findOne.get("work_time").toString());
		hxCiceroneInfoVoInfo.setBanner(findOne.get("banner")==null?null:findOne.get("banner").toString());
		
		hxCiceroneDetails.setHxCiceroneInfoVoInfo(hxCiceroneInfoVoInfo);
		hxCiceroneDetails.setIs_collection(0);
		if (!ObjectHelper.isEmpty(User_id)){
			HwUserCollect queryIsCollect = hwUserCollectMapper.queryIsCollect(User_id, id, 2);
			if (queryIsCollect!=null){
				hxCiceroneDetails.setIs_collection(1);
				hxCiceroneDetails.setCollect_id(queryIsCollect.getCollect_id());
			}else {
				hxCiceroneDetails.setIs_collection(0);
			}
		}
		
		return hxCiceroneDetails;
	}

	@Override
	public void update_workTime(String workTime, Long user_id) {
		if (ObjectHelper.isEmpty(workTime)){
			return;
		}
		String[] workTimes = workTime.split(",");
		
		Map<String, Object> WorkTimes = new HashMap<>();
		
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "user_id", user_id);
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, queryValue , null);
		Object work_time = findOne.get("work_time");
		
		
		//将被下单的时间提出来
		if (work_time!=null){
			Map<String, Object> work_timeMap = JsonUtils.json2Map(work_time.toString());
			Set<String> keySet = work_timeMap.keySet();
			for (String string : keySet) {
				HxCiceroneWorkTimeVo json2Bean = JsonUtils.json2Bean(work_timeMap.get(string).toString(), HxCiceroneWorkTimeVo.class);
				if (json2Bean.getStatus()==1){
					WorkTimes.put(string, JsonUtils.Bean2Json(json2Bean));
				}
			}
		}
		//添加新增是时间
		for (int i = 0; i < workTimes.length; i++) {
			String string = workTimes[i];
			if (ObjectHelper.isEmpty(WorkTimes.get(string))){
				HxCiceroneWorkTimeVo hxCiceroneWorkTimeVo = new HxCiceroneWorkTimeVo();
				hxCiceroneWorkTimeVo.setStatus(0);
				hxCiceroneWorkTimeVo.setWork_time(string);
				WorkTimes.put(string, JsonUtils.Bean2Json(hxCiceroneWorkTimeVo));
			}
		}
		
		//修改
		Map<String, Object> updateMap = new HashMap<>();
    	updateMap.put("work_time", JsonUtils.Bean2Json(WorkTimes));
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("user_id", user_id);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, filterMap , updateMap );
		
	}

	@Override
	public void update_about(String about, Long user_id) {
		Map<String, Object> updateMap = new HashMap<>();
    	updateMap.put("about", about);
    	
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("user_id", user_id);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, filterMap , updateMap );
	}

	@Override
	@Transactional
	public void update_cover(String cover, Long user_id) {
		mapper.update_cover(cover,user_id);
		
		Map<String, Object> updateMap = new HashMap<>();
    	updateMap.put("image", cover);
    	
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("name_id", user_id);
		filterMap.put("type", 2);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap , updateMap );
	}

	@Override
	@Transactional
	public void update_about_cover(String about, String cover, Long user_id ,String banner) {
		//MySQL里面数据修改
		mapper.update_cover(cover,user_id);
		
		
		//mong里面数据修改
		Map<String, Object> updateMap = new HashMap<>();
    	updateMap.put("image", cover);
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("name_id", user_id);
		filterMap.put("type", 2);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filterMap , updateMap );
		
		
		Map<String, Object> updateMap1 = new HashMap<>();
		updateMap1.put("about", about);
		updateMap1.put("banner", banner);
    	
		Map<String, Object> filterMap1 = new HashMap<>();
		filterMap1.put("user_id", user_id);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, filterMap1 , updateMap1 );
	}

	@Override
	public HxCiceroneInfoUserInfo cicerone_im_id(Long cicerone_user_id) {
		HxCiceroneInfoUserInfo hxCiceroneInfoUserInfo = new HxCiceroneInfoUserInfo();
		 String im_id = hxUserMapper.queryImIdByUserId(cicerone_user_id);
		 if (StringUtils.isBlank(im_id)){
			 throw new RuntimeException("该用户不存在");
		 }
		 hxCiceroneInfoUserInfo.setIm_id(im_id);
		return hxCiceroneInfoUserInfo;
	}

	@Override
	public String cicerone_work_time(Long cicerone_user_id) {
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "user_id", cicerone_user_id);
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_CiceroneInfo, queryValue , null);
		Object work_time = findOne.get("work_time");
		if (work_time!=null){
			return work_time.toString();
		}
		return null;
	}

	

	@Override
	public List<HwPriceVo> price_mouth_query(Long user_id, String time_str) {
		if (ObjectHelper.isEmpty(time_str)){
			List<HwPriceVo> list = hwPriceMapper.cicerone_price_query_all(user_id);
			return list;
		} else {
			String[] split = time_str.split("-");
			
			int year = Integer.parseInt(split[0]);
			int month = Integer.parseInt(split[1]);
			
			Long start = getFirstDayOfMonth(year, month);
			Long end = getLastDayOfMonth(year, month);
			
			List<HwPriceVo> list = hwPriceMapper.cicerone_price_mouth_query(user_id,start,end);
			return list;
		}
		
	}

	
	/**
	* 获得该月开始时间戳
	* @param year
	* @param month
	* @return
	*/
	public static  Long getFirstDayOfMonth(int year,int month){
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最小天数
	        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最小天数
	        cal.set(Calendar.DAY_OF_MONTH, firstDay);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return cal.getTime().getTime();
	    }
	
	/**
	* 获得该月结束时间戳
	* @param year
	* @param month
	* @return
	*/
	 public static  Long getLastDayOfMonth(int year,int month){
	        Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return (cal.getTime().getTime());
	    }

	@Override
	public void price_delete(Long user_id, String workTime) throws Exception {
		String[] split = workTime.split(",");
		Set<Long> set = new HashSet<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//获取所有的存在的值
		for (String time_str : split) {
			long time = simpleDateFormat.parse(time_str).getTime();
			set.add(time);
		}
		//查询数据库里面存在的
		List<HwPrice> list = hwPriceMapper.selectCiceronePriceByTimesAndUserIdAndStatus(set,user_id,1);
		if (ObjectHelper.isEmpty(list)){
			hwPriceMapper.deleteCiceronePriceBySetAndUserId(set,user_id);
		} else {
			String time_str = list.get(0).getTime_str();
			throw new RuntimeException(time_str+"被预约了，不能删除！");
		}
	}

	@Override
	@Transactional
	public void price_insert_update(Long user_id, String workTime, BigDecimal adult_price) throws Exception {
		String[] split = workTime.split(",");
		
		if (ObjectHelper.isEmpty(split)){
			return;
		}
		Set<Long> set = new HashSet<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		//获取所有的存在的值
		for (String time_str : split) {
			long time = simpleDateFormat.parse(time_str).getTime();
			set.add(time);
		}
		
		//查询数据库里面存在的
		List<HwPrice> list = hwPriceMapper.selectCiceronePriceByTimesAndUserId(set,user_id);
		
		//需要添加的
		List<HwPrice> inset = new ArrayList<>();
		//需要修改的
		List<Long> update = new ArrayList<>();
		long currentTimeMillis = new Date().getTime();
		if (ObjectHelper.isEmpty(list)){
			//说明全是添加
			for (Long time : set) {
				HwPrice creatPrice = creatPrice(adult_price, 
						adult_price, currentTimeMillis, user_id, time, simpleDateFormat.format(new Date(time)));
				inset.add(creatPrice);
			}
			//添加
			hwPriceMapper.insertList(inset);
			
		} else {
			//存在说明需要修改的
			for (HwPrice hwPrice : list) {
				Long time = hwPrice.getTime();
				if (!hwPrice.getAdult_price().toString().equals(adult_price.toString())){
					if (hwPrice.getStatus().equals("0")){
						throw new RuntimeException(hwPrice.getTime_str()+"被预约了，不能修改价格！");
					}
					update.add(time);
				}
				//移除该时间
				set.remove(time);
			}
			
			//修改  存在修改
			if (!ObjectHelper.isEmpty(update)){
				hwPriceMapper.updateCiceroneList(update,currentTimeMillis,user_id,adult_price,adult_price);
			}
			
			
			
			//需要添加的
			if (!ObjectHelper.isEmpty(set)){
				for (Long time : set) {
					HwPrice creatPrice = creatPrice(adult_price, 
							adult_price, currentTimeMillis, user_id, time, simpleDateFormat.format(new Date(time)));
					inset.add(creatPrice);
				}
				//添加  存在添加
				if (!ObjectHelper.isEmpty(inset)){
					hwPriceMapper.insertList(inset);
				}
				
			}
		
		}
	}

	/**
	 * 
	 * @param adult_price      成人价格
	 * @param child_price		儿童价格
	 * @param currentTimeMillis	时间戳
	 * @param user_id	导游id
	 * @param time		时间戳
	 * @param time_str	
	 * @return
	 */
	private HwPrice creatPrice(BigDecimal adult_price,BigDecimal child_price,long currentTimeMillis,long user_id,long time,String time_str) {
		//说明不存在
		HwPrice hwPrice = new HwPrice();
		hwPrice.setAdult_price(adult_price);
		//单位  1-每单 2-每人 3-每天 4-每小时  目前还是每单
		hwPrice.setCompany("1");
		
		hwPrice.setCreate_time(currentTimeMillis);
		hwPrice.setUpdate_time(currentTimeMillis);
		hwPrice.setName_id(user_id);
		hwPrice.setName_type(2);
		//新添加的默认是可被下单的
		hwPrice.setStatus(0);
		hwPrice.setTime(time);
		hwPrice.setTime_str(time_str);
		//人数上限  0-无上限  导游暂定无上限
		hwPrice.setPerson_num(0);
		//儿童价暂定与成人一样
		hwPrice.setChild_price(child_price);
		
		return hwPrice;
		
	}

	
}
