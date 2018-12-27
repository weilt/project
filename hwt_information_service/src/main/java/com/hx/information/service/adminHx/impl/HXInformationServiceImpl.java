package com.hx.information.service.adminHx.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.es.ESQuery;
import com.hwt.domain.entity.information.HwInformationComment;
import com.hwt.domain.entity.information.vo.HwInformationCommentAdminVo;
import com.hwt.domain.entity.mg.information.HwInformation;
import com.hwt.domain.mapper.information.HwInformationCommentMapper;
import com.hx.core.mongo.service.MongoService;
import com.hx.core.mongo.value.MongoOperator;
import com.hx.core.mongo.value.MongoQueryCondition;
import com.hx.core.mongo.value.MongoQueryProjections;
import com.hx.core.mongo.value.MongoQueryValue;
import com.hx.core.mongo.value.Sort;
import com.hx.core.mongo.value.MongoQueryCondition.LinkKey;
import com.hx.core.page.asyn.PageResult;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.utils.ObjectHelper;
import com.hx.information.service.adminHx.HXInformationService;

@Service
public class HXInformationServiceImpl implements HXInformationService {

	@Autowired
	private HwInformationCommentMapper hwInformationCommentMapper;
	@Override
	public PageResult<Map<String, Object>> query(Map<String, Object> map) {
		Integer pageSize =  (Integer) map.get("pageSize");
		
		Integer currentPage =  (Integer) map.get("currentPage");
		
		String orderBy = (String) map.get("orderBy");
		
		String titel = (String) map.get("titel");
		
		
		//查询条件封装
		MongoQueryCondition condition = new MongoQueryCondition(LinkKey.and);
		if (!ObjectHelper.isEmpty(titel)){
			condition.add(new MongoQueryValue(MongoOperator.regex, "titel", titel));
		}
		
		
		//查询总条数
		Long count = MongoService.findPageCount(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, condition);
		
		
		
		//排序
		String[] split = orderBy.split(" ");
		Sort sort = null;
		if (split[1].equals("asc")){
			sort = new Sort(split[0],Sort.ASC);
		}else{
			sort = new Sort(split[0],Sort.DESC);
		}
		//不显示字段 
		MongoQueryProjections projection = new MongoQueryProjections();
		projection.put("author", 0);
		projection.put("information_type", 0);
		projection.put("content", 0);
		projection.put("source", 0);
		
		List<Map<String,Object>> findByPage = MongoService.findByPage(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, projection , condition, sort , currentPage, pageSize);
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		
		pageResult.setCount(count);
		pageResult.setDataList(findByPage);
		return pageResult;
	}

	@Override
	public void insert(HwInformation hwInformation) throws Exception {
		HwInformation insertIncId = MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, hwInformation, "information_id");
		ESQuery esQuery = createESQuery(insertIncId);
		MongoService.insertIncId(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, esQuery, "id");
	}

	private ESQuery createESQuery(HwInformation hwInformation) {
		ESQuery esQuery = new ESQuery();
		esQuery.setCan_not_recommended(0);
		esQuery.setComment_count(hwInformation.getComment_num());
		esQuery.setCreate_time(hwInformation.getCreate_time());
		esQuery.setImage(hwInformation.getImages());
		esQuery.setIs_hide(hwInformation.getIs_hide());
		esQuery.setName(hwInformation.getTilte());
		esQuery.setAuthor(hwInformation.getAuthor());
		esQuery.setIs_hot(0);
		esQuery.setTar(hwInformation.getKeyword());
		esQuery.setIs_display(hwInformation.getIs_display());
		esQuery.setType(4);
		esQuery.setName_id(hwInformation.getInformation_id());
		return esQuery;
	}

	@Override
	public Map<String, Object> queryInfo(Long information_id) {
		MongoQueryValue queryValue = new MongoQueryValue(MongoOperator.eq, "information_id", information_id);
		Map<String, Object> findOne = MongoService.findOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, queryValue , null);
		return findOne;
	}

	@Override
	public void update(Map<String, Object> update, Map<String, Object> filter) {
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_Information, filter, update);
		
	}

	@Override
	public PageResult<HwInformationCommentAdminVo> query_comment(Map<String, Object> map) {
		Long information_id =  Long.parseLong(map.get("information_id").toString());
		Long count = hwInformationCommentMapper.query_comment_count_admin(information_id);
		
		List<HwInformationCommentAdminVo> list = hwInformationCommentMapper.query_comment_admin(map);
		PageResult<HwInformationCommentAdminVo> pageResult = new PageResult<>();
		pageResult.setCount(count);
		pageResult.setDataList(list);
		return pageResult;
	}

	@Override
	public void comment_hide(Long comment_id) {
		hwInformationCommentMapper.comment_hide(comment_id);
		
	}

	@Override
	public void comment_not_hide(Long comment_id) {
		hwInformationCommentMapper.comment_not_hide(comment_id);
		
	}

	@Override
	@Transactional
	public void delete(Long comment_id) {
		HwInformationComment hwInformationComment = hwInformationCommentMapper.selectByPrimaryKey(comment_id);
		if (ObjectHelper.isEmpty(hwInformationComment)){
			throw new RuntimeException("该评论已删除！");
		}
		if (hwInformationComment.getParent_comment_id()==0){
			//删除与这条评论相关的
			hwInformationCommentMapper.deleteByRelation(comment_id);
		}
		
		//删除本身
		hwInformationCommentMapper.deleteByPrimaryKey(comment_id);
		
		
		
	}

	@Override
	public void updateEsq(Map<String, Object> update1, Map<String, Object> filter1) {
		
		MongoService.updateOne(HXSystemConfig.MONGO_Collection_NAME_hwt, HXSystemConfig.MONGO_Collection_NAME_ESQuery, filter1, update1);
	}


}
