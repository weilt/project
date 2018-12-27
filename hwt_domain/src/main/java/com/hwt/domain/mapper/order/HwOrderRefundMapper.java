package com.hwt.domain.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.order.HwOrderRefund;
import com.hwt.domain.entity.order.vo.HwOrderRefundVo;

@Mapper
public interface HwOrderRefundMapper {


    int deleteByPrimaryKey(Long order_id);

    int insert(HwOrderRefund record);

    int insertSelective(HwOrderRefund record);


    HwOrderRefund selectByPrimaryKey(Long order_id);


    int updateByPrimaryKeySelective(HwOrderRefund record);


    int updateByPrimaryKey(HwOrderRefund record);

	/**
	 * 通过订单id查询
	 * @param order_id
	 * @return
	 */
    @Select("select  order_id, refund_sum, is_complete, refund_dec, user_remarks, business_remarks,refund_time_back, "
    		+ "refund_cause,operator, consent_time, contract_money	 from hw_order_refund where order_id = #{order_id}")
	HwOrderRefundVo query_by_order_id(Long order_id);
}