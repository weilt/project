package com.hx.api.tencent.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "腾讯云获取临时上传token")
public class TecentAPPSTS {
	
	@ApiModelProperty(value="sessionToken")
	private String sessionToken;
	
	@ApiModelProperty(value="tmpSecretId")
	private String tmpSecretId;
	
	@ApiModelProperty(value="tmpSecretKey;")
	private String tmpSecretKey;
	
	@ApiModelProperty(value="expiredTime")
	private Long expiredTime;
	
	
	@ApiModelProperty(value="EXTERNAL_ENDPOINT")
	private String EXTERNAL_ENDPOINT;
	
	@ApiModelProperty(value="BUCKET_NAME")
	private String BUCKET_NAME;
	
	
	
}
