package com.hx.order.service.RedPacketUtils;

import org.springframework.stereotype.Component;

import com.hx.core.redis.RedisCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * 监听器，监听redis中键到期自动删除事件，（键需要设置过期时间）
 * 
 * 需要在redis配置文件中设置 notify-keyspace-events 的值为 Ex
 */
@Component
public class RedisTimerListener extends JedisPubSub {

	// 获取jedis连接
//	Jedis jedis;
//
//	public void init() {
//
//		Jedis jedis = RedisCache.getJedis();
//
//	}

	/**
	 * 监听器被加载时执行该方法
	 */
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		RedisCache.getJedis();
		System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
	}

	/**
	 * 键到期自动删除时触发该方法
	 */
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		//init();
		Jedis jedis = RedisCache.getJedis();
		System.out.println(jedis.get(message));
		System.out.println("onPMessage pattern " + pattern + " " + channel + " " + message);

	}
	

	public static void main(String[] args) {
		JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "192.168.1.246", 6379, 3000, "cqhx1123");
		Jedis jedis = jedisPool.getResource();

		jedis.select(1);
		// 将键放入redis，并设置生存时间 5s
		jedis.set("shorderid_12", "a12");
		jedis.expire("shorderid_12", 5);

		// 删除键 (手动删除键时不触发事件)
		// jedis.del("shorderid_12");

		jedis.set("shorderid_14", "a14");
		jedis.expire("shorderid_14", 7);

		// JedisPool jedisPool = new JedisPool(new
		// JedisPoolConfig(),"127.0.0.1",6379,0,"redis",0);
		// Jedis jedis = jedisPool.getResource();

		// 订阅信息
		jedis.psubscribe(new RedisTimerListener(), "__keyevent@1__:expired");

	}

}
