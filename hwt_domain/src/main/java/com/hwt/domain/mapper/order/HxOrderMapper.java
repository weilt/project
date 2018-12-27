package com.hwt.domain.mapper.order;

import com.hwt.domain.entity.order.HwOrder;
import com.hwt.domain.entity.order.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface HxOrderMapper {

    //查询路线全部订单
//    @Select("select status from hw_order where status != 0 and cicerone_id =0")
    List<HwOrder> selectQuery(Map<String,Object> map);
    //查询导游全部订单
    //@Select("select status from hw_order where status != 0 and cicerone_id!=0")
    List<HwOrder> selectGuideQuery(Map<String,Object> map);
    //查询退款全部订单
    List<HwOrder> RefundSelectGuideQuery(Map<String,Object> map);

    //按条件查询条数
    Long selectQueryCountByMap(Map<String, Object> map);
    Long selectGuideQueryCountByMap(Map<String, Object> map);
    Long selectQueryCountByMap2(Map<String, Object> map);
    //条件查询线路数据
    List<Map<String, Object>> selectQueryByMap(Map<String, Object> map);
    //条件查询线路(退款)数据
    List<Map<String, Object>> RefundSelectQueryByMap(Map<String, Object> map);
    //条件查询导游数据
    List<Map<String, Object>> selectGuideQueryByMap(Map<String, Object> map);

    //条件查询导游(退款)数据
    List<Map<String, Object>> RefundSelectGuideQueryByMap(Map<String, Object> map);


    /**
     * 通过id查询  查询订单详情
     * @param order_id
     * @return
     */
   // @Select("select a.account,b.* from hx_user a,hw_order b  where 1=1  and a.user_id = b.user_id and order_id = #{order_id}")
    HwOrderVo selectByorder_id(@Param("order_id")Long order_id);

    /**
     * 通过订单id查询  查询产品详情
     * @param order_id
     * @return
     */
    @Select("select order_id, unit_price, travel_line_id, image, `dec` from hx_order_info where order_id = #{order_id}")
    HxOrderInfoVo selectQueryByOrderInfo(@Param("order_id")Long order_id);
    /**
     * 通过订单id查询 查询退款订单
     * @param order_id
     * @return
     */
   // @Select("select * from hw_order_refund where order_id = #{order_id}")
    HwOrderRefundVo selectQueryByOrderRefund(@Param("order_id")Long order_id);

    /**
     * 通过订单id查询用户信息以及保险信息
     * @param order_id
     * @return
     */
    List<HwOrderUserVo> selectQueryListByOrderUser(@Param("order_id")Long order_id);


    /**
     * 同意退款 修改order表
     * @param order_id 订单id
     * @param time_long 当前时间戳
     */
    @Update("update hw_order SET completion_time = #{time_long} ,is_settlement=1,status=5, refundable_time =#{time_long} WHERE order_id=#{order_id}")
    void updateByOrder(@Param("order_id")Long order_id,@Param("time_long") Long time_long);

    /**
     * 拒绝退款 修改order表
     * @param order_id 订单id
     * @param time_long 当前时间戳
     */
//    @Update("update hw_order SET status=8,update_time = #{time_long} WHERE order_id=#{order_id}")
    void updateByrefuseOrder(@Param("order_id")Long order_id,@Param("time_long") Long time_long);


    /**
     * 退款 修改 orderRefund表(同意和拒绝)
     * @param order_id
     * @param refund_sum 退款金额
     * @param i  是否完成退款  0-否 1-是
     * @param refund_dec 退款描述
     * @param refund_time 拒绝退款时间
     * @param operator 操作人
     * @param consent_time 同意退款时间
     * @param contract_money 违约金
     * @param refund_cause   拒绝原因
     *
     */
    void updateByOrderRefund(@Param("order_id")Long order_id,
                             @Param("refund_sum") BigDecimal refund_sum,
                             @Param("i") int i,
                             @Param("refund_dec") String refund_dec,
                             @Param("refund_time")Long refund_time,
                             @Param("operator")String operator,
                             @Param("consent_time")Long consent_time,
                             @Param("contract_money")BigDecimal contract_money,
                             @Param("refund_cause")String refund_cause);


    @Select("select real_name from hx_cicerone_info where user_id = #{id}")
    String selectByCiceroneName(@Param("id") Long id);

    @Select("select company_name from hw_travel_bureau where bureau_id = #{id}")
    String selectByBureauName(@Param("id")Long id);

//    @Select("select trans_num from hx_user_wallet_bill where order_num = #{order_num} and bill_type=2")
    String queryByWallet_bill(@Param("order_num")String order_num);

    //@Select("select trans_num from hx_user_wallet_bill where order_num = #{order_num} and bill_type=4")
    String queryByBill_type(@Param("order_num")String order_num);
	
}
