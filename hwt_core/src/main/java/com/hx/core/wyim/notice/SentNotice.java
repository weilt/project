package com.hx.core.wyim.notice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hx.core.systemconfig.HXSystemConfig;
import com.hx.core.utils.GsonUtil;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.ObjectHelper;
import com.hx.core.wyim.entity.FriendOperationNotice;
import com.hx.core.wyim.entity.SMSUser;
import com.hx.core.wyim.entity.emu.SMSNotice;
import com.hx.core.wyim.entity.system.SystemNotice;
import com.hx.core.wyim.entity.system.VideoNotice;
import com.hx.core.wyim.service.ImService;
import com.hx.core.wyim.service.SmsService;

/**
 * 系统消息
 * @author Administrator
 *
 */
@Service
public class SentNotice {
	
	@Autowired
	private ImService imService;
	
	@Autowired
	private SmsService smsService;
	
	/**
	 * 发送系统消息
	 * @param im_id      接受消息用户id
	 * @param title			消息标题
	 * @param description	描述
	 * @param url			链接
	 * @param type			//1 文本 2 账单 3 html//4 导游拒绝或者取消或者评价
	 * 					 5 线路被确认或者拒绝或者取消或者评价//   6 导游被取消了 7 其他消息 //8 导游需要确认  //9小视频初级升中级
	 * @param actualId		//   查询依据的id
	 * @param aboutBills	//账单实体
	 * @throws Exception		
	 */
	public void sendSystem(String im_id,String title,String description,String url,int type,String actualId,String aboutBills) throws Exception{
		
		SystemNotice systemNotice = new SystemNotice(title, description, url, type, System.currentTimeMillis(), actualId, aboutBills);
		
		Map<String, Object> msg = new HashMap<>();
		msg.put("msg", description);
		
		Map<String, Object> ext = new HashMap<>();
		ext.put("systemMsgType", 10000);
		
		//发送通知
		imService.singleNotice(HXSystemConfig.HX_OFFICIAL_IM_ID, im_id,
				GsonUtil.toJson(new FriendOperationNotice(null,null, 10000, GsonUtil.toJson(systemNotice))), 2);
		//发消息
		imService.sendMsgPTOPExt(HXSystemConfig.HX_OFFICIAL_IM_ID, 0+"", im_id, 0,  JsonUtils.Bean2Json(msg),JsonUtils.Bean2Json(ext));
				
		
		
	}
	
	
	/**
	 * 发送系统短信
	 */
	public void sendSystemSms(String contacts_phome,int template,String order_num) throws Exception{
		//发送消息
		SMSUser smsUser = new SMSUser();
		smsUser.setMobiles(new String[]{contacts_phome});
		smsUser.setTemplateid(template);
		if (!ObjectHelper.isEmpty(order_num)){
			smsUser.setParams(new String[]{order_num});
		}
		
		smsService.sendtemplate(smsUser);
	}
	
//	/**
//	 * 发送系统消息Msg
//	 */
//	public void sendSystemMsg(String im_id,String title,String description,String url,int type,String actualId,String aboutBills) throws Exception{
//		
//		SystemNotice systemNotice = new SystemNotice(title, description, url, type, System.currentTimeMillis(), actualId, aboutBills);
//		
//		Map<String, Object> map = new HashMap<>();
//		map.put("msg", systemNotice);
//		//发消息
//		imService.sendMsgPTOP(HXSystemConfig.HX_OFFICIAL_IM_ID, 0+"", im_id, 0,  JsonUtils.Bean2Json(map));
//	}
	
	/**
	 * 小视频通知发送
	 * @param replyName      发视频的用户昵称
	 * @param replyAvatar      发视频的用户头像
	 * @param im_id      接受消息用户id
	 * @param title			消息标题
	 * @param description	描述
	 * @param type			//1 小视频
	 * @param actualId		//   查询依据的id
	 * @param aboutBills	//实体
	 * @throws Exception		
	 */
	public void sendVideo(String replyName,String replyAvatar,String im_id,String title,String description,int type,Long actualId,String aboutBills) throws Exception{
		
		VideoNotice videoNotice = new VideoNotice(type, 0l, 0l, replyName,
				replyAvatar, null, System.currentTimeMillis(), aboutBills, actualId);
		Map<String, Object> msg = new HashMap<>();
		msg.put("msg", description);
		
		Map<String, Object> ext = new HashMap<>();
		ext.put("systemMsgType", 10001);
		
		//发送通知
		imService.singleNotice(HXSystemConfig.HX_OFFICIAL_IM_ID, im_id,
				GsonUtil.toJson(new FriendOperationNotice(null,null, 10001, GsonUtil.toJson(videoNotice))), 2);
		//发消息
		imService.sendMsgPTOPExt(HXSystemConfig.HX_OFFICIAL_IM_ID, 0+"", im_id, 0,  JsonUtils.Bean2Json(msg),JsonUtils.Bean2Json(ext));
		
	}
}
