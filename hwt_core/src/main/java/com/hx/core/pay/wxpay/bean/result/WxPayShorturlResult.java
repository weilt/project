package com.hx.core.pay.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 转换短链接结果对象类
 * Created by2017-3-27.
 * </pre>
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayShorturlResult extends BaseWxPayResult {
  /**
   * <pre>
   * URL链接
   * short_url
   * 是
   * String(64)
   * weixin：//wxpay/s/XXXXXX
   * 转换后的URL
   * </pre>
   */
  @XStreamAlias("short_url")
  private String shortUrl;

}
