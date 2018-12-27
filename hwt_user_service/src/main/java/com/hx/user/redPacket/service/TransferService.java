package com.hx.user.redPacket.service;

import java.math.BigDecimal;

import com.hwt.domain.entity.user.Vo.LoginVo;
import com.hwt.domain.entity.user.redPacket.HxUserTransfer;
import com.hwt.domain.entity.user.redPacket.vo.HxUserTransferVo;

public interface TransferService {

	/**
	 * 转账
	 * @param user_id
	 * @param title
	 * @param content
	 * @param accept_user_id
	 * @param send_amount
	 * @param transfer_type 
	 * @return
	 */
	HxUserTransfer send(Long user_id, String title, String content, Long accept_user_id, BigDecimal send_amount, Integer transfer_type);

	
	/**
	 * 领取
	 * @param loginVo
	 * @param transfer_id
	 * @param send_user_id
	 * @param accept_user_id
	 * @param is_accept 
	 * @return
	 */
	int accept(LoginVo loginVo, Long transfer_id, Long send_user_id, Long accept_user_id, Integer is_accept) throws Exception ;


	/**
	 * 判断红包领过了，还是过期了
	 * @param transfer_id
	 * @return
	 */
	int is_accept(Long transfer_id);


	/**
	 * 根据转账id查询
	 * @param user_id
	 * @param transfer_id
	 * @return
	 */
	HxUserTransferVo info(Long user_id, Long transfer_id);

}
