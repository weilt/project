package com.hx.api.controller.user.video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hwt.domain.entity.user.video.vo.*;
import jdk.nashorn.internal.runtime.Undefined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoComment;
import com.hx.api.base.ResultEntity;
import com.hx.api.validate.ValidateParam;
import com.hx.api.validate.ValidateParam.CheckedType;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.ZZLocationUtils;
import com.hx.user.utils.BaseUtil;
import com.hx.user.video.service.VideoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "API - VideoController", description = "小视屏操作接口")
@RestController
public class VideoController {

	@Autowired
	private VideoService videoService;
	
	/**
	 * 发布小视频
	 * @param token - 验证登录状态 - Y
	 * @return
	 */
	@ApiOperation(value = "发布小视频", notes = "发布小视频" , response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "attribute_id", value = "分类  0-默认", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "dec", value = "描述", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "image", value = "封面图", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容url", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "music_url", value = "音乐的url", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "music_id", value = "音乐id", dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "music_cover", value = "音乐封面",dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "music_time", value = "音乐时长(时间戳Long)",dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "music_writer", value = "音乐作者",dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "is_open", value = "是否公开 1-是 0-否   默认1", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "longitude", value = "经度", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "latitude", value = "纬度", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "location", value = "地址", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "name_id", value = "关联的id 0为没有关联", dataType = "string",paramType = "query"),
			@ApiImplicitParam(name = "name_type", value = "关联的类型   1-景点 2-导游  3- 线路 4-资讯 0为没有关联", dataType = "string",paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","attribute_id","dec","content","image"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/insert",method = RequestMethod.POST)
	public ResultEntity accountInfo(String token,Long attribute_id,String dec,String image, String content,
			@RequestParam(defaultValue="1")Integer is_open, 
				String longitude, String latitude,String location,Long name_id,Integer name_type,
									@RequestParam(required = false) Long music_id,
									@RequestParam(required = false) String music_url,
									@RequestParam(required = false) String music_cover,
									@RequestParam(required = false) Long music_time,
									@RequestParam(required = false) String music_writer)  throws Exception{
		 LoginVo loginVo = BaseUtil.getLoginVo(token);
			Long user_id = loginVo.getUser_id();
		 HxUserVideo hxUserVideo = new HxUserVideo();
		//根据经纬度 获取城市，区号
		if (ObjectHelper.isEmpty(longitude)||ObjectHelper.isEmpty(latitude)){
			
		}else{
			Map<String, String> map = ZZLocationUtils.get_province_city_district_by_gaode_log_lat(longitude,latitude);
			
			String area_code = map.get("area_code");
			String city = map.get("city");
			hxUserVideo.setArea_code(area_code);
			hxUserVideo.setCity(city);
		}
		Long create_time = System.currentTimeMillis();
		HxMusicVo hxMusicVo = new HxMusicVo();

		if (music_id != null){
			hxMusicVo.setMusic_id(music_id);
			hxMusicVo.setCreate_time(create_time);
			hxMusicVo.setUse_account(1l);

		}else {
			hxMusicVo.setMusic_url(music_url);
			hxMusicVo.setCreate_time(create_time);
			hxMusicVo.setUse_account(1l);
			hxMusicVo.setUser_id(user_id);
			hxMusicVo.setMusic_title(dec);
			hxMusicVo.setMusic_writer(music_writer);
			hxMusicVo.setMusic_cover(music_cover);
			hxMusicVo.setMusic_time(music_time);
		}



		hxUserVideo.setLocation(location);
		hxUserVideo.setAttribute_id(attribute_id);
		hxUserVideo.setMusic_id(music_id);

		hxUserVideo.setContent(content);
		hxUserVideo.setCreate_time(create_time);
		hxUserVideo.setDec(dec);
		hxUserVideo.setIs_open(is_open);
		hxUserVideo.setLatitude(latitude);
		hxUserVideo.setLongitude(longitude);
		hxUserVideo.setUser_id(loginVo.getUser_id());
		hxUserVideo.setImage(image);
		hxUserVideo.setName_id(name_id);
		hxUserVideo.setName_type(name_type);
		videoService.insert(loginVo,hxUserVideo,hxMusicVo);


		return new ResultEntity();
	}
	/**
	 * 用户查询后台提供的音频列表
	 */
	@ApiOperation(value = "用户查询后台提供的音频列表", notes = "用户查询后台提供的音频列表" , response = HxMusicVo.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页大小 默认20", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "当前页码默认为1", dataType = "string", paramType = "query")
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/music_query",method = RequestMethod.POST)
	public ResultEntity music_query(String token,
									@RequestParam(defaultValue="20") Integer pageSize,
									@RequestParam(defaultValue = "1")Integer pageNum) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("pageSize", pageSize);
//		map.put("user_id", loginVo.getUser_id());
		Integer page =(pageNum-1)*pageSize;

		List<HxMusicVo> list = videoService.music_query(pageSize,page);
		return new ResultEntity(list);
	}


	/**
	 * 小视频详情
	 */
	@ApiOperation(value = "小视频详情", notes = "小视频详情" , response = HxMusicVo.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "music_id", value = "音乐的id", dataType = "Integer",required = true,paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/music_shinee",method = RequestMethod.POST)
	public ResultEntity music_shinee(String token,
//									 @RequestParam(defaultValue="20") Integer pageSize,
//									 @RequestParam(defaultValue="1") Integer status,
									 @RequestParam(required = true) Long music_id) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("pageSize", pageSize);
//		map.put("status", status);
		map.put("music_id", music_id);
//		map.put("user_id", loginVo.getUser_id());
		HxMusicVo Music = videoService.music_shinee(map);
		return new ResultEntity(Music);
	}
	/**
	 * 同款小视频
	 */
//	@ApiOperation(value = "同款小视频", notes = "同款小视频" , response = HxVideoVo.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
//			@ApiImplicitParam(name = "music_id", value = "音乐的id", dataType = "Integer",required = true,paramType = "query"),
//			@ApiImplicitParam(name = "pageSize", value = "每页大小 默认20", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "pageNum", value = "当前页码默认为1", dataType = "string", paramType = "query")
//	})
//	@ApiResponses({@ApiResponse(code=200,message="")})
//	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
//	@RequestMapping(value = "video/music_video",method = RequestMethod.POST)
//	public ResultEntity music_video(String token,
//									 @RequestParam(required = true) Integer music_id,
//									@RequestParam(defaultValue = "1")Integer pageNum,
//									@RequestParam(defaultValue = "20")Integer pageSize) {
//		LoginVo loginVo = BaseUtil.getLoginVo(token);
//		Map<String, Object> map = new HashMap<String, Object>();
//		//从第几条开始查
//		Integer page =(pageNum-1)*pageSize;
//		map.put("music_id", music_id);
//		map.put("pageNum", page);
//		map.put("pageSize", pageSize);
//		List<HxVideoVo> list = videoService.music_video(map);
//		return new ResultEntity(list);
//	}

	/**
	 * 删除小视频
	 */
	@ApiOperation(value = "删除小视频", notes = "删除小视频" , response = ResultEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
			@ApiImplicitParam(name = "video_id", value = "小视频id ", dataType = "string",required = true, paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/delete",method = RequestMethod.POST)
	public ResultEntity delete(String token,Long video_id){
		 LoginVo loginVo = BaseUtil.getLoginVo(token);
		videoService.delete(loginVo.getUser_id(), video_id);
		return new ResultEntity();
	}
	
	/**
	 * 看客的身份查询小视频
	 */
	@ApiOperation(value = "看客的身份查询小视频", notes = "看客的身份查询小视频" , response = HxVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "field", value = "搜索字段", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "area_code", value = "区号", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "video_user_id", value = "小视频发布人id  0为查看所有人的  默认0", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "last_video_id", value = "查询的最后一条小视频id  默认0", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string", paramType = "query")
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/onlooker_query",method = RequestMethod.POST)
	public ResultEntity onlooker_query(String token,@RequestParam(defaultValue="0") Long video_user_id ,
			@RequestParam(defaultValue="0")Long last_video_id,String field,String area_code
			,@RequestParam(defaultValue="10") Integer pageSize) {
		 LoginVo loginVo = BaseUtil.getLoginVo(token);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("video_user_id", video_user_id);
		map.put("last_video_id", last_video_id);
		map.put("pageSize", pageSize);
		map.put("field", field);
		map.put("area_code", area_code);
		map.put("user_id", loginVo.getUser_id());
		List<HxVideoVo> list = videoService.onlooker_query(map);
		return new ResultEntity(list);
	}
	
	/**
	 * 查看自己的小视频
	 */
	@ApiOperation(value = "查看自己的小视频", notes = "查看自己的小视频" , response = HxUserVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "last_video_id", value = "查询的最后一条小视频id  默认0", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string",required = true,paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/own_query",method = RequestMethod.POST)
	public ResultEntity own_query(String token,@RequestParam(defaultValue="0") Integer last_video_id 
			,@RequestParam(defaultValue="10") Integer pageSize) {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		List<HxUserVideo> list = videoService.own_query(loginVo.getUser_id(),last_video_id,pageSize);
		return new ResultEntity(list);
	}
	
	/**
	 * 添加喜欢
	 */
	@ApiOperation(value = "添加喜欢", notes = "添加喜欢" , response = ResultEntity.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_id", value = "小视频id ", dataType = "string",required = true, paramType = "query"),
		@ApiImplicitParam(name = "received_im_id", value = "收消息方", dataType = "string",paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_id","received_im_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/add_like",method = RequestMethod.POST)
	public ResultEntity add_like(String token, Long video_id,String received_im_id) throws Exception  {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		videoService.add_like(loginVo,video_id,received_im_id);
		return new ResultEntity();
	}
	
	/**
	 * 删除喜欢
	 */
	@ApiOperation(value = "删除喜欢", notes = "删除喜欢" , response = ResultEntity.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_id", value = "小视频id ", dataType = "string",required = true, paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/delete_like",method = RequestMethod.POST)
	public ResultEntity delete_like(String token, Long video_id) {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		videoService.delete_like(loginVo.getUser_id(),video_id);
		return new ResultEntity();
	}
	
	/**
	 * 查询喜欢
	 */
	@ApiOperation(value = "查询喜欢", notes = "查询喜欢" , response = HxUserVideoLikeListVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "last_like_time", value = "查询的最后一条喜欢时间   0为第一页查询", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string",required = true,paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/like_query",method = RequestMethod.POST)
	public ResultEntity like_query(String token,@RequestParam(defaultValue="10") Integer pageSize 
			,@RequestParam(defaultValue="10") Long last_like_time) {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", loginVo.getUser_id());
		map.put("last_like_time", last_like_time);
		map.put("pageSize", pageSize);
		List<HxUserVideoLikeListVo> list = videoService.like_query(map);
		return new ResultEntity(list);
	}
	
	
	/**
	 * 添加关注
	 */
	@ApiOperation(value = "添加关注", notes = "添加关注" , response = ResultEntity.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "follow_user_id", value = "被关注人的id", dataType = "string",required = true, paramType = "query"),
		
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","follow_user_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/add_follow",method = RequestMethod.POST)
	public ResultEntity add_follow(String token, Long follow_user_id) {
		
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		videoService.add_follow(loginVo.getUser_id(),follow_user_id);
		return new ResultEntity();
	}
	
	/**
	 * 删除关注
	 */
	@ApiOperation(value = "删除关注", notes = "删除关注" , response = ResultEntity.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "follow_user_id", value = "被关注人的id", dataType = "string",required = true, paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","follow_user_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/delete_follow",method = RequestMethod.POST)
	public ResultEntity delete_follow(String token, Long follow_user_id) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		videoService.delete_follow(loginVo.getUser_id(),follow_user_id);
		return new ResultEntity();
	}
	
	/**
	 * 查询关注列表
	 */
	@ApiOperation(value = "查询关注列表", notes = "查询关注列表" , response = VideoFollowVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "last_follow_time", value = "查询的最后一条关注时间   0为第一页查询", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string",required = true,paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/query_follow",method = RequestMethod.POST)
	public ResultEntity query_follow(String token,Long last_follow_time, Integer pageSize) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", loginVo.getUser_id());
		map.put("last_follow_time", last_follow_time);
		map.put("pageSize", pageSize);
		List<VideoFollowVo> list = videoService.query_follow(map);
		return new ResultEntity(list);
	}
	
	/**
	 * 评论
	 */
	@ApiOperation(value = "评论", notes = "评论" , response = HxUserVideoComment.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_id", value = "小视频   id", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "content", value = "评论内容", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "type", value = "1-评论 2-点赞   默认1", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "parent_id", value = "回复的哪一条  0-不是回复  默认0", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "parent_user_id", value = "回复的谁  0-不是回复 默认0", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "received_im_id", value = "收消息方", dataType = "string",paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_id","received_im_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/add_comment",method = RequestMethod.POST)
	public ResultEntity add_comment(String token, Long video_id,String content, 
			Integer type, Long parent_id, Long parent_user_id,String received_im_id) throws Exception {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		HxUserVideoComment comment = videoService.comment(loginVo,video_id,content,type,parent_id,parent_user_id,received_im_id);
		return new ResultEntity(comment);
	}
	
	/**
	 * 删除评论
	 */
	@ApiOperation(value = "删除评论", notes = "删除评论" , response = ResultEntity.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_comment_id", value = "评论id", dataType = "string",required = true, paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_comment_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/delete_comment",method = RequestMethod.POST)
	public ResultEntity delete_comment(String token, Long video_comment_id) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		videoService.delete_comment(loginVo.getUser_id(),video_comment_id);
		return new ResultEntity();
	}
	
	/**
	 * 查询评论
	 */
	@ApiOperation(value = "查询评论", notes = "查询评论" , response = HxUserVideoCommentVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_id", value = "小视频id", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "last_video_comment_id", value = "查询的最后一条评论id  默认0", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string", paramType = "query")
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/query_comment",method = RequestMethod.POST)
	public ResultEntity query_comment(@RequestParam(defaultValue="0") Long video_id ,Long last_video_comment_id ,
			@RequestParam(defaultValue="10") Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("video_id", video_id);
		map.put("last_video_comment_id", last_video_comment_id);
		map.put("pageSize", pageSize);
		List<HxUserVideoCommentVo> list = videoService.query_comment(map);
		return new ResultEntity(list);
	}
	
	/**
	 * 小视频个人首页数据返回
	 */
	@ApiOperation(value = "小视频个人首页数据返回", notes = "小视频个人首页数据返回" , response = HxUserVideoBasicNumShow.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
	
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/query_own_basicNum",method = RequestMethod.POST)
	public ResultEntity query_own_basicNum(String token) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		HxUserVideoBasicNumShow basicNumShow = videoService.query_own_basicNum(loginVo.getUser_id());
		return new ResultEntity(basicNumShow);
	}
	
	
	/**
	 * 小视频他人首页数据返回
	 */
	@ApiOperation(value = "小视频他人首页数据返回", notes = "小视频他人首页数据返回" , response = HxUserVideoOtherBasicNumShow.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "other_user_id", value = "用户id", dataType = "string",required = true,paramType = "query"),
	
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","other_user_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/query_other_basicNum",method = RequestMethod.POST)
	public ResultEntity query_other_basicNum(String token ,Long other_user_id) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		if (loginVo.getUser_id().equals(other_user_id)){
			throw new RuntimeException("接口调错！");
		}
		HxUserVideoOtherBasicNumShow basicNumShow = videoService.query_other_basicNum(loginVo.getUser_id(),other_user_id);
		return new ResultEntity(basicNumShow);
	}
	
	/**
	 * 根据关联id查询小视频
	 */
	@ApiOperation(value = "根据关联id查询小视频", notes = "根据关联id查询小视频" , response = HxVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "name_id", value = "关联的id", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "name_type", value = "关联的类型   1-景点 2-导游  3- 线路 4-资讯   默认1", dataType = "string",paramType = "query"),
		@ApiImplicitParam(name = "last_video_id", value = "查询的最后一条小视频id  默认0", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string", paramType = "query")
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"name_id"})
	@RequestMapping(value = "video/name_query",method = RequestMethod.POST)
	public ResultEntity name_query(String token,Long name_id ,@RequestParam(defaultValue="1")Integer name_type
			,@RequestParam(defaultValue="0")Long last_video_id
			,@RequestParam(defaultValue="10") Integer pageSize) {
		Long user_id =null;
		if (ObjectHelper.isEmpty(token)){
			LoginVo loginVo = BaseUtil.getLoginVo(token);
			user_id = loginVo.getUser_id();
		}
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name_id", name_id);
		map.put("name_type", name_type);
		map.put("last_video_id", last_video_id);
		map.put("pageSize", pageSize);
		map.put("user_id", user_id);
		List<HxVideoVo> list = videoService.name_query(map);
		return new ResultEntity(list);
	}
	
	/**
	 * 根据小视频id查询
	 */
	@ApiOperation(value = "根据小视频id查询", notes = "根据小视频id查询" , response = HxVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_id", value = "小视频id", dataType = "string",paramType = "query"),
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token","video_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/query_by_id",method = RequestMethod.POST)
	public ResultEntity query_by_id(String token,Long video_id) {
		 LoginVo loginVo = BaseUtil.getLoginVo(token);
		
		HxVideoVo hxVideoVo = videoService.query_by_id(loginVo.getUser_id(),video_id);
		return new ResultEntity(hxVideoVo);
	}
	
	/**
	 * 查询关注人列表的小视频
	 */
	@ApiOperation(value = "查询关注人列表的小视频", notes = "查询关注人列表的小视频" , response = HxVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "last_video_id", value = "查询的最后一条小视频id  默认0", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页大小 默认10", dataType = "string", paramType = "query")
	})
	@ApiResponses({@ApiResponse(code=200,message="")})
	@ValidateParam(field={"token"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/query_follow_video",method = RequestMethod.POST)
	public ResultEntity query_follow_video(String token,
			@RequestParam(defaultValue="0")Long last_video_id
			,@RequestParam(defaultValue="10") Integer pageSize) {
		 LoginVo loginVo = BaseUtil.getLoginVo(token);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("last_video_id", last_video_id);
		map.put("pageSize", pageSize);
		map.put("user_id", loginVo.getUser_id());
		List<HxVideoVo> list = videoService.query_follow_video(map);
		return new ResultEntity(list);
	}
	
	
	/**
	 * 从消息界面过去查看小视频
	 */
	@ApiOperation(value = "从消息界面过去查看小视频", notes = "从消息界面过去查看小视频" , response = HxVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "video_id", value = "小视频id", dataType = "string", paramType = "query"),
	})
	@ValidateParam(field={"token","video_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/msg_like_video",method = RequestMethod.POST)
	public ResultEntity msg_like_video(String token,Long video_id) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		HxVideoVo hxVideoVo = videoService.msg_like_video(loginVo.getUser_id(),video_id);
		return new ResultEntity(hxVideoVo);
	}
	
	/**
	 * 从消息界面评论消息过去查看小视频
	 */
	@ApiOperation(value = "从消息界面评论过去查看小视频", notes = "从消息界面评论过去查看小视频" , response = MsgCommentVideoVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "用户验证TOKEN - Y", dataType = "string",required = true,paramType = "query"),
		@ApiImplicitParam(name = "comment_id", value = "评论id", dataType = "string", paramType = "query"),
	})
	@ValidateParam(field={"token","comment_id"},checkedType={CheckedType.TOKEN})
	@RequestMapping(value = "video/msg_comment_video",method = RequestMethod.POST)
	public ResultEntity msg_comment_video(String token,Long comment_id) {
		LoginVo loginVo = BaseUtil.getLoginVo(token);
		MsgCommentVideoVo hxUserVideoVo = videoService.msg_comment_video(loginVo.getUser_id(),comment_id);
		return new ResultEntity(hxUserVideoVo);
	}
}
