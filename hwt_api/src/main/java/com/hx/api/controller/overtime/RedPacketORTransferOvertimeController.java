
package com.hx.api.controller.overtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hx.api.base.ResultEntity;
import com.hx.core.redis.RedisLock;
import com.hx.core.systemconfig.HwPrefix;
import com.hx.user.async.UserAsyncService;

/**
 * 红包
 * @author Administrator
 *
 */
@RestController
public class RedPacketORTransferOvertimeController {
	
	@Autowired
	private UserAsyncService userAsyncService;

	
	/**
	 * 红包、转账 key 过期
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redPacketORTransferOvertime",method = RequestMethod.POST)
	public ResultEntity redPacketORTransferOvertime(String message) throws Exception{
		
		if (message.startsWith(HwPrefix.RedPacketPrefix)){
			userAsyncService.redPacketExpire(message);
		} else if (message.startsWith(HwPrefix.TransferPrefix)){
			userAsyncService.transferExpire(message);
		}
		return new ResultEntity();
		
	}
}
