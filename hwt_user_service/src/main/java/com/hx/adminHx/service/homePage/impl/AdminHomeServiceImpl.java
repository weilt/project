package com.hx.adminHx.service.homePage.impl;

import com.hwt.domain.mapper.admin.AdminHomeMapper;
import com.hx.adminHx.service.homePage.AdminHomeSesrvice;
import com.hx.adminHx.service.vo.AdminHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class AdminHomeServiceImpl implements AdminHomeSesrvice {

    @Autowired
    private AdminHomeMapper adminHomeMapper;

    @Override
    public AdminHome selectByAll() throws ParseException {


        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        //当前日期的 0点(时间戳)
        Long present = c1.getTime().getTime();
        //86400000 一天的时间戳 twenty_three 23:59:59
        //当天的23:59:59
        Long twenty_three_ =present + ((1000l*60l*60l*24l)-1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转date
        Date twenty_three = new Date(twenty_three_);
        //今日新增
        Integer today = adminHomeMapper.selectToday(c1.getTime(),twenty_three);
        //昨日新增
        Integer yesterday = adminHomeMapper.selectToday(new Date(c1.getTime().getTime()-86400000l),new Date(c1.getTime().getTime()-1000l));
        //本月新增
        Date byMonth = initDateByMonth();
        Long lastDay = lastDay();
        Integer month = adminHomeMapper.selectToday(byMonth,new Date(lastDay));
        //用户总数
        Integer userCount = adminHomeMapper.userCount();
        //商家入驻审核 数量
        // 显示数量为旅行社与导游待处理数量之和
        Integer bureau =adminHomeMapper.selectBureau();
        Integer info =adminHomeMapper.selectInfo();
        Integer merchantCount = bureau+info;

        //退款申请 数量
        //显示数量为线路退款待处理的数量
        Integer refundCount = adminHomeMapper.selectRefund();

        //小视频审核
        // 显示是待审核数量
        Integer videoCount = adminHomeMapper.selectVideo();

        //提现申请
        //显示数量为提现未对账数量
        Integer depositCount = adminHomeMapper.selectAccount();

        //今日导游订单数
        Integer orderGuideCount = adminHomeMapper.selectGuideCount(c1.getTime().getTime(),twenty_three_);

        //今日线路单数
        Integer orderCircuitCount = adminHomeMapper.selectCircuitCount(c1.getTime().getTime(),twenty_three_);

        //平台今日订单数
        //支付成功的订单数量（导游+线路）
        Integer orderCount = orderCircuitCount + orderGuideCount;

        //平台今日销售总额
        //今日订单数量入账金额总和
        Double todayAmount = 0.00;
        Double today2 = adminHomeMapper.selectOrderSum(c1.getTime().getTime(),twenty_three_);
        if (today2 != null && today2 != 0.00){
            todayAmount = today2;
        }

        //平台昨日销售总额
        //昨天订单数量入账金额总和
        Double yesterdayAmount = 0.00;
        Double yesterday2 = adminHomeMapper.selectOrderSum(c1.getTime().getTime()-86400000l,c1.getTime().getTime()-1000l);
        if (yesterday2 != null && yesterday2 != 0.00){
            yesterdayAmount = yesterday2;
        }

        //近7天订单入账总金额   604800000    7天的毫秒值
        Double hebdomadAmount =0.00;
        Double hebdomad2 = adminHomeMapper.selectOrderSum(c1.getTime().getTime()-604800000l,c1.getTime().getTime()-1000l);
        if (hebdomad2 != null && hebdomad2 != 0.00){
            hebdomadAmount = hebdomad2;
        }

        AdminHome adminHome = new AdminHome();
        adminHome.setToday(today);
        adminHome.setYesterday(yesterday);
        adminHome.setMonth(month);
        adminHome.setUserCount(userCount);
        adminHome.setMerchantCount(merchantCount);
        adminHome.setRefundCount(refundCount);
        adminHome.setVideoCount(videoCount);
        adminHome.setDepositCount(depositCount);
        adminHome.setOrderCount(orderCount);
        adminHome.setOrderGuideCount(orderGuideCount);
        adminHome.setOrderCircuitCount(orderCircuitCount);
        adminHome.setTodayAmount(todayAmount);
        adminHome.setYesterdayAmount(yesterdayAmount);
        adminHome.setHebdomadAmount(hebdomadAmount);

        return adminHome;
    }

    // date要转换的long类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
    /**
     * 获得当月1号零时零分零秒
     * @return
     */
    public Date initDateByMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    //当前月最后一天的毫秒值 的23:59:59
    public Long lastDay() {
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        // String last = format.format(ca.getTime());
        // System.out.println("===============last:"+last);
        return ca.getTime().getTime();
    }
}

