package com.hwt.domain.mapper.user.wallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hwt.domain.entity.order.HwOrder;
import com.hwt.domain.entity.user.wallet.Vo.HxUserWallBillExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.user.wallet.HxUserWalletBill;
import com.hwt.domain.entity.user.wallet.Vo.HxUserWalletBillVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HxUserWalletBillMapper {

    int deleteByPrimaryKey(Long bill_id);

    int insert(HxUserWalletBill record);

    int insertSelective(HxUserWalletBill record);



    HxUserWalletBill selectByPrimaryKey(Long bill_id);

    int updateByPrimaryKeySelective(HxUserWalletBill record);

    int updateByPrimaryKey(HxUserWalletBill record);

	/**
	 * 查询账单
	 * @param map
	 * @return
	 */
	List<HxUserWalletBillVo> query_bill_app(Map<String, Object> map);

	/**
	 * 旅行社账单查询
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> query_bill_bureau(Map<String, Object> map);

	/**
	 * 查询旅行社账条数
	 * @param map
	 * @return
	 */
	long query_bill_bureau_count(Map<String, Object> map);


	/**
	 * 通过order_num查询账单
	 * @param order_num
	 * @return
	 */
	@Select("select  bill_id, name_id, name_type, bill_dec, bill_title, bill_type, order_num, trans_num, "
    +"operation_amount, balance, is_wallet, source, remarks, is_get, create_time, stare_time, "
    +"end_time, flow_num, is_success, failed_reason, status, account_time, admin_user_id, "
    +"username, bank_name, card_num, account_success from hx_user_wallet_bill where order_num = #{order_num}")
	HxUserWalletBill query_by_order_num(String order_num);

	/**
	 * 根据账单对象修改
	 * @param hxUserWalletBill
	 * @param time_long
	 */
	@Update("update hx_user_wallet_bill set is_success = 1,end_time = #{date}where order_num = #{order_num}")
	void updateByPrimary(HxUserWalletBill hxUserWalletBill, Long time_long);


	/**
	 * 通过交易单号查询
	 * @return
	 */
	@Select("select  bill_id, name_id, name_type, bill_dec, bill_title, bill_type, order_num, trans_num, "
    +"operation_amount, balance, is_wallet, source, remarks, is_get, create_time, stare_time, "
    +"end_time, flow_num, is_success, failed_reason, status, account_time, admin_user_id, "
    +"username, bank_name, card_num, account_success  from hx_user_wallet_bill where trans_num = #{trans_num}")
	HxUserWalletBill selectByTrans_num(@Param("trans_num")String trans_num);

	/**
	 * 通过订单编号查询 订单
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectByOrder(Map<String, Object> map);

	/**
	 * 通过交易单号 或者流水号查询 充值 提现
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectByAccount(Map<String, Object> map);

	//查询满足条件的订单总数
	@Select("SELECT count(0) FROM (select count(0) from hw_order a LEFT JOIN hx_user_wallet_bill b ON a.order_num = b.order_num where b.status=0 AND a.paymen_type !=3 GROUP BY a.order_num) a WHERE 1=1")
	Integer selectByOrderCount();

	//查询满足条件的充值总数
	@Select("SELECT count(0) FROM hx_user_wallet_bill WHERE bill_type = 1 and status=0 and is_success = 1")
	Integer selectByRechargeCount();

	//查询满足条件的提现总数
	@Select("SELECT count(0) FROM hx_user_wallet_bill WHERE bill_type = 5 and status=0")
	Integer selectByDrawingsCount();

	//查询未对账的订单的数量
	@Select("SELECT count(0) FROM hx_user_wallet_bill WHERE `status` = 0 and order_num = #{trans_num}")
	Integer selectStatus(@Param("trans_num") String trans_num);

	//批量对账订单成功
	@Update("UPDATE hx_user_wallet_bill SET handlers = #{handlers} ,account_time = #{time_long},`status`= 1 WHERE order_num = #{trans_num}")
	void updateByStatus(@Param("trans_num")String trans_num,@Param("handlers")String handlers , @Param("time_long")Long time_long);

	List<HxUserWallBillExcelVo> selectByExcel(@Param("order_num")String order_num,@Param("trans_num") String trans_num);

	//批量对账充值提现成功
	@Update("UPDATE hx_user_wallet_bill SET handlers = #{handlers} ,account_time = #{time_long},`status`= 1 WHERE trans_num = #{trans_num}")
	void updateByStatus2(@Param("trans_num")String trans_num,@Param("handlers")String handlers , @Param("time_long")Long time_long);

	//查询未对账的充值提现的数量
	@Select("SELECT count(0) FROM hx_user_wallet_bill WHERE `status` = 0 and trans_num = #{trans_num}")
	Integer selectStatus2(@Param("trans_num") String trans_num);

	//批量对账提现成功
	@Update("UPDATE hx_user_wallet_bill SET is_success=1,end_time = #{time_long}, handlers = #{handlers} ,account_time = #{time_long},`status`= 1 WHERE trans_num = #{trans_num}")
	void updateByStatus3(@Param("trans_num")String trans_num, @Param("handlers")String handlers, @Param("time_long")long time_long);

	@Select("select operation_amount,name_id,name_type from hx_user_wallet_bill where trans_num = #{trans_num}")
	Map<String,Object> selectByOperation_amount(@Param("trans_num")String trans_num);

	@Update("UPDATE hx_user_wallet SET get_payment = #{bigDecimal} WHERE name_id = #{name_id} and name_type = #{name_type}")
	void selectByGet_payment(@Param("bigDecimal")BigDecimal bigDecimal, @Param("name_id")long name_id,@Param("name_type") int name_type);
}