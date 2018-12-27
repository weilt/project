package com.hx.user.redPacket.service;

import java.math.BigDecimal;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.vo.HxUserRedPacketVo;
import com.hwt.domain.entity.user.redPacket.vo.UserRobRedPacketVo;

public interface RedPacketService {

	/**
	 * 发红包
	 * @param user_id			发送人id
	 * @param title				红包名称
	 * @param content			消息文本内容
	 * @param is_random 		1-随机  0-均等
	 * @param group_id			群id 0不是群
	 * @param accept_user_id	收红包人id  0-说明是群红包
	 * @param send_amount		红包金额
	 * @param packet_number		红包数
	 * @return
	 */
	HxUserRedPacket send(Long user_id, String title, String content, Integer is_random, Long group_id, Long accept_user_id,
			BigDecimal send_amount,Integer packet_number);

	/**
	 * 抢红包
	 * @param loginVo
	 * @param red_packet_id
	 * @param group_id
	 * @param accept_user_id
	 * @return 
	 */
	int rob(LoginVo loginVo, Long red_packet_id, Long group_id, Long accept_user_id)  throws Exception ;

	/**
	 * 查询红包详情
	 * @param red_packet_id
	 * @return
	 */
	HxUserRedPacketVo queryInfo(Long red_packet_id);

}
