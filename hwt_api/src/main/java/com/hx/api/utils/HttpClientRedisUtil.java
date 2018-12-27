package com.hx.api.utils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.hx.core.utils.RandomUtils;


/**
 * Created by RO on 2018/3/7.
 */
public class HttpClientRedisUtil {

    /**
     * HTTPClient POST请求
     * @param url       请求地址
     * @param paramMap  参数列表
     * @return  请求响应JSON
     * @throws Exception
     */
    public static String postHttp(String url, Map<String,String> paramMap) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
       // HttpClient httpClient = HttpClientBuilder.create().build();
        
        HttpPost httpPost = new HttpPost(url);
        
        // 设置请求的header
//        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        String sessionId = getSessionId();
//        httpPost.setHeader("SessionId", sessionId);
        httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true); 
//        httpPost.addHeader("AppKey", "aaa");
//        httpPost.addHeader("Nonce", RandomUtils.randomString(16));
//        httpPost.addHeader("CurTime", String.valueOf((new Date()).getTime() / 1000L));
      
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<>();

        if(paramMap != null) {
            for (String s : paramMap.keySet()) {
                nvps.add(new BasicNameValuePair(s, paramMap.get(s)));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
    
 // 构建唯一会话Id
    public static String getSessionId(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }
}
