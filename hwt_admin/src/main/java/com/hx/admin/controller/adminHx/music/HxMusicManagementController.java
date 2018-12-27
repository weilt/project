package com.hx.admin.controller.adminHx.music;

import com.alibaba.fastjson.JSONObject;
import com.hwt.domain.entity.user.video.vo.HxMusicVo;
import com.hwt.domain.entity.user.video.vo.HxMusicVos;
import com.hx.admin.base.ResultEntity;
import com.hx.adminHx.service.music.HxMusicManagementService;
import com.hx.adminHx.service.vo.HxUserVideoPage;
import com.hx.core.exception.BaseException;
import com.hx.core.page.asyn.PageResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

@RestController
@Api(value = "API - HxMusicManagementController", description = "音乐库管理")
public class HxMusicManagementController {

    @Autowired
    HxMusicManagementService hxMusicManagementService;

    /**
     *音乐库
     *
     * @return
     */
    @RequestMapping("admin/music/list")
    public ModelAndView refundList() {
        return new ModelAndView("adminHx/music/music-list");
    }
    /**
     * 查询小视频
     *
     * @param pageSize  分页大小
     * @param startNum  当前哪一条
     * @param orderBy   排序  0-默认升序 1-降序
     * @param is_open   是否公开 0-公开 1-隐藏
     * @param music_id  id
     * @param user_id  上传人id  如果不为 null 就是模糊搜索所有
     * @param music_title  音乐名称 0-无音乐名称 1-有音乐名称             如果不为 0-1 null 就是模糊搜索所有
     * @param music_title2  音乐名称  如果不为  null 就是模糊搜索所有
     * @param music_cover  音乐封面 0-无封面图 1-有封面图
     * @param music_tag  音乐分类0-全部 1-影视原声,2-日韩,3-生活,4-搞怪,5-流行,6-说唱,7-民谣,8-欧美
     * @param music_writer 音乐作者 歌手名称 0-无歌手名称 1-有歌手名称            如果不为 0-1 null 就是模糊搜索所有
     * @param music_writer2 歌手名称     如果不为null 就是模糊搜索所有
     * @param song 搜索 1-歌手名搜索 2-歌曲名搜索  3-用户ID搜索
     * @return
     */
    @ApiOperation(value = "查询小视频", notes = "查询小视频" , response = ResultEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小 默认10", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "startNum", value = "重第几条开始查询", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "orderBy", value = "排序  0-默认升序 1-降序", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "is_open", value = " 是否公开 0-公开 1-隐藏", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "music_id", value = "音乐id", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "music_title", value = "音乐名称 0-无音乐名称 1-有音乐名称", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "music_title2", value = "音乐名称  如果不为  null 就是模糊搜索所有", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "music_cover", value = "音乐封面 0-无封面图 1-有封面图", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "music_tag", value = "音乐分类0-全部 1-影视原声,2-日韩,3-生活,4-搞怪,5-流行,6-说唱,7-民谣,8-欧美", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "song", value = "搜索 1-歌手名搜索 2-歌曲名搜索  3-用户ID搜索", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "music_writer", value = "音乐作者 歌手名称 0-无歌手名称 1-有歌手名称 ", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "music_writer2", value = "歌手名称     如果不为null 就是模糊搜索所有", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "user_id", value = "上传人id  如果不为 null 就是模糊搜索所有", dataType = "int",paramType = "query")
    })
    @RequestMapping("admin/music/query")
    public ResultEntity musicQuery(@RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "0") Integer startNum,
                              @RequestParam(defaultValue = "0",name = "paramMap[use_account]") String orderBy,
                              @RequestParam(defaultValue = "0",name = "paramMap[is_open]") Integer is_open,
                              @RequestParam(required = false,name = "paramMap[music_id]") Long music_id,
                              @RequestParam(defaultValue = "1" ,required = false,name = "paramMap[music_title]") String music_title,
                              @RequestParam(required = false,name = "paramMap[music_title2]") String music_title2,
                              @RequestParam(defaultValue = "1" ,required = false,name = "paramMap[music_cover]") String music_cover,
                              @RequestParam(defaultValue = "0",name = "paramMap[music_tag]")String music_tag,
                              @RequestParam(defaultValue = "0",name = "paramMap[song]")Integer song,
                              @RequestParam(defaultValue = "1" ,required = false,name = "paramMap[music_writer]")String music_writer,
                              @RequestParam(required = false,name = "paramMap[music_writer2]")String music_writer2,
                              @RequestParam(required = false,name = "paramMap[user_id]")Long user_id){
        if ("0".equals(orderBy)){
            orderBy="use_account ASC";
        }
        else if ("1".equals(orderBy)){
            orderBy="use_account desc";
        }
        else {
            throw new BaseException("排序异常请联系管理员");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageSize", pageSize);
        map.put("startNum", startNum);
        map.put("orderBy", orderBy);
        map.put("is_open", is_open);
        map.put("song", song);
        map.put("music_id", music_id);
        map.put("music_cover", music_cover);
        map.put("music_tag", music_tag);
        map.put("music_title", music_title);
        map.put("music_title2", music_title2);
        map.put("user_id", user_id);
        map.put("music_writer", music_writer);
        map.put("music_writer2", music_writer2);
        PageResult pageResult = hxMusicManagementService.selectQuery(map);
        return new ResultEntity(pageResult);
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除", notes = "批量删除" , response = ResultEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "music_ids", value = "所选的id逗号拼接成的字符串",
                    dataType = "string",required = true,paramType = "query")
    })
    @ApiResponses({@ApiResponse(code=200,message="")})
    @RequestMapping("admin/music/delete")
    public ResultEntity musicDelete(@RequestParam(name="music_ids",required = false)String music_ids) {

        String[] ids = music_ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            if (!"".equals(id)) {
                list.add(Integer.parseInt(id));
            }
        }
        if (list == null || list.size() == 0){
            return new ResultEntity("200","没有选中音频编号");
        }
        boolean  yesOrNo =hxMusicManagementService.musicDelete(list);
            if (!yesOrNo){
                throw new BaseException("操作异常请联系管理员");
            }
        return new ResultEntity("200","操作成功");

    }

    /**
     * 查询多条数据的详情
     * @return
     */
    @RequestMapping("admin/music/selects")
    public ResultEntity selects(@RequestParam("ids[]") Integer[] ids){
        List<HxMusicVo> hxMusicVos= hxMusicManagementService.batchSelectDetails(ids);
        return new ResultEntity(hxMusicVos);
    }

    /**
     * 批量修改音频
     * @param
     * @return
     */
