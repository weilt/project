package com.hx.user.async;

public interface UserAsyncService {
	
	/**
	 * 转账过期
	 * @param message
	 * @throws Exception
	 */
	public void transferExpire(String message) throws Exception ;
	
	/**
	 * 红包过期
	 * @param message
	 * @throws Exception
	 */
	public void redPacketExpire(String message) throws Exception ;
}
