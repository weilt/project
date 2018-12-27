//package com.hx.api.controller;
//
//import com.hwt.domain.entity.order.HwOrderUnpaid;
//import com.hwt.domain.entity.user.TestUser;
//import com.hwt.domain.mapper.order.HwOrderUnpaidMapper;
//import com.hx.core.redis.RedisCache;
//import com.hx.core.utils.JsonUtils;
//import com.hx.core.utils.ObjectHelper;
//import com.hx.order.service.hx.OrderService;
//import com.hx.order.service.hx.PayService;
//import com.hx.user.video.service.VideoService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
///**
// * Created by RO on 2018/3/28.
// */
//@RestController
//public class TestController {
//
//	@Autowired
//	private OrderService orderService; 
//	
//	@Autowired
//	private PayService payService;
//	
//	@Autowired
//	private HwOrderUnpaidMapper hwOrderUnpaidMapper;
//	
//	@Autowired
//	private VideoService videoService;
//	
//    @RequestMapping("/test")
//    public void test() throws Exception {
//    	//导游
//    	for (int i = 0; i < 1; i++) {
//    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    		Date parse = simpleDateFormat.parse("2018-11-26");
//    		long time = parse.getTime() + i*24*60*60*1000;
//    		long time1 = time + 1*24*60*60*1000 -1;
//			HwOrderUnpaid hwOrder = new HwOrderUnpaid();
//			hwOrder.setUser_id(2108l);
//			hwOrder.setCicerone_id(1107l);
//			hwOrder.setAdult_num(1);
//			hwOrder.setChildren_num(0);
//			hwOrder.setBuyer_name("nihao");
//			hwOrder.setBuyer_phone("13452828493");
//			hwOrder.setLine_id(0l);
//			hwOrder.setBureau_id(0l);
//			hwOrder.setStart_time(time);
//			hwOrder.setEnd_time(time1);
//			orderService.insert(hwOrder, null);
//		}
//    }
//    
//    @RequestMapping("/testpay")
//    public void testpay() throws Exception {
//    	long time = System.currentTimeMillis() - 20*60*1000l;
//		List<HwOrderUnpaid> query_no_unpaid = hwOrderUnpaidMapper.query_no_unpaid(time);
//		if (!ObjectHelper.isEmpty(query_no_unpaid)){
//			for (HwOrderUnpaid hwOrderUnpaid : query_no_unpaid) {
//				payService.wallet_pay(2108l, hwOrderUnpaid.getOrder_num(), "123456", null);
//			}
//		}
//    }
//    
////    @RequestMapping("/testv")
////    public void tetsss() throws Exception{
////		for (int i = 0; i < 100; i++) {
////			videoService.insert(2108l, 0l,"很好","chengdu.myqcloud.com/cover/TXVideo_20181102_143800.jpg",
////					"chengdu.myqcloud.com/video/TXVideo_20181102_143800.mp4",1,null,null,null);
////		}
////	}
//    @RequestMapping("/testRedis")
//    public void tetsss() throws Exception{
//    	List<String> list = new ArrayList<>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		
//		RedisCache.hsetpar("RedPacket_500_1530192067_0", null, list, 30, RedisCache.db10);
//    }
//   
//    @RequestMapping("/testaaa")
//    public void testaaa(String message) throws Exception{
//    	
//    	
////    	String string = RedisCache.get("RedPacket_500_1530192067_0", RedisCache.db10);
////    	System.out.println(string);
//    	
//    	
//    	Map<String, String> hgetgey = RedisCache.hgetgey("RedPacket_500_1530192067_0", RedisCache.db10);
//    	System.err.println(hgetgey);
//    	System.err.println(hgetgey.isEmpty());
//    	RedisCache.del("RedPacket_500_1530192067_0", RedisCache.db10);
//    	System.out.println();
//    	RedisCache.hdel("RedPacket_500_1530192067_0", "0", RedisCache.db10);
//    	RedisCache.hdel("RedPacket_500_1530192067_0", "1", RedisCache.db10);
//    	RedisCache.hdel("RedPacket_500_1530192067_0", "2", RedisCache.db10);
//    	Map<String, String> hgetgey1 = RedisCache.hgetgey("RedPacket_500_1530192067_0", RedisCache.db10);
//    	System.err.println(hgetgey1.isEmpty());
//    	System.err.println(ObjectHelper.isEmpty(hgetgey1));
//    }
//}
