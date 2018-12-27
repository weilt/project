package com.hx.api.config;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hx.api.base.ResultEntity;
import com.hx.core.utils.JsonUtils;
import com.hx.core.utils.Md5Utils;
import com.hx.core.utils.ObjectHelper;



/**
 * 签名认证
 * @author JIAO_LIU_BABA
 */
//@WebFilter(urlPatterns = {"/*"})
public class SignAutheFilter implements Filter{
	
	
	
	private static final Logger log = LoggerFactory.getLogger(SignAutheFilter.class);
	
	@Value("${releaseIps}")
	private String[] releaseIps;
 
	@Value("${secret}")
	private String secret;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        res.setHeader("Content-type", "text/html;charset=UTF-8");  
		//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859  
        response.setCharacterEncoding("UTF-8");  
        
        String url = req.getRequestURI();
        log.info(" doFilter uri : " + url);
        
        boolean boo = boo(url); //包含的数据直接过滤
		if(boo){
			chain.doFilter(request, response);
			return;
		}
		
		if(ObjectHelper.getOrPost(req)){
			chain.doFilter(request, response);
			return;
		}
		// 获取客户端ip
		String ip = ObjectHelper.getIpAddress(req);
		
		log.info("getted ip is ---> " + ip);
		// 判断该ip是否放行
		boolean containIp = false;
		for (String string : releaseIps) {
			if (string.equals(ip)) {
				containIp = true;
				break;
			}
		}
		if (containIp){
			chain.doFilter(request, response);
			return;
		}
		
	

		/* 
		 * 读取请求体中的数据(字符串形式)
		 * 注:由于同一个流不能读取多次;如果在这里读取了请求体中的数据,那么@RequestBody中就不能读取到了
		 *    会抛出异常并提示getReader() has already been called for this request
		 * 解决办法:先将读取出来的流数据存起来作为一个常量属性.然后每次读的时候,都需要先将这个属性值写入,再读出.
		 *        即每次获取的其实是不同的流,但是获取到的数据都是一样的.
		 *        这里我们借助HttpServletRequestWrapper类来实现
		 *      注:此方法涉及到流的读写、耗性能;
		 */
		
		Map<String, String> parameterMap = getParameterMap(req);
		log.info("getted requestbody data is ---> " + parameterMap);
		
		// 获取几个相关的字符
		// 由于authorization类似于
		// cardid="1234554321",timestamp="9897969594",signature="a69eae32a0ec746d5f6bf9bf9771ae36"
		// 这样的,所以逻辑是下面这样的
		
//		int signatureIndex = info[2].indexOf("=") + 2;
//		String signature = info[2].substring(signatureIndex, info[2].length() - 1);
		
		Map<String, String> headersInfo = getHeadersInfo(req);
		String sign = headersInfo.get("sign");
		
		String tmptString = getSignature(parameterMap, secret);
		log.info("getted ciphertext is ---> {}, correct ciphertext is ---> {}", 
				sign , tmptString);
		
		
		
		// 再判断Authorization内容是否正确,进而判断是否最终放行
		boolean couldPass = tmptString.equalsIgnoreCase(sign);
		if (couldPass) {
			// 放行
			chain.doFilter(request, response);
			return;
		}
		res.getWriter().write(JsonUtils.Bean2Json(new ResultEntity("403","签名错误")));

	}

	@Override
	public void destroy() {
		
	}
	
	//过滤的请求地址 - session值的数据
	private String[] filter = {"/admin/user/index","/admin/user/left"};
	//过滤的带后缀的地址
	private String[] filter_to = {".html",".js",".png",".PNG",".jsp",".htm",".jpg",".gif",".text"};
	//请求过滤信息
	public boolean booSession(String url){
		for(int i = 0;i<filter.length;i++){
			if(url.equals(filter[i])){
				return true;
			}
		}
		return false;
	}
	//请求过滤信息
	public boolean boo(String url){
		for(int i = 0;i < filter_to.length;i++){
			if(url.contains(filter_to[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 和风天气签名生成算法-JAVA版本
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String secret 签名密钥（用户的认证key）
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(Map<String,String> params, String secret) throws IOException {
	        // 先将参数以其参数名的字典序升序进行排序
	        Map<String, String> sortedParams = new TreeMap<>(params);
//	        System.err.println(sortedParams);
	        // 使用MD5对待签名串求签
	        String betData = getBetData(JsonUtils.Bean2Json(sortedParams));
	        betData =  betData.replace(" ", "");
	        betData =  betData.replace("\\n", "");
	        betData =  betData.replace("\\", "");
	        String encodeMD5 = Md5Utils.encodeMD5(betData+secret);
	        return encodeMD5;
	    }
	
	public static String getBetData(String betData) {
	    String data = betData;
	    try {
	        // 将application/x-www-from-urlencoded字符串转换成普通字符串
	        data = URLDecoder.decode(betData, "UTF-8");
	        // 将普通字符创转换成application/x-www-from-urlencoded字符串
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return data;
	}
	 public static Map<String,String> getParameterMap(HttpServletRequest request) {  
	        // 参数Map  
	        Map properties = request.getParameterMap();  
	        // 返回值Map  
	        Map<String,String>  returnMap = new HashMap<String,String>();  
	        Iterator entries = properties.entrySet().iterator();  
	        Map.Entry entry;  
	        String name = "";  
	        String value = "";  
	        while (entries.hasNext()) {  
	            entry = (Map.Entry) entries.next();  
	            name = (String) entry.getKey();  
	            Object valueObj = entry.getValue();  
	            if(null == valueObj){  
	                value = "";  
	            }else if(valueObj instanceof String[]){  
	                String[] values = (String[])valueObj;  
	                for(int i=0;i<values.length;i++){  
	                    value = values[i] + ",";  
	                }  
	                value = value.substring(0, value.length()-1);  
	            }else{  
	                value = valueObj.toString();  
	            }
	            returnMap.put(name, value);  
	        }  
	        return returnMap;  
	    } 
	 
	 private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		    Map<String, String> map = new HashMap<String, String>();
		    Enumeration headerNames = request.getHeaderNames();
		    while (headerNames.hasMoreElements()) {
		        String key = (String) headerNames.nextElement();
		        String value = request.getHeader(key);
		        map.put(key, value);
		    }
		    return map;
	 }
}


///**
// * 辅助类 ---> 变相使得可以多次通过(不同)流读取相同数据
// *
// * @author JustryDeng
// * @DATE 2018年9月11日 下午7:13:52
// */
//class MyRequestWrapper extends HttpServletRequestWrapper {
// 
//    private final String body;
// 
//    public String getBody() {
//		return body;
//	}
// 
//	public MyRequestWrapper(final HttpServletRequest request) throws IOException {
//        super(request);
//        StringBuilder sb = new StringBuilder();
//        String line;
//        BufferedReader reader = request.getReader();
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
// 
//        body = sb.toString();
//    }
// 
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
//        return new ServletInputStream() {
//            /*
//             * 重写ServletInputStream的父类InputStream的方法
//             */
//            @Override
//            public int read() throws IOException {
//                return bais.read();
//            }
//            
//			@Override
//			public boolean isFinished() {
//				return false;
//			}
// 
//			@Override
//			public boolean isReady() {
//				return false;
//			}
// 
//			@Override
//			public void setReadListener(ReadListener listener) {	
//			}
//        };
//    }
// 
//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(this.getInputStream()));
//    }
//
//}
