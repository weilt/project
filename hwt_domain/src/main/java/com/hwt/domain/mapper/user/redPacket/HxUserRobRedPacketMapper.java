package com.hwt.domain.mapper.user.redPacket;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.user.redPacket.HxUserRobRedPacket;

@Mapper
public interface HxUserRobRedPacketMapper {

    int insert(HxUserRobRedPacket record);

    int insertSelective(HxUserRobRedPacket record);

    int updateByPrimaryKeySelective(HxUserRobRedPacket record);

    int updateByPrimaryKey(HxUserRobRedPacket record);

    /**
     * 通过主键查询
     * @param user_id
     * @param red_packet_id
     * @return
     */
    @Select("select  user_id, red_packet_id, accept_amount, trans_num, creat_time "
    		+ "from hx_user_rob_red_packet where  user_id = #{user_id} and red_packet_id = #{red_packet_id}")
	HxUserRobRedPacket selectByByPrimaryKey(@Param("user_id")Long user_id, @Param("red_packet_id")Long red_packet_id);

    /**
     * 根据红包id查询被抢总额
     * @param red_packet_id
     * @return
     */
    @Select("select SUM(accept_amount) from hx_user_rob_red_packet where red_packet_id = #{red_packet_id}")
	BigDecimal acceptTotalAmount(@Param("red_packet_id")Long red_packet_id);
}