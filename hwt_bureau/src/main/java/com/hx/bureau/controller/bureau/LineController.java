package com.hx.bureau.controller.bureau;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hwt.domain.entity.es.ESQuery;
import com.hwt.domain.entity.mg.scenic.ScenicSpot;
import com.hwt.domain.entity.mg.travel.line.HwTravelLine;
import com.hwt.domain.entity.mg.travel.line.vo.HwTravelLineDetailsVo;
import com.hwt.domain.entity.mg.travel.line.vo.HwTravelLineUpdateVo;
import com.hx.bureau.base.ResultEntity;
import com.hx.bureau.service.Vo.PageResultHxLine;
import com.hx.bureau.service.cache.BureauUserCache;
import com.hx.bureau.service.cache.BureauUserCacheTools;
import com.hx.bureau.service.hx.HxLineService;
import com.hx.bureau.util.BaseController;
import com.hx.bureau.validate.ValidateParam;
import com.hx.core.logs.annotation.Log;
import com.hx.core.logs.annotation.Log.LogType;
import com.hx.core.page.asyn.PageResult;
import com.hx.core.redis.RedisCache;
import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.systemconfig.HwPrefix;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 线路
 * @author Administrator
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "API - LineController", description = "线路")
@RestController
public class LineController extends BaseController{
	
	@Autowired
	private HxLineService hxLineService;
	/**
	 * 跳转到线路管理界面
	 * @return
	 */
	@GetMapping("bureau/line/list")
	public ModelAndView list() {
		return new ModelAndView("bureau/line/line-list");
	}
	
	
	/**
	 * 通过条件查询
	 * @param pageSize		页面条数
	 * @param currentPage 	页码
	 * @param orderBy		排序
	 * @param line_name		路线名称
	 * @return
	 */
	@PostMapping("bureau/line/query")
	public ResultEntity query(@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "1") Integer currentPage, String orderBy,
			@RequestParam(name = "paramMap[line_name]", required = false) String line_name) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", pageSize);
		map.put("currentPage", currentPage);
		map.put("orderBy", orderBy);
		map.put("line_name", line_name);
		map.put("bureau_id", sessionUser().getBureau_id());
		PageResultHxLine pageResult = hxLineService.queryByMap(map);
		return new ResultEntity(pageResult);
	}
	
	/**
	 * 添加页面跳转
	 */
	@RequestMapping("bureau/line/insert_page")
	public ModelAndView insert_page() throws Exception{
		
		Map<String, Object> map = hxLineService.getLineAttribute();
		return new ModelAndView("bureau/line/insert").addObject("map", map);
		
		
	}
	/**
	 * 添加
	 */
	@Log(logType = LogType.OPERATION, dec = "添加线路")
	@PostMapping("bureau/line/insert")
	public ResultEntity insert(HwTravelLine hwTravelLine) throws Exception{
		
	
		hwTravelLine.setBureau_id(sessionUser().getBureau_id());
		if("{".equals(hwTravelLine.getLine_price())||"".equals(hwTravelLine.getLine_price())){
			hwTravelLine.setLine_price("{}");
		}
		hxLineService.insert(hwTravelLine);
		//删除首页缓存
				RedisCache.delRegex(HwPrefix.APPIndexPrefix,2);
		return new ResultEntity("200","添加成功");
		
	}
	
	/**
	 * 修改线路
	 * @return
	 */
	@Log(logType = LogType.OPERATION, dec = "修改线路")
	@ValidateParam(field={"line_id"}, message={"Id不能为空"})
	@PostMapping("bureau/line/update")
	public ResultEntity update(HwTravelLineUpdateVo hwTravelLine,@RequestParam(defaultValue="1")Integer type){
			if("{".equals(hwTravelLine.getLine_price())||"".equals(hwTravelLine.getLine_price())){
				hwTravelLine.setLine_price("{}");
			}
			hxLineService.update(hwTravelLine);
			RedisCache.del(HwPrefix.LinePrefix+hwTravelLine.getLine_id(),3);
			//删除首页缓存
			RedisCache.delRegex(HwPrefix.APPIndexPrefix,2);
			return new ResultEntity("200","修改成功");
		
	}
	/**
	 * 修改线路 返回
	 * @return
	 */
	@ApiOperation(value = "查看线路详情" , notes = "查看线路详情", response = HwTravelLineDetailsVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scenic", value = "输入的内容", dataType = "string",required = true,paramType = "query"),
    })
	@ValidateParam(field={"line_id"}, message={"Id不能为空"})
	@PostMapping("bureau/line/update_query")
	public ResultEntity update_query(HwTravelLineUpdateVo hwTravelLine,@RequestParam(defaultValue="1")Integer type){
		Map<String, Object> map = hxLineService.queryByLineIdForUpdate(hwTravelLine.getLine_id());
		return new ResultEntity(map);
		
	}
	
	
	/**
	 * 通过id下架
	 * 
	 * @param id
	 * @param type
	 *            0-下架  1-上架
	 * @return
	 */
	@Log(logType = LogType.OPERATION, dec = "上架成功")
	@ValidateParam(field = { "id" }, message = { "id不能为空" })
	@PostMapping("bureau/line/delete")
	public ResultEntity delete(Long id) {
		hxLineService.deleteById(id, 0);
		return new ResultEntity("200","上架成功");
	}
	
	
	/**
	 * 通过id上架
	 * @param id
	 * @return
	 */
	@Log(logType = LogType.OPERATION, dec = "下架成功")
	@ValidateParam(field = { "id" }, message = { "id不能为空" })
	@PostMapping("bureau/line/recovery")
	public ResultEntity recovery(Long id) {
		hxLineService.deleteById(id, 1);
		return new ResultEntity("200","下架成功");
	}
	
	
	/**
	 * 获取线路的属性信息
	 */
	@RequestMapping("bureau/line/qureyAttribute")
	public ResultEntity qureyAttribute() {
		Map<String, Object> map = hxLineService.qureyAttribute();
		return new ResultEntity(map);
	}
	
	/**
	 * 获取景点信息
	 */
	@ApiOperation(value = "获取景点信息" , notes = "获取景点信息", response = ESQuery.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scenic", value = "输入的内容", dataType = "string",required = true,paramType = "query"),
    })
	@ValidateParam(field = { "scenic" }, message = { "scenic不能为空" })
	@PostMapping("bureau/line/scenic_spot")
	public ResultEntity scenic_spot(String scenic) {
		List<Map<String, Object>> list = hxLineService.qurey_scenic_spot(scenic);
		return new ResultEntity(list);
	}
	
}
