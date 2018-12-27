package com.hx.core.wyim.entity.system;

import com.hx.core.utils.UUIDHelper;

/**
 * 小视频通知
 * @author Administrator
 *
 */
public class VideoNotice {
	
	private int type;                   // 1-小视频发布(来自后台通知)
    // 2-小视频点赞(视频主人收到信息)
    // 3-小视频评论和视频主人直接关联属于一级评论(发送给视频主人点击时当前信息置顶第一个)
    // 4-小视频评论二级相关的两个之间发送(点击时当前信息置顶第一个)
    // 5-小视频评论删除


	private long commentId;             // 当前的评论id
	private long replyId;               // 当前的回复评论id
	private String replyName;           // 回复人名字
	private String replyAvatar;         // 回复人头像
	private String commentContent;      // 当前评论的内容
	private long createTime;            // 创建时间
	private String aboutSmallVideos;    // 实体
	private long actualId;              // 实际id查询该条小视频的信息
	
	
	public VideoNotice(int type, long commentId, long replyId, String replyName, String replyAvatar,
			String commentContent, long createTime, String aboutSmallVideos, long actualId) {
		super();
		this.type = type;
		this.commentId = commentId;
		this.replyId = replyId;
		this.replyName = replyName;
		this.replyAvatar = replyAvatar;
		this.commentContent = commentContent;
		this.createTime = createTime;
		this.aboutSmallVideos = aboutSmallVideos;
		this.actualId = actualId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public long getReplyId() {
		return replyId;
	}
	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public String getReplyAvatar() {
		return replyAvatar;
	}
	public void setReplyAvatar(String replyAvatar) {
		this.replyAvatar = replyAvatar;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getAboutSmallVideos() {
		return aboutSmallVideos;
	}
	public void setAboutSmallVideos(String aboutSmallVideos) {
		this.aboutSmallVideos = aboutSmallVideos;
	}
	public long getActualId() {
		return actualId;
	}
	public void setActualId(long actualId) {
		this.actualId = actualId;
	}
	
}
