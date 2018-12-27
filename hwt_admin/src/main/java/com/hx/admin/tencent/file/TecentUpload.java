package com.hx.admin.tencent.file;

import java.util.Map;
import java.util.TreeMap;

import com.hx.core.utils.ObjectHelper;

public class TecentUpload {
	private static String EXTERNAL_ENDPOINT = "https://video-1257315329.cos.ap-chengdu.myqcloud.com";
	private static String BUCKET_NAME = "video-1257315329";
	/**
	 * app端上传图片，返回token
	 * @return
	 */
	public static Map<String, Object>  getToken() {
		
		TreeMap<String, Object> config = new TreeMap<String, Object>();

		// 您的 SecretID
		config.put("SecretId", "AKIDZ2KUWMtUlqkSzKczc4LQL4e2FG9rthpI");
		// 您的 SecretKey
		config.put("SecretKey", "RhleMI76wc7ScaMhttkT1NYXLFSZVJwN");
		// 临时密钥有效时长，单位是秒，如果没有设置，默认是30分钟
		config.put("durationInSeconds", 1800);
		// 配置的 policy
		
		  //String policy = "{\"statement\": [{\"action\": [\"name/cos:GetObject\",\"name/cos:PutObject\"],\"effect\": \"allow\",\"resource\":[\"qcs::cos:ap-beijing:uid/12345678910:prefix//12345678910/sevenyou/*\"]}],\"version\": \"2.0\"}";
		//全读写
		String policy = "{\"version\":\"2.0\",\"statement\":[{\"action\":[\"cos:*\"],\"resource\":\"*\",\"effect\":\"allow\"},{\"effect\":\"allow\",\"action\":[\"monitor:*\",\"cam:ListUsersForGroup\",\"cam:ListGroups\",\"cam:GetGroup\"],\"resource\":\"*\"}]}";
	        
		config.put("policy", policy);
		
		
		Map<String, Object> map  = StorageSts.getCredential(config);
//		map.put("EXTERNAL_ENDPOINT",EXTERNAL_ENDPOINT);
//		map.put("BUCKET_NAME",BUCKET_NAME);
//		map.put("startTime", System.currentTimeMillis());
		return map;
	}
	
}
