package com.hx.information.service.hx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.information.HwInformationComment;
import com.hwt.domain.entity.information.HwInformationGood;
import com.hwt.domain.mapper.information.HwInformationCommentGoodMapper;
import com.hwt.domain.mapper.information.HwInformationCommentMapper;
import com.hwt.domain.mapper.information.HwInformationGoodMapper;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryCondition;
import com.hx.core.mongo.value.MongoQueryProjections;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.mongo.value.Sort;
import com.hx.core.mongo.value.MongoQueryCondition.LinkKey;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.information.service.hx.InformationService;

@Service
public class InformationServiceImpl implements InformationService{

	@Autowired
	private  HwInformationCommentMapper hwInformationCommentMapper;
	
	@Autowired
	private HwInformationGoodMapper hwInformationGoodMapper;
	
	@Autowired
	private HwInformationCommentGoodMapper hwInformationCommentGoodMapper;
	
	@Override
	public List<Map<String, Object>> qurey(Map<String, Object> map) {
		
		Integer pageSize =  (Integer) map.get("pageSize");
		
		Long startNum =  Long.parseLong(map.get("startNum").toString());
		
		String orderBy = (String) map.get("orderBy");
		if (ObjectHelper.isEmpty(orderBy)){
			orderBy = "create_time asc";
		}
		
		//查询条件封装
		MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
		condition.add(new MongoQueryValue(MongoOperator.eq, "is_hide", 0));
		condition.add(new MongoQueryValue(MongoOperator.eq, "is_display", 1));
		
		//排序
		String[] split = orderBy.split(" ");
		Sort sort = null;
		if (split[1].equals("asc")){
			sort = new Sort(split[0],Sort.ASC);
			condition.add(new MongoQueryValue(MongoOperator.gt, "information_id", startNum));
		}else{
			sort = new Sort(split[0],Sort.DESC);
			condition.add(new MongoQueryValue(MongoOperator.lt, "information_id", startNum));
		}
		
		//不显示字段 
		MongoQueryProjections projection = new MongoQueryProjections();
		projection.put("information_type", 0);
		projection.put("content", 0);
		projection.put("source", 0);
		
		List<Map<String,Object>> findByPage = MongoService.findByPage(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, projection , condition, sort , 1, pageSize);
		return findByPage;
	}

	@Override
	public Map<String, Object> qureyInfo(Long user_id,Long information_id) {
		Map<String, Object> information = null;
		
		//获取缓存数据
		String str = RedisCache.get(HwPrefix.InformationPrefix+information_id,3);
		if (ObjectHelper.isEmpty(str)){
			MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "information_id", information_id);
			information = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, queryValue , null);
			
