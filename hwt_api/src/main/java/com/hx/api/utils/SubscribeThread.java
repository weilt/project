package com.hx.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hx.core.redis.RedisCache;

import redis.clients.jedis.Jedis;

@Component
public class SubscribeThread  implements CommandLineRunner {
	
	@Value("${redisExpireUrl}")
	private String redisExpireUrl;
	
	@Override
	@Async("asyncServiceExecutor")
    public void run(String... strings) throws Exception {
        Jedis jedis= RedisCache.getJedis();
        try {
            //监听所有reids通道中的过期事件
        	jedis.psubscribe(new Subscribe(redisExpireUrl), "__keyevent@10__:expired");
        	System.err.println("+---------------------------------------------+");
        } catch (Exception e) {
            jedis.close();
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }

}
