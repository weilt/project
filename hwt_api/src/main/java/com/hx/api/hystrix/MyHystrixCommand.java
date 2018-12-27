//package com.hx.api.hystrix;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//
//public class MyHystrixCommand extends HystrixCommand<String> {
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(MyHystrixCommand.class);
//	 private String name;
//
//	 public MyHystrixCommand(HystrixCommandGroupKey group, String name) {
//	        super(group);
//	        this.name = name;
//	    }
//	 
//	 public MyHystrixCommand(Setter setter, String name) {
//    	 super(setter);
//    	
//         this.name = name;
//    }
//
//	 @Override
//	    protected String run() throws Exception {
//	        if ("Alice".equals(name)) {
//	        	 LOGGER.error("服务调用失败,service:'{}'");
//	            throw new RuntimeException("出错了");
//	        }
//	        
//	        return "Hello, " + name;
//	    }
//
//	 @Override
//    protected String getFallback() {
//		 LOGGER.error("服务调用失败,service:'{}'");
//        return "Failure, " + name;
//    }
//}
