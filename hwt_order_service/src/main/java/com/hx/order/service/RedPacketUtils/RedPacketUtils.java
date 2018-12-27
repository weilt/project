package com.hx.order.service.RedPacketUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.hx.core.redis.RedisCache;
import com.hx.core.utils.JsonUtils;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author Administrator
 *
 */
public class RedPacketUtils {
	
	 /**
	 * 设置值  失效时会触发   发红包
	 * 分区
	 * @Title: set
	 * @Description: TODO
	 * @param key 键
	 * @param value 值
	 * @param experice 失效时间
	 * @param redisIndex 区
	 * @return
	 */
	public  static boolean setparred(String key, String value,int experice,Integer redisIndex) {
		Jedis jedis = RedisCache.getJedis();
		
		config(jedis);//最重要的部分
		//订阅信息
        jedis.psubscribe(new RedisTimerListener(), "__keyevent@"+redisIndex+"__:expired");

		
		try { 
			jedis.select(redisIndex == null ? 0 : redisIndex);
			if ("OK".equals(jedis.set(key, value))) {
				jedis.expire(key, experice);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(jedis != null)
				jedis.close();
		}
	}
		
	 /**
     * 计算每人获得红包金额;最小每人0.01元
     * @param mmm 红包总额
     * @param number 人数
     * @return
     */
	 public static List<String> math(BigDecimal mmm, int number) {
	        if (mmm.doubleValue() < number * 0.01) {
	            return null;
	        }
	        Random random = new Random();
	        // 金钱，按分计算 10块等于 1000分
	        int money = mmm.multiply(BigDecimal.valueOf(100)).intValue();
	        // 随机数总额
	        double count = 0;
	        // 每人获得随机点数
	        double[] arrRandom = new double[number];
	        // 每人获得钱数
	        List<String> arrMoney = new ArrayList<String>(number);
	        // 循环人数 随机点
	        for (int i = 0; i < arrRandom.length; i++) {
	            int r = random.nextInt((number) * 99) + 1;
	            count += r;
	            arrRandom[i] = r;
	        }
	        // 计算每人拆红包获得金额
	        int c = 0;
	        for (int i = 0; i < arrRandom.length; i++) {
	            // 每人获得随机数相加 计算每人占百分比
	            Double x = new Double(arrRandom[i] / count);
	            // 每人通过百分比获得金额
	            int m = (int) Math.floor(x * money);
	            // 如果获得 0 金额，则设置最小值 1分钱
	            if (m == 0) {
	                m = 1;
	            }
	            // 计算获得总额
	            c += m;
	            // 如果不是最后一个人则正常计算
	            if (i < arrRandom.length - 1) {
	                arrMoney.add((new BigDecimal(m).divide(new BigDecimal(100))).toString());
	            } else {
	                // 如果是最后一个人，则把剩余的钱数给最后一个人
	                arrMoney.add((new BigDecimal(money - c + m).divide(new BigDecimal(100))).toString());
	            }
	        }
	        // 随机打乱每人获得金额
	        Collections.shuffle(arrMoney);
	        return arrMoney;
	    }
	 
//	 public static void main(String[] args) {
//		 List<BigDecimal> math = math(new BigDecimal("0.01"), 2);
//		 //math.clear();
//		 String bean2Json = JsonUtils.Bean2Json(math);
//		 System.out.println(bean2Json);
//		 List<BigDecimal> json2List = JsonUtils.json2List(bean2Json, BigDecimal.class);
//		 System.out.println(json2List);
//		 
//		 
//	}
	 
	 private static void config(Jedis jedis){
	        String parameter = "notify-keyspace-events";
	        List<String> notify = jedis.configGet(parameter);
	        if(notify.get(1).equals("")){
	            jedis.configSet(parameter, "Ex"); //过期事件
	        }
	 }

}
