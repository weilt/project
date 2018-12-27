package com.hx.core.pay.wxpay.bean;

import com.hx.core.pay.wxpay.json.WxGsonBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxAccessToken implements Serializable {
  private static final long serialVersionUID = 8709719312922168909L;

  private String accessToken;

  private int expiresIn = -1;

  public static WxAccessToken fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
  }

}
