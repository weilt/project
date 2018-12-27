//package com.hx.core.redis;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import redis.clients.jedis.Jedis;
//
//@Component
//public class SubscribeThread  implements CommandLineRunner {
//	
//	
//	@Override
//    public void run(String... strings) throws Exception {
//        Jedis jedis= RedisCache.getJedis();
//        try {
//            //监听所有reids通道中的过期事件
//        	jedis.psubscribe(new Subscribe(), "__keyevent@10__:expired");
//        } catch (Exception e) {
//            jedis.close();
//            e.printStackTrace();
//        }finally {
//            jedis.close();
//        }
//    }
//
//}
