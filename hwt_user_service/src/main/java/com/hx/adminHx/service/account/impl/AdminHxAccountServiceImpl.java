package com.hx.adminHx.service.account.impl;


import com.hwt.domain.entity.user.wallet.Vo.HxUserWallBillExcelVo;
import com.hwt.domain.mapper.user.wallet.HxUserWalletBillMapper;
import com.hx.adminHx.service.account.AdminHxAccountService;
import com.hx.adminHx.service.vo.HxUserWallAccountPageVo;
import com.hx.core.logs.annotation.Log;
import com.hx.core.utils.ObjectHelper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminHxAccountServiceImpl implements AdminHxAccountService {
    @Autowired
    private HxUserWalletBillMapper hxUserWalletBillMapper;

    @Override
    public HxUserWallAccountPageVo selectQuery(Map<String, Object> map) {
        //订单数量
        Integer orderNum = 0;
        //充值数量
        Integer rechargeNum = 0;
        //提现数量
        Integer drawingsNum = 0;
        //查询订单数量
        Integer orderlist = hxUserWalletBillMapper.selectByOrderCount();
        Integer rechargelist = hxUserWalletBillMapper.selectByRechargeCount();
        Integer drawingslist = hxUserWalletBillMapper.selectByDrawingsCount();
        if (!ObjectHelper.isEmpty(orderlist)) {
            orderNum = orderlist;
        }
        if (!ObjectHelper.isEmpty(rechargelist)) {
            rechargeNum = rechargelist;
        }
        if (!ObjectHelper.isEmpty(drawingslist)) {
            drawingsNum = drawingslist;
        }

        Integer cont = null;
        List<Map<String, Object>> list = null;
        //查询订单数据
        if (map.get("bill_type").equals(0)) {
            list = hxUserWalletBillMapper.selectByOrder(map);
            cont = orderNum;
        }

        //查询充值的数据
        if (map.get("bill_type").equals(1)) {
            list = hxUserWalletBillMapper.selectByAccount(map);
            cont = rechargeNum;
        }
        //查询提现的数据
        if (map.get("bill_type").equals(5)) {
            list = hxUserWalletBillMapper.selectByAccount(map);
            cont = drawingsNum;
        }

        HxUserWallAccountPageVo hxUserWallAccountPageVo = new HxUserWallAccountPageVo();
        hxUserWallAccountPageVo.setOrderNum(orderNum);
        hxUserWallAccountPageVo.setCount(cont);
        hxUserWallAccountPageVo.setDrawingsNum(drawingsNum);
        hxUserWallAccountPageVo.setRechargeNum(rechargeNum);
        hxUserWallAccountPageVo.setDataList(list);
        return hxUserWallAccountPageVo;
    }

    // date要转换的long类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    @Override
    @Transactional
    public void batchReconciliation(List<String> list, String handlers,Integer bill_type) {
        Integer count = null;
        //当前时间戳
        Date date = new Date();
        long time_long = dateToLong(date);
        for (String trans_num : list) {

            if (bill_type == 0){
             count = hxUserWalletBillMapper.selectStatus(trans_num);//未对账的订单
            }else {
             count = hxUserWalletBillMapper.selectStatus2(trans_num);//未对账的充值提现
            }

            if (count == 0) {
                throw new RuntimeException("你选择的账单中 有已对账的账单");
            } else {

                //对账成功的数据
                if (bill_type == 0){
                    //订单数据
                    hxUserWalletBillMapper.updateByStatus(trans_num, handlers, time_long);
                }else if (bill_type == 1){
                    //充值
                    hxUserWalletBillMapper.updateByStatus2(trans_num, handlers, time_long);
                }else if (bill_type == 5){
                    //查询这个订单的操作金额
                    Map<String,Object> map = hxUserWalletBillMapper.selectByOperation_amount(trans_num);
                    String operation_amount = map.get("operation_amount").toString();
                    long l = Long.parseLong(operation_amount);
                    long name_id = Long.parseLong(map.get("name_id").toString());
                    int name_type = Integer.parseInt(map.get("name_type").toString());
                    hxUserWalletBillMapper.selectByGet_payment(new BigDecimal(l),name_id,name_type);
                    //提现
                    hxUserWalletBillMapper.updateByStatus3(trans_num, handlers, time_long);
                }else {
                    throw  new RuntimeException("bill_type参数不正确");
                }


            }
        }
    }



    @Override
    public void
    excel(List<String> list, Integer bill_type, HttpServletResponse response) {
            // 第一步，创建一个webbook，对应一个Excel文件
            XSSFWorkbook wb = new XSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            XSSFSheet sheet = wb.createSheet("资金管理");
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            XSSFRow row = sheet.createRow((int) 0);
        List<HxUserWallBillExcelVo> hxUserWallBillExcelVos = null;
        //当前时间戳
            Date date = new Date();
            long time_long = dateToLong(date);
            if (bill_type == 0){
                row.createCell(0).setCellValue("订单编号");
                row.createCell(1).setCellValue("订单金额");
                row.createCell(2).setCellValue("支付方式");
                row.createCell(3).setCellValue("支付时间");
                row.createCell(4).setCellValue("对账人员");
                row.createCell(5).setCellValue("对账时间");
                row.createCell(6).setCellValue("状态");
                sheet.setColumnWidth(0, 256*30);
                sheet.setColumnWidth(1, 80*30);
                sheet.setColumnWidth(3, 256*30);
                sheet.setColumnWidth(4, 150*30);
                sheet.setColumnWidth(5, 256*30);
        //查询数据
        for (String order_num : list) {
             hxUserWallBillExcelVos = hxUserWalletBillMapper.selectByExcel(order_num,null);

                for (int i = 0; i < hxUserWallBillExcelVos.size(); i++) {
                    //获取最后一行
                    int lastRowNum = sheet.getLastRowNum();
                    XSSFRow hssfRow = sheet.createRow(lastRowNum + 1);
                    //根据i 获取对象
                    HxUserWallBillExcelVo userWallBillExcelVo = hxUserWallBillExcelVos.get(i);
                    hssfRow.createCell(0).setCellValue(userWallBillExcelVo.getOrder_num());
                    hssfRow.createCell(1).setCellValue(userWallBillExcelVo.getOperation_amount().doubleValue());
                    String payWay = "";
                    if (userWallBillExcelVo.getSource() ==1){
                        payWay = "支付宝";
                    }
                    if (userWallBillExcelVo.getSource() ==2){
                        payWay = "微信";
                    }
                    if (userWallBillExcelVo.getSource() ==3){
                        payWay = "钱包";
                    }
                    hssfRow.createCell(2).setCellValue(payWay);
                    hssfRow.createCell(3).setCellValue(getLongAsDateStr(userWallBillExcelVo.getCreate_time()));
                    hssfRow.createCell(4).setCellValue(userWallBillExcelVo.getHandlers());

                    if (userWallBillExcelVo.getAccount_time() == null){
                        hssfRow.createCell(5).setCellValue("N/A");
                    }else {
                        hssfRow.createCell(5).setCellValue(getLongAsDateStr(userWallBillExcelVo.getAccount_time()));
                    }
                    if (userWallBillExcelVo.getStatus() == 0 || userWallBillExcelVo.getStatus() == null){
                        hssfRow.createCell(6).setCellValue("未对账");
                    }else {
                        hssfRow.createCell(6).setCellValue("已对账");
                    }

                } }
            }
            if (bill_type == 1){
                row.createCell(0).setCellValue("交易单号");
                row.createCell(1).setCellValue("交易金额");
                row.createCell(2).setCellValue("支付方式");
                row.createCell(3).setCellValue("支付时间");
                row.createCell(4).setCellValue("对账人员");
                row.createCell(5).setCellValue("对账时间");
                row.createCell(6).setCellValue("状态");
                    sheet.setColumnWidth(0, 256*30);
                    sheet.setColumnWidth(1, 80*30);
                    sheet.setColumnWidth(3, 256*30);
                    sheet.setColumnWidth(4, 150*30);
                    sheet.setColumnWidth(5, 256*30);
                for (String trans_num : list) {
                    hxUserWallBillExcelVos = hxUserWalletBillMapper.selectByExcel(null,trans_num);

                for (int i = 0; i < hxUserWallBillExcelVos.size(); i++) {
                    int lastRowNum = sheet.getLastRowNum();
                    XSSFRow hssfRow = sheet.createRow(lastRowNum + 1);
                    //根据i 获取对象
                    HxUserWallBillExcelVo userWallBillExcelVo = hxUserWallBillExcelVos.get(i);
                    hssfRow.createCell(0).setCellValue(userWallBillExcelVo.getTrans_num());
                    hssfRow.createCell(1).setCellValue(userWallBillExcelVo.getOperation_amount().doubleValue());
                    String payWay = "";
                    if (userWallBillExcelVo.getSource() ==1){
                        payWay = "支付宝";
                    }
                    if (userWallBillExcelVo.getSource() ==2){
                        payWay = "微信";
                    }
                    if (userWallBillExcelVo.getSource() ==3){
                        payWay = "银行卡";
                    }
                    hssfRow.createCell(2).setCellValue(payWay);
                    hssfRow.createCell(3).setCellValue(getLongAsDateStr(userWallBillExcelVo.getCreate_time()));
                    hssfRow.createCell(4).setCellValue(userWallBillExcelVo.getHandlers());

                    if (userWallBillExcelVo.getAccount_time() == null){
                        hssfRow.createCell(5).setCellValue("N/A");
                    }else {
                        hssfRow.createCell(5).setCellValue(getLongAsDateStr(userWallBillExcelVo.getAccount_time()));
                    }
                    if (userWallBillExcelVo.getStatus() == 0 || userWallBillExcelVo.getStatus() == null){
                        hssfRow.createCell(6).setCellValue("未对账");
                    }else {
                        hssfRow.createCell(6).setCellValue("已对账");
                    }

                }
                }
            }
            if (bill_type == 5){
                row.createCell(0).setCellValue("交易单号");
                row.createCell(1).setCellValue("交易金额");
                row.createCell(2).setCellValue("提现方式");
                row.createCell(3).setCellValue("申请时间");
                row.createCell(4).setCellValue("对账人员");
                row.createCell(5).setCellValue("对账时间");
                row.createCell(6).setCellValue("状态");
                sheet.setColumnWidth(0, 256*30);
                sheet.setColumnWidth(1, 80*30);
                sheet.setColumnWidth(3, 256*30);
                sheet.setColumnWidth(4, 150*30);
                sheet.setColumnWidth(5, 256*30);
                for (String trans_num : list) {
                    hxUserWallBillExcelVos = hxUserWalletBillMapper.selectByExcel(null,trans_num);

                for (int i = 0; i < hxUserWallBillExcelVos.size(); i++) {
                    int lastRowNum = sheet.getLastRowNum();
                    XSSFRow hssfRow = sheet.createRow(lastRowNum + 1);
                    //根据i 获取对象
                    HxUserWallBillExcelVo userWallBillExcelVo = hxUserWallBillExcelVos.get(i);
                    hssfRow.createCell(0).setCellValue(userWallBillExcelVo.getTrans_num());
                    hssfRow.createCell(1).setCellValue(userWallBillExcelVo.getOperation_amount().doubleValue());
                    String payWay = "";
                    if (userWallBillExcelVo.getSource() ==1){
                        payWay = "支付宝";
                    }
                    if (userWallBillExcelVo.getSource() ==2){
                        payWay = "微信";
                    }
                    if (userWallBillExcelVo.getSource() ==3){
                        payWay = "银行卡";
                    }
                    hssfRow.createCell(2).setCellValue(payWay);
                    hssfRow.createCell(3).setCellValue(getLongAsDateStr(userWallBillExcelVo.getCreate_time()));
                    hssfRow.createCell(4).setCellValue(userWallBillExcelVo.getHandlers());

                    if (userWallBillExcelVo.getAccount_time() == null){
                        hssfRow.createCell(5).setCellValue("N/A");
                    }else {
                        hssfRow.createCell(5).setCellValue(getLongAsDateStr(userWallBillExcelVo.getAccount_time()));
                    }
                    if (userWallBillExcelVo.getStatus() == 0 || userWallBillExcelVo.getStatus() == null){
                        hssfRow.createCell(6).setCellValue("未对账");
                    }else {
                        hssfRow.createCell(6).setCellValue("已对账");
                    }

                }
                }
            }

        //application/vnd.ms-excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+UUID.randomUUID().toString()+".xlsx");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            wb.write(out);
            //out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    //时间戳转string
    private String getLongAsDateStr(Long timeMillions){

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sim.format(new Date(timeMillions));
        return format;
    }
}
