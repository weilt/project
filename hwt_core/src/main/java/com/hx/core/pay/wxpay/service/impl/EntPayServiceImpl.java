package com.hx.core.pay.wxpay.service.impl;


import com.hx.core.pay.wxpay.bean.entpay.*;
import com.hx.core.pay.wxpay.bean.request.WxPayDefaultRequest;
import com.hx.core.pay.wxpay.bean.result.BaseWxPayResult;
import com.hx.core.pay.wxpay.exception.WxPayException;

import com.hx.core.pay.wxpay.service.EntPayService;
import com.hx.core.pay.wxpay.service.WxPayService;
import com.hx.core.pay.wxpay.util.SignUtils;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.security.Security;

/**
 * <pre>
 *  Created by BinaryWang on 2017/12/19.
 * </pre>
 *
 *
 */
public class EntPayServiceImpl implements EntPayService {
  private WxPayService payService;

  public EntPayServiceImpl(WxPayService payService) {
    this.payService = payService;
  }

  @Override
  public EntPayResult entPay(EntPayRequest request) throws WxPayException {
    request.checkAndSign(this.payService.getConfig());
    String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/promotion/transfers";

    String responseContent = this.payService.post(url, request.toXML(), true);
    EntPayResult result = BaseWxPayResult.fromXML(responseContent, EntPayResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result;
  }

  @Override
  public EntPayQueryResult queryEntPay(String partnerTradeNo) throws WxPayException {
    EntPayQueryRequest request = new EntPayQueryRequest();
    request.setPartnerTradeNo(partnerTradeNo);
    request.checkAndSign(this.payService.getConfig());

    String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/gettransferinfo";
    String responseContent = this.payService.post(url, request.toXML(), true);
    EntPayQueryResult result = BaseWxPayResult.fromXML(responseContent, EntPayQueryResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result;
  }

  @Override
  public String getPublicKey() throws WxPayException {
    WxPayDefaultRequest request = new WxPayDefaultRequest();
    request.setMchId(this.payService.getConfig().getMchId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));
    request.setSign(SignUtils.createSign(request, null, this.payService.getConfig().getMchKey(),
      true));

    String url = "https://fraud.mch.weixin.qq.com/risk/getpublickey";
    String responseContent = this.payService.post(url, request.toXML(), true);
    GetPublicKeyResult result = BaseWxPayResult.fromXML(responseContent, GetPublicKeyResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result.getPubKey();
  }

  @Override
  public EntPayBankResult payBank(EntPayBankRequest request) throws WxPayException {
    File publicKeyFile = this.buildPublicKeyFile();
    request.setEncBankNo(this.encryptRSA(publicKeyFile, request.getEncBankNo()));
    request.setEncTrueName(this.encryptRSA(publicKeyFile, request.getEncTrueName()));
    publicKeyFile.deleteOnExit();

    request.checkAndSign(this.payService.getConfig());

    String url = this.payService.getPayBaseUrl() + "/mmpaysptrans/pay_bank";
    String responseContent = this.payService.post(url, request.toXML(), true);
    EntPayBankResult result = BaseWxPayResult.fromXML(responseContent, EntPayBankResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result;
  }

  @Override
  public EntPayBankQueryResult queryPayBank(String partnerTradeNo) throws WxPayException {
    EntPayBankQueryRequest request = new EntPayBankQueryRequest();
    request.setPartnerTradeNo(partnerTradeNo);
    request.checkAndSign(this.payService.getConfig());

    String url = this.payService.getPayBaseUrl() + "/mmpaysptrans/query_bank";
    String responseContent = this.payService.post(url, request.toXML(), true);
    EntPayBankQueryResult result = BaseWxPayResult.fromXML(responseContent, EntPayBankQueryResult.class);
    result.checkResult(this.payService, request.getSignType(), true);
    return result;
  }

  private String encryptRSA(File publicKeyFile, String srcString) throws WxPayException {
    try {
      Security.addProvider(new BouncyCastleProvider());
      Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
      try (PEMParser reader = new PEMParser(new FileReader(publicKeyFile))) {
        final PublicKey publicKey = new JcaPEMKeyConverter().setProvider("BC")
          .getPublicKey((SubjectPublicKeyInfo) reader.readObject());

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypt = cipher.doFinal(srcString.getBytes());
        return Base64.encodeBase64String(encrypt);
      }
    } catch (Exception e) {
      throw new WxPayException("加密出错", e);
    }
  }

  private File buildPublicKeyFile() throws WxPayException {
    try {
      String publicKeyStr = this.getPublicKey();
      Path tmpFile = Files.createTempFile("payToBank", ".pem");
      Files.write(tmpFile, publicKeyStr.getBytes());
      return tmpFile.toFile();
    } catch (Exception e) {
      throw new WxPayException("生成加密公钥文件时发生异常", e);
    }
  }


}