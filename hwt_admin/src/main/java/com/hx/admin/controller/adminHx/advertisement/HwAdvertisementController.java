package com.hx.admin.controller.adminHx.advertisement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hwt.domain.entity.advertisement.HwAdvertisement;
import com.hx.admin.base.ResultEntity;
import com.hx.admin.validate.ValidateParam;
import com.hx.adminHx.service.vo.PageResultHxCiceroneInfo;
import com.hx.advertisement.service.adminHx.AdminAdvertisementService;
import com.hx.core.page.asyn.PageResult;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.core.utils.JsonUtils;

/**
 * 广告管理
 * @author Administrator
 *
 */
@RestController
public class HwAdvertisementController {

	@Autowired
	private AdminAdvertisementService adminAdvertisementService;

	/**
	 * 跳转到广告管理
	 * @return
	 */
	@RequestMapping("adminHx/advertisement/list")
	public ModelAndView advertisement_list(){
		
		return new ModelAndView("adminHx/advertisement/advertisement-list");
		
	}
	
	/**
	 * 通过条件查询广告
	 * 
	 * @param pageSize
	 *            页面大小
	 * @param startNum
	 *            起始条数数
	 * @param orderBy
	 *            排序
	 * @param real_name
	 *            导游名字
	 * @param service_area_city_name
	 *            服务城市

	 * @return
	 */
	@RequestMapping("adminHx/advertisement/query")
	public ResultEntity query(@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "0") Integer startNum, String orderBy,
			@RequestParam(name = "paramMap[title]", required = false) String title,
			@RequestParam(name = "paramMap[ad_type]", required = false) Long ad_type) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", pageSize);
		map.put("startNum", startNum);
		map.put("orderBy", orderBy);
		map.put("ad_type", ad_type);
		map.put("title", title);


		PageResult<Map<String, Object>> pageResult = adminAdvertisementService.queryByMap(map);
		return new ResultEntity(pageResult);
	}
	
	/**
	 * 添加
	 */
	@ValidateParam(field={"title","ad_type","ad_position","image","is_online"})
	@RequestMapping("adminHx/advertisement/insert")
	public ResultEntity insert(HwAdvertisement advertisement){
		adminAdvertisementService.insert(advertisement);
		
		//删除首页缓存
		RedisCache.delRegex(HwPrefix.APPIndexPrefix,2);
		return new ResultEntity("200","添加成功");
	}
	
	/**
	 * 通过id查询  修改用
	 */
	@ValidateParam(field={"ad_id"})
	@RequestMapping("adminHx/advertisement/queryInfo")
	public ResultEntity queryInfo(Long ad_id){
		Map<String, Object> map = new HashMap<>();
		HwAdvertisement advertisement = adminAdvertisementService.queryInfo(ad_id);
		map.put("advertisement", advertisement);
		
		//属性查询
		adminAdvertisementService.queryAdvertisementAttribute(map);
		return new ResultEntity(map);
	}
	
	/**
	 * 广告属性查询
	 */
	@RequestMapping("adminHx/advertisement/attribute")
	public ResultEntity advertisementAttribute(Long ad_id){
		Map<String, Object> map = new HashMap<>();
		
		//属性查询
		map = adminAdvertisementService.queryAdvertisementAttribute(map);
		return new ResultEntity(map);
	}
	
	/**
	 * 修改
	 */
	@ValidateParam(field={"ad_id","title","ad_type","ad_position","image","is_online"})
	@RequestMapping("adminHx/advertisement/update")
	public ResultEntity update(HwAdvertisement advertisement){
		
		//属性查询
		adminAdvertisementService.update(advertisement);
		//删除首页缓存
		RedisCache.delRegex(HwPrefix.APPIndexPrefix,2);
		return new ResultEntity("200","修改成功");
	}
	
	/**
	 * 上线
	 */
	@ValidateParam(field={"ad_id"})
	@RequestMapping("adminHx/advertisement/online")
	public ResultEntity online(Long ad_id){
		
		//属性查询
		adminAdvertisementService.online(ad_id);
		return new ResultEntity("200","上线成功");
	}
	
	/**
	 * 下线
	 */
	@ValidateParam(field={"ad_id"})
	@RequestMapping("adminHx/advertisement/not_online")
	public ResultEntity not_online(Long ad_id){
		
		//属性查询
		adminAdvertisementService.not_online(ad_id);
		return new ResultEntity("200","下线成功");
	}
	
	/**
	 * 隐藏
	 */
	@ValidateParam(field={"ad_id"})
	@RequestMapping("adminHx/advertisement/is_hide")
	public ResultEntity is_hide(Long ad_id){
		
		//属性查询
		adminAdvertisementService.is_hide(ad_id);
		return new ResultEntity("200","删除成功");
	}
	
	/**
	 * 恢复
	 */
	@ValidateParam(field={"ad_id"})
	@RequestMapping("adminHx/advertisement/is_not_hide")
	public ResultEntity is_not_hide(Long ad_id){
		
		//属性查询
		adminAdvertisementService.is_not_hide(ad_id);
		return new ResultEntity("200","恢复成功");
	}
	
}