			//加入缓存
			RedisCache.setpar(HwPrefix.InformationPrefix+information_id, JsonUtils.Bean2Json(information), 5*24*60*60*1000,3);
		
		} else {
			information = JsonUtils.json2Map(str);
		}
		//
		if (!ObjectHelper.isEmpty(information)){
			information.put("good_user_id", null);
			if (!ObjectHelper.isEmpty(user_id)){
				HwInformationGood record = hwInformationGoodMapper.selectByUser_idAndInformation_id(user_id,information_id);
				if (record != null){
					//说明存在点赞
					information.put("good_user_id", user_id);
				}
			} 
		}
		return information;
	}

	@Override
	@Transactional
	public void information_good(Long user_id, Long information_id) {
		HwInformationGood record = hwInformationGoodMapper.selectByUser_idAndInformation_id(user_id,information_id);
		if (record != null){
			throw new RuntimeException("已经点过赞!");
		}
		Long create_time = System.currentTimeMillis();
		hwInformationGoodMapper.insertGood(user_id,information_id,create_time);
		
		
		//资讯点赞次数加1
		addnum(information_id, 1, "good_num");
	}

	@Override
	@Transactional
	public void information_not_good(Long user_id, Long information_id) {
		int num = hwInformationGoodMapper.deleteGood(user_id,information_id);
		if (num==0){
			throw new RuntimeException("已取消！");
		}
		if (num!=1){
			throw new RuntimeException("数量不对！");
		}
		
		//资讯点赞次数减1
		addnum(information_id, -1, "good_num");
	}

	@Override
	@Transactional
	public HwInformationComment information_comment_insert(Long user_id, Long information_id, Long parent_user_id,
			Long parent_comment_id, String content) {
		HwInformationComment hwInformationComment = new HwInformationComment();
		hwInformationComment.setComment_num(0l);
		hwInformationComment.setContent(content);
		hwInformationComment.setCreate_time(System.currentTimeMillis());
		hwInformationComment.setGood_num(0l);
		hwInformationComment.setInformation_id(information_id);
		hwInformationComment.setIs_hide(0);
		hwInformationComment.setParent_comment_id(parent_comment_id);
		hwInformationComment.setParent_user_id(parent_user_id);
		hwInformationComment.setType(1);
		hwInformationComment.setUser_id(user_id);
		
		//是否是回复的哪一条
		if(parent_comment_id!=null&&parent_comment_id!=0){
			HwInformationComment parent_comment = hwInformationCommentMapper.selectByPrimaryKey(parent_comment_id);
			if(!ObjectHelper.isEmpty(parent_comment)){
				
				if (parent_comment.getRelation_comment_id()==0){
					//说明父级评论是顶级评论，所以评论的关系为现在的父id，为顶级回复
					hwInformationComment.setRelation_comment_id(parent_comment_id);
					hwInformationComment.setIs_top(1);
				}else {
					//说明父级评论不是顶级评论，所以评论的关系为父id的关系id，不是顶级回复
					hwInformationComment.setRelation_comment_id(parent_comment.getRelation_comment_id());
					hwInformationComment.setIs_top(0);
				}
				//顶级回复加1
				hwInformationCommentMapper.parent_comment_num_add(hwInformationComment.getRelation_comment_id(),1);
			}else {
				
				throw new RuntimeException("回复的那条评论已经删除了!");
				
			}
		} else {
			hwInformationComment.setIs_top(0);
			hwInformationComment.setRelation_comment_id(0l);
			//资讯评论次数加1
			addnum(information_id, 1, "comment_num");
		}
		
		hwInformationCommentMapper.insertBackId(hwInformationComment);
		return hwInformationComment;
	}

	@Override
	@Transactional
	public void information_comment_delete(Long user_id, Long comment_id) {
		HwInformationComment hwInformationComment = hwInformationCommentMapper.selectByPrimaryKey(comment_id);
		if (ObjectHelper.isEmpty(hwInformationComment)){
			throw new RuntimeException("该评论已删除！");
		}
		if (hwInformationComment.getUser_id().equals(user_id)){
			throw new RuntimeException("非本人不能超作！");
		}
		if (hwInformationComment.getParent_comment_id()==0){
			//删除与这条评论相关的
			hwInformationCommentMapper.deleteByRelation(comment_id);
			//资讯评论次数减1
			addnum(hwInformationComment.getInformation_id(), -1, "comment_num");
		} else {
			//顶级回复-1
			hwInformationCommentMapper.parent_comment_num_add(hwInformationComment.getRelation_comment_id(),-1);
		}
		
		//删除本身
		hwInformationCommentMapper.deleteByPrimaryKey(comment_id);
		
	}

	/**
	 * 资讯的点赞或者评论次数加减1
	 * @param information_id        资讯id
	 * @param num   1或者-1
	 * @param good_num_or_comment_num  "comment_num"或者"good_num"
	 */
	private void addnum(Long information_id,int num,String good_num_or_comment_num){
		//资讯点赞次数减1
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "information_id", information_id);
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, queryValue , null);
		
		Long good_num = null;
		if (findOne.get(good_num_or_comment_num) == null){
			good_num = 0l;
		}else {
			good_num = Long.parseLong(findOne.get(good_num_or_comment_num).toString());
		}
		good_num = good_num + num;
		Map<String, Object> filterMap = new HashMap<>();
		filterMap.put("information_id", information_id);
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put(good_num_or_comment_num, good_num<0?0:good_num);
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, filterMap, updateMap);
	}

	@Override
	@Transactional
	public void information_comment_good(Long user_id, Long comment_id) {
		Long  create_time = System.currentTimeMillis();
		hwInformationCommentGoodMapper.insertGood(user_id,comment_id,create_time );
		
		//点赞次数加1
		hwInformationCommentMapper.addGood(comment_id,1);
	}

	@Override
	public void information_comment_not_good(Long user_id, Long comment_id) {
		int num = hwInformationCommentGoodMapper.information_comment_not_good(user_id,comment_id);
		if (num==0){
			throw new RuntimeException("已取消！");
		}
		if (num!=1){
			throw new RuntimeException("数量不对！");
		}
		//点赞次数-1
		hwInformationCommentMapper.addGood(comment_id,-1);
	}
}