//    @ApiOperation(value = "批量修改音频", notes = "批量修改音频" , response = ResultEntity.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "model", value = "实体里面包一个数组对象", dataType = "string",required = true,paramType = "query")
//    })
//    @ApiResponses({@ApiResponse(code=200,message="")})
    @RequestMapping("admin/music/update")

    public ResultEntity musicUpdate(@RequestParam("ds[]") String[] ds){
        List<HxMusicVo> list = new ArrayList<>();
        for (String d : ds) {
           HxMusicVo hxMusicVo = JSONObject.parseObject(d,HxMusicVo.class);

               list.add(hxMusicVo);

        }
        System.out.println(list);
        hxMusicManagementService.updateByMusic(list);
        return new ResultEntity("200","操作成功");
    }

    /**
     * 修改-隐藏-删除 功能
     * @param mhd 1-修改 2-隐藏 3-删除 4-查询单条数据
     * @param music_id 音乐id
     * @param music_tag 音乐分类
     * @param music_writer 音乐作者
     * @param music_cover 音乐封面
     * @param music_title  音乐名称
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改-隐藏-删除 功能", notes = "修改-隐藏-删除 功能" , response = ResultEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "music_id", value = "音乐id", dataType = "int",required = true,paramType = "query"),
            @ApiImplicitParam(name = "music_tag", value = "音乐分类", dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "music_writer", value = "音乐作者", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "music_cover", value = "音乐封面图", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "music_title", value = "音乐名称", dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "mhd", value = "1-修改 2-隐藏 3-删除 4-查询单条数据", dataType = "int",required = true,paramType = "query")
    })
    @RequestMapping("admin/music/modifyHideDelete")
    public ResultEntity modifyHideDelete(@RequestParam Integer mhd,
                                   @RequestParam Long music_id,
                                   @RequestParam(required = false,name = "radios")Integer music_tag,
                                   @RequestParam(required = false,name = "geshouname")String music_writer,
                                   @RequestParam(required = false,name = "url")String music_cover,
                                   @RequestParam(required = false,name = "name")String music_title) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mhd", mhd);
        map.put("music_id", music_id);
        map.put("music_writer", music_writer);
        map.put("music_title", music_title);
        map.put("music_tag", music_tag);
        map.put("music_cover", music_cover);
        HxMusicVo yesOrNo = hxMusicManagementService.selectModifyHideDelete(map);
        if (Integer.parseInt(map.get("mhd").toString()) == 4){
            return new ResultEntity(yesOrNo);
        }else {
            if(yesOrNo == null){
                return new ResultEntity("200","成功");
            }else {
                return new ResultEntity("500","操作失败");
            }
        }

    }

    /**
     * 类部类 不要删除
     */
    @ApiModel(value="类部类")
    class Model{
        @ApiModelProperty(value="音乐库")
        private List<HxMusicVo> hxMusicVos = new ArrayList<>();

        public List<HxMusicVo> getHxMusicVos() {
            return hxMusicVos;
        }

        public void setHxMusicVos(List<HxMusicVo> hxMusicVos) {
            this.hxMusicVos = hxMusicVos;
        }
    }
}
