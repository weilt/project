package com.hwt.domain.entity.user.video.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="从消息界面点赞过去查看小视频")
public class MsgCommentVideoVo {
	
	@ApiModelProperty(value="小视频")
	private HxVideoVo hxVideoVo;
	
	@ApiModelProperty(value="评论")
	private List<HxUserVideoCommentVo> hxUserVideoCommentVos;

	public HxVideoVo getHxVideoVo() {
		return hxVideoVo;
	}

	public void setHxVideoVo(HxVideoVo hxVideoVo) {
		this.hxVideoVo = hxVideoVo;
	}

	public List<HxUserVideoCommentVo> getHxUserVideoCommentVos() {
		return hxUserVideoCommentVos;
	}

	public void setHxUserVideoCommentVos(List<HxUserVideoCommentVo> hxUserVideoCommentVos) {
		this.hxUserVideoCommentVos = hxUserVideoCommentVos;
	}
	
	
}
