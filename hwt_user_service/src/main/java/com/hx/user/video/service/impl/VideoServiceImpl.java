
package com.hx.user.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hwt.domain.entity.user.video.vo.*;
import com.hwt.domain.mapper.user.music.HxUserMusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwt.domain.entity.user.HxUserInfo;
import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.video.HxUserVideo;
import com.hwt.domain.entity.user.video.HxUserVideoComment;
import com.hwt.domain.entity.user.video.HxUserVideoFollow;
import com.hwt.domain.entity.user.video.HxUserVideoLike;
import com.hwt.domain.mapper.user.HxUserInfoMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoCommentMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoFollowMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoLikeMapper;
import com.hwt.domain.mapper.user.video.HxUserVideoMapper;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.utils.ZZLocationUtils;
import com.hx.core.wyim.notice.SentNotice;
import com.hx.user.video.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	private HxUserVideoCommentMapper hxUserVideoCommentMapper;

	@Autowired
	private HxUserMusicMapper hxUserMusicMapper;
	
	@Autowired
	private HxUserVideoFollowMapper hxUserVideoFollowMapper;
	
	@Autowired
	private HxUserVideoLikeMapper hxUserVideoLikeMapper;
	
	@Autowired
	private HxUserVideoMapper hxUserVideoMapper;
	
	@Autowired
	private SentNotice sentNotice;
	
	@Autowired
	private HxUserInfoMapper hxUserInfoMapper;

	@Override
	@Transactional
	public void insert(LoginVo loginVo,HxUserVideo hxUserVideo,HxMusicVo hxMusicVo) throws Exception {

		hxUserVideoMapper.insertSelectiveBcakId(hxUserVideo);
		if ("1".equals(hxUserVideo.getIs_open().toString())){
			List<String> list = hxUserVideoFollowMapper.queryImIdsFollowByUserID(loginVo.getUser_id());
			
			HxUserInfo selectByUserId = hxUserInfoMapper.selectByUserId(loginVo.getUser_id());
			
			if (ObjectHelper.isNotEmpty(list)){
				for (String im_id : list) {
					sentNotice.sendVideo(selectByUserId.getNickname(),selectByUserId.getUser_icon(), 
							im_id, "发小视频消息", selectByUserId.getNickname()+"发了小视频", 1, hxUserVideo.getVideo_id(), null);
				}
				
			}
		}

		if (hxMusicVo.getMusic_id() == null){
			//插入音频
			hxUserMusicMapper.insertByMusics(hxMusicVo);

			hxUserVideoMapper.UpdateByMusicId(hxMusicVo.getMusic_id(),hxUserVideo.getVideo_id());
		}else {
//			Long musics = Long.parseLong(music.get("music_id").toString());
			Integer yesorno = hxUserVideoMapper.selectMusic(hxMusicVo.getMusic_id());
			if (yesorno != 0){
				//修改音频引用次数
				hxUserMusicMapper.updateByUse_account(hxMusicVo.getMusic_id(),1);
			}else {
				throw new RuntimeException("音频不存在,请联系管理员");
			}

		}
		
	}

	@Override
	public List<HxVideoVo> onlooker_query(Map<String, Object> map) {
		List<HxVideoVo> list = hxUserVideoMapper.onlooker_query(map);
		//查询Music_id对应的音乐
		for (int i = 0; i < list.size(); i++) {
			HxMusicVo hxMusicVo =hxUserVideoMapper.selectMusicID(list.get(i).getMusic_id());
			list.get(i).setHxMusicVo(hxMusicVo);
		}
		return list;
	}

	@Override
	public List<HxUserVideo> own_query(Long user_id, Integer last_video_id, Integer pageSize) {
		if (last_video_id==null||last_video_id<=0){
			return hxUserVideoMapper.own_query_1(user_id, pageSize);
		}
		List<HxUserVideo> list = hxUserVideoMapper.own_query(user_id,last_video_id,pageSize);
		return list;
	}

	@Override
	@Transactional
	public void add_like(LoginVo loginVo, Long video_id, String received_im_id) throws Exception {
		Long user_id = loginVo.getUser_id();
		HxUserVideoLike hxUserVideoLike = new HxUserVideoLike();
		hxUserVideoLike.setCreate_time(System.currentTimeMillis());
		hxUserVideoLike.setUser_id(user_id);
		hxUserVideoLike.setVideo_id(video_id);
		hxUserVideoLikeMapper.insert(hxUserVideoLike);
		hxUserVideoMapper.likeAdd1(video_id);
		
		if (!loginVo.getIm_id().equals(received_im_id)){
			
			HxUserInfo selectByUserId = hxUserInfoMapper.selectByUserId(user_id);
			sentNotice.sendVideo(selectByUserId.getNickname(),selectByUserId.getUser_icon(), 
					received_im_id,selectByUserId.getNickname()+"赞了你" , selectByUserId.getNickname()+"赞了你",2,
					video_id, null);
			
//			sentNotice.sendVideo(selectByUserId.getNickname(),selectByUserId.getUser_icon(), 
//					im_id, "发小视频消息", selectByUserId.getNickname()+"发了小视频", 1, hxUserVideo.getVideo_id(), null);
		}
		
		
	}

	@Transactional
	@Override
	public void delete_like(Long user_id, Long video_id) {
		hxUserVideoLikeMapper.delete_like(user_id,video_id);
		hxUserVideoMapper.likeReduce1(video_id);
		
	}

	@Override
	public List<HxUserVideoLikeListVo> like_query(Map<String, Object> map) {
		List<HxUserVideoLikeListVo> list = hxUserVideoLikeMapper.like_query(map);
		//查询Music_id对应的音乐
		for (int i = 0; i < list.size(); i++) {
			HxMusicVo hxMusicVo =hxUserVideoMapper.selectMusicID(list.get(i).getMusic_id());
			list.get(i).setHxMusicVo(hxMusicVo);
		}
		return list;
	}

	@Override
	@Transactional
	public void delete(Long user_id, Long video_id) {
		HxUserVideo selectByPrimaryKey = hxUserVideoMapper.selectByPrimaryKey(video_id);
		if (!selectByPrimaryKey.getUser_id().equals(user_id)){
			throw new RuntimeException("非本人不能操作");
		}
		selectByPrimaryKey.setIs_hide(1);
		Long musicid = selectByPrimaryKey.getMusic_id();
        if (musicid != null){
            //查询引用次数
           Integer use_account = hxUserMusicMapper.selectMusicId(musicid);
            if (use_account < 2){
                //假删除操作
                hxUserMusicMapper.updateByDelete(musicid);
            }else {
                //减去引用次数 1次
                hxUserMusicMapper.updateOrUse_account(musicid,1);
            }
        }
		hxUserVideoMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
	}


	@Transactional
	@Override
	public void add_follow(Long user_id, Long follow_user_id) {
		HxUserVideoFollow hxUserVideoFollow = new HxUserVideoFollow();
		hxUserVideoFollow.setCreate_time(System.currentTimeMillis());
		hxUserVideoFollow.setFollow_user_id(follow_user_id);
		hxUserVideoFollow.setUser_id(user_id);
		hxUserVideoFollowMapper.insertSelective(hxUserVideoFollow);
		
	}

	@Override
	@Transactional
	public void delete_follow(Long user_id, Long follow_user_id) {
		hxUserVideoFollowMapper.delete_follow(user_id,follow_user_id);
		
	}

	@Override
	public List<VideoFollowVo> query_follow(Map<String, Object> map) {
		return hxUserVideoFollowMapper.query_follow(map);
	}

	@Override
	@Transactional
	public HxUserVideoComment comment(LoginVo loginVo, Long video_id, String content, Integer type, Long parent_id,
			Long parent_user_id,String received_im_id ) throws Exception {
		Long user_id = loginVo.getUser_id();
		HxUserVideo hxUserVideo = hxUserVideoMapper.selectByPrimaryKey(video_id);
		 if (hxUserVideo==null){
			 throw new RuntimeException("该小视频不存在！");
		 } else {
			 if (hxUserVideo.getIs_hide().equals(1)){
				 throw new RuntimeException("该小视频已删除！");
			 }
			 if(hxUserVideo.getStatus().equals(4)){
				 throw new RuntimeException("该小视频已删除！");
			 }
			 if(hxUserVideo.getStatus().equals(3)){
				 throw new RuntimeException("该小视频审核不通过！");
			 }
		 }
		HxUserVideoComment hxUserVideoComment = new HxUserVideoComment();
		hxUserVideoComment.setContent(content);
		hxUserVideoComment.setCreate_time(System.currentTimeMillis());
		hxUserVideoComment.setParent_id(parent_id);
		hxUserVideoComment.setParent_user_id(parent_user_id);
		hxUserVideoComment.setType(type);
		hxUserVideoComment.setVideo_id(video_id);
		hxUserVideoComment.setUser_id(user_id);
		hxUserVideoComment.setIs_hide(0);
		hxUserVideoCommentMapper.insertBackId(hxUserVideoComment);
		hxUserVideoMapper.commentAdd1(video_id);
		
//		 //回复自己不用发消息
//		 if(parent_user_id.equals(user_id)){
//			 
//			 return hxUserVideoComment;
//		 }
//		 //自己评论的小视频不用发消息
//		 if(parent_user_id==0&&hxUserVideo.getUser_id().equals(user_id)){
//			 
//			 return hxUserVideoComment;
//		 }
		
		//自己操作自己就不用发消息了
		 if (!loginVo.getIm_id().equals(received_im_id)){
			HxUserInfo selectByUserId = hxUserInfoMapper.selectByUserId(user_id);
			
			StringBuffer buffer =  new StringBuffer(selectByUserId.getNickname());
			
			if (type==2){
				 buffer.append("回复了你");
			 } else {
				 buffer.append("评论了你");
			 }
			
			 sentNotice.sendVideo(selectByUserId.getNickname(),selectByUserId.getUser_icon(), 
						received_im_id, buffer.toString(), hxUserVideoComment.getContent(),3,
						hxUserVideoComment.getVideo_comment_id(), null);
		 }
		
		
		return hxUserVideoComment;
	}

	

	@Override
	@Transactional
	public void delete_comment(Long user_id, Long video_comment_id) {
		HxUserVideoComment selectByPrimaryKey = hxUserVideoCommentMapper.selectByPrimaryKey(video_comment_id);
		if (!selectByPrimaryKey.getUser_id().equals(user_id)){
			throw new RuntimeException("非本人不能操作");
		}
		selectByPrimaryKey.setIs_hide(1);
		hxUserVideoCommentMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
		hxUserVideoMapper.commentReduce1(selectByPrimaryKey.getVideo_id());
	}

	@Override
	public List<HxUserVideoCommentVo> query_comment(Map<String, Object> map) {
		return hxUserVideoCommentMapper.query_comment(map);
	}

	@Override
	public HxUserVideoBasicNumShow query_own_basicNum(Long user_id) {
		HxUserVideoBasicNumShow hxUserVideoBasicNumShow = new HxUserVideoBasicNumShow();
		
		//被关注数量
		Long get_total_follow = hxUserVideoFollowMapper.get_total_follow(user_id);
		hxUserVideoBasicNumShow.setGet_total_follow(get_total_follow);
		
		//被赞数量
		Long get_total_good = hxUserVideoLikeMapper.get_total_good(user_id);
		hxUserVideoBasicNumShow.setGet_total_good(get_total_good);
		
		//评论次数
		Long total_comment = hxUserVideoCommentMapper.total_comment(user_id);
		hxUserVideoBasicNumShow.setTotal_comment(total_comment);
		
		//关注数量
		Long total_follow = hxUserVideoFollowMapper.total_follow(user_id);
		hxUserVideoBasicNumShow.setTotal_follow(total_follow);
		
		//喜欢数量
		Long total_good = hxUserVideoLikeMapper.total_good(user_id);
		hxUserVideoBasicNumShow.setTotal_good(total_good);
		
		//自己发的小视频数量
		Long total_my = hxUserVideoMapper.total_my(user_id);
		hxUserVideoBasicNumShow.setTotal_my(total_my);
		
		return hxUserVideoBasicNumShow;
	}

	@Override
	public HxUserVideoOtherBasicNumShow query_other_basicNum(Long user_id,Long other_user_id) {
		HxUserVideoOtherBasicNumShow hxUserVideoOtherBasicNumShow = new HxUserVideoOtherBasicNumShow();
		//被关注数量
		Long get_total_follow = hxUserVideoFollowMapper.get_total_follow(other_user_id);
		hxUserVideoOtherBasicNumShow.setGet_total_follow(get_total_follow);
		
		//被赞数量
		Long get_total_good = hxUserVideoLikeMapper.get_total_good(other_user_id);
		hxUserVideoOtherBasicNumShow.setGet_total_good(get_total_good);
		
		//评论次数
		Long total_comment = hxUserVideoCommentMapper.total_comment(other_user_id);
		hxUserVideoOtherBasicNumShow.setTotal_comment(total_comment);
		
		//查询是否关注
		HxUserVideoFollow selectByUserIdFollowUserId = hxUserVideoFollowMapper.selectByUserIdFollowUserId(user_id,other_user_id);
		if (selectByUserIdFollowUserId!=null){
			hxUserVideoOtherBasicNumShow.setIs_follow(1);
		}else {
			hxUserVideoOtherBasicNumShow.setIs_follow(0);
		}
		return hxUserVideoOtherBasicNumShow;
	}

	@Override
	public List<HxVideoVo> name_query(Map<String, Object> map) {
		
		//不存在用户id
		if (ObjectHelper.isEmpty(map.get("user_id"))){
			
			List<HxVideoVo> list = hxUserVideoMapper.name_query_not_user_id(map);
			//查询Music_id对应的音乐
			for (int i = 0; i < list.size(); i++) {
				HxMusicVo hxMusicVo =hxUserVideoMapper.selectMusicID(list.get(i).getMusic_id());
				list.get(i).setHxMusicVo(hxMusicVo);
			}
			return list;
		} else {
			//存在用户id
			List<HxVideoVo> list = hxUserVideoMapper.name_query(map);
			//查询Music_id对应的音乐
			for (int i = 0; i < list.size(); i++) {
				HxMusicVo hxMusicVo =hxUserVideoMapper.selectMusicID(list.get(i).getMusic_id());
				list.get(i).setHxMusicVo(hxMusicVo);
			}
			return list;
		}
		
	}

	@Override
	public HxVideoVo query_by_id(Long user_id, Long video_id) {
		HxVideoVo hxVideoVo = hxUserVideoMapper.query_by_id(user_id,video_id);
		return hxVideoVo;
	}

	@Override
	public List<HxVideoVo> query_follow_video(Map<String, Object> map) {
		List<HxVideoVo> list = hxUserVideoMapper.query_follow_video(map);
		return list;
	}

	@Override
	public HxVideoVo msg_like_video(Long user_id, Long video_id) {
		HxVideoVo hxVideoVo = hxUserVideoMapper.query_by_id(user_id, video_id);
		if (hxVideoVo==null) {
			throw new RuntimeException("该小视频已删除!");
		}
		if("1".equals(hxVideoVo.getIs_hide())||"4".equals(hxVideoVo.getStatus())||"3".equals(hxVideoVo.getStatus())){
			throw new RuntimeException("该小视频已删除!");
		}
		return hxVideoVo;
	}

	@Override
	public MsgCommentVideoVo msg_comment_video(Long user_id, Long comment_id) {
		//查询消息的那条评论
		HxUserVideoCommentVo hxUserVideoCommentVo = hxUserVideoCommentMapper.query_comment_by_id(comment_id);
		
		if (hxUserVideoCommentVo==null){
			throw new RuntimeException("该评论不存在!");
		}
		
//		if ("1".equals(hxUserVideoCommentVo.getIs_hide().toString())){
//			throw new RuntimeException("该评论已删除!");
//		}
		//查询小视频
		HxVideoVo hxVideoVo = hxUserVideoMapper.query_by_id(user_id, hxUserVideoCommentVo.getVideo_id());
		if (hxVideoVo==null){
			throw new RuntimeException("该小视频已删除!");
		}
		if("1".equals(hxVideoVo.getIs_hide())||"4".equals(hxVideoVo.getStatus())||"3".equals(hxVideoVo.getStatus())){
			throw new RuntimeException("该小视频已删除!");
		}
		
		Long user_id2 = null;
		//判断是否与本人有关系
		if (hxUserVideoCommentVo.getParent_comment_user()==null){
			user_id2 = null;
		} else {
			user_id2 = hxUserVideoCommentVo.getParent_comment_user().getUser_id();//回复的人
		}
		
		Long user_id3 = hxVideoVo.getUser_id();//发小视频的人
		
		if (!(user_id.equals(user_id2)||user_id.equals(user_id3))){
			throw new RuntimeException("该小视频与你没有关系!");
		}
		
		
		MsgCommentVideoVo commentVideoVo = new MsgCommentVideoVo();
		
		//查询10条评论
		Map<String, Object> map = new HashMap<>();
		map.put("video_id", hxUserVideoCommentVo.getVideo_id());
		map.put("last_video_comment_id", 0);
		map.put("pageSize", 10);
		List<HxUserVideoCommentVo> hxUserVideoCommentVos = hxUserVideoCommentMapper.query_comment(map);
		
		hxUserVideoCommentVos.add(0, hxUserVideoCommentVo);
		commentVideoVo.setHxVideoVo(hxVideoVo);
		commentVideoVo.setHxUserVideoCommentVos(hxUserVideoCommentVos);
		return commentVideoVo;
	}
    /**
     * 分页查看 音乐库中的数据
     * @param pageSize
     * @return
     */
    @Override
    public List<HxMusicVo> music_query(Integer pageSize,Integer page) {
        List<HxMusicVo> list =hxUserMusicMapper.selectMusicALL(pageSize,page);
        return list;
    }
    /**
     * 分页查询 音乐库和视频详情的log
     * @param map
     * @return
     */
    @Override
    public HxMusicVo music_shinee(Map<String, Object> map) {
//       Integer str =  Integer.parseInt(map.get("status").toString());
//        if (str == 1){//查询小视频详情的log
        HxMusicVo list = hxUserVideoMapper.selectVideo(map);

        return list;
    }

    @Override
    public List<HxVideoVo> music_video(Map<String, Object> map) {
        List<HxVideoVo> list = hxUserVideoMapper.selectDetails(map);
        return list;
    }

}
