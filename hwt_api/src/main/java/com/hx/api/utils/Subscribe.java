package com.hx.api.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hx.core.redis.RedisCache;
import com.hx.core.utils.JsonUtils;

import redis.clients.jedis.JedisPubSub;



public class Subscribe extends JedisPubSub {
    private static final Log log= LogFactory.getLog(Subscribe.class);
    
    private String redisExpireUrl;
    
    
    
    // 初始化按表达式的方式订阅时候的处理
	@Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info("Subscribe-onPSubscribe>>>>>>>>>>>>>>>>>>>>>>>>"+pattern + "=" + subscribedChannels);
    }
    
    // 取得按表达式的方式订阅的消息后的处理
   // @Transactional
	@Override
    public void onPMessage(String pattern, String channel, String message) {
		
		String[] split = message.split("_");
		
		//前缀
		String prefix = split[0];
		
		
		Long red_packet_id = Long.parseLong(split[1]) ;
		
		
		
		Long group_id = Long.parseLong(split[2]) ;
		
		
		Long accept_user_id = Long.parseLong(split[3]);
		
		//处理的次数
		Integer count = 0;
		if (split.length==5){
			count = Integer.parseInt(split[4]);
			
		}
		
		count++;
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append(prefix).append("_").append(red_packet_id)
      	.append("_").append(group_id).append("_").append(accept_user_id).append("_").append(count);
    	Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("message", message);
    	//RedisCache.del(message, RedisCache.db10);
		try {
			log.info("请求开始>>>>>>>>>>>>>>>>>>>>>>>>" + "=======" + message);
		//	Thread.sleep(5000);
			String postHttp = HttpClientRedisUtil.postHttp(redisExpireUrl, paramMap );
			Map<String, Object> json2Map = JsonUtils.json2Map(postHttp);
			System.err.println(stringBuffer.toString()+"--------------");
			if (!"200".equals(json2Map.get("code").toString())){
				//说明失败了
				//每次最加10秒
				RedisCache.setpar(stringBuffer.toString(), "faile", 10, RedisCache.db10);
				
			}
			log.info("pattern>>>>>>>>>>>>>>>>>>>>>>>>"+postHttp + "=======" + message);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//RedisCache.del(message, RedisCache.db10);
			RedisCache.setpar(stringBuffer.toString(), "faile", 10, RedisCache.db10);
		}
    	
//    	String[] split = message.split("_");
//		
//		//前缀
//		String prefix = split[0];
//		
//		//实体的id
//		Long transfer_id = Long.parseLong(split[1]) ;
//		
//		
//		//转账人id
//		Long user_id = Long.parseLong(split[2]) ;
//		
//		//接受人id
//		Long accept_user_id = Long.parseLong(split[3]);
//		
//		//处理的次数
//		Integer count = 0;
//		if (split.length==5){
//			count = Integer.parseInt(split[4]);
//			
//		}
//		
//		if (count>2){
//			//说明超过3次了  
//			throw new BaseException("超过3次了，请联系开发人员！");
//		}
//		count++;
//        try {
//            log.info(pattern + "=" + channel + "=" + message);
//            
//            
//            if(message.startsWith(HwPrefix.RedPacketPrefix)){
//            	//说明是红包过期了
//            	redPacketExpire(message);
//            	
//          //  } else if (message.startsWith(HwPrefix.TransferPrefix)){
//            } else {
//            	//说明是转账过期了
//        	   transferExpire(message);
//            }
//            
//            
//            //在这里写你相关的逻辑代码
//        }catch (Exception e){
//            e.printStackTrace();
//            if (count>2){
//            	//发消息告知开发人员
//            }
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append(prefix).append("_").append(transfer_id)
//            	.append("_").append(user_id).append("_").append(accept_user_id).append("_").append(count);
//            //60秒执行一次
//            RedisCache.setpar(stringBuffer.toString(), count.toString(), 60, RedisCache.db10);
//            throw new RuntimeException("监听rides key消失，业务处理失败！");
//        }
    }

    

	public String getRedisExpireUrl() {
		return redisExpireUrl;
	}

	public void setRedisExpireUrl(String redisExpireUrl) {
		this.redisExpireUrl = redisExpireUrl;
	}

	public Subscribe(String redisExpireUrl) {
		super();
		this.redisExpireUrl = redisExpireUrl;
	}

	
}
