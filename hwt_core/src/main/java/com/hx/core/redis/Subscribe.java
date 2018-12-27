//package com.hx.core.redis;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import redis.clients.jedis.JedisPubSub;
//
//public class Subscribe extends JedisPubSub {
//    private static final Log log= LogFactory.getLog(Subscribe.class);
//    
//    // 初始化按表达式的方式订阅时候的处理
//    public void onPSubscribe(String pattern, int subscribedChannels) {
//        log.info("Subscribe-onPSubscribe>>>>>>>>>>>>>>>>>>>>>>>>"+pattern + "=" + subscribedChannels);
//    }
//    
//    // 取得按表达式的方式订阅的消息后的处理
//    public void onPMessage(String pattern, String channel, String message) {
//        try {
//            log.info(pattern + "=" + channel + "=" + message);
//            //在这里写你相关的逻辑代码
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
