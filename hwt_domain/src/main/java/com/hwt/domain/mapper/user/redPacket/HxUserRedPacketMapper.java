package com.hwt.domain.mapper.user.redPacket;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.hwt.domain.entity.user.redPacket.HxUserRedPacket;
import com.hwt.domain.entity.user.redPacket.vo.HxUserRedPacketVo;
import com.hwt.domain.entity.user.redPacket.vo.UserRobRedPacketVo;

@Mapper
public interface HxUserRedPacketMapper {

    int deleteByPrimaryKey(Long red_packet_id);

    int insert(HxUserRedPacket record);

    int insertSelective(HxUserRedPacket record);

    HxUserRedPacket selectByPrimaryKey(Long red_packet_id);

    int updateByPrimaryKeySelective(HxUserRedPacket record);

    int updateByPrimaryKey(HxUserRedPacket record);

    
    /**
     * 返回主键添加
     * @param hxUserRedPacket
     */
	void insertBackId(HxUserRedPacket hxUserRedPacket);

	/**
	 * 查询红包详情
	 * @param red_packet_id
	 * @return
	 */
	HxUserRedPacketVo queryInfoById(Long red_packet_id);

	/**
	 * 领完时间填入
	 * @param currentTimeMillis
	 * @param red_packet_id 
	 */
	@Update("update hx_user_red_packet set linwan_time = #{currentTimeMillis} where red_packet_id = #{red_packet_id}")
	void linwan_time(@Param("currentTimeMillis")long currentTimeMillis, @Param("red_packet_id")Long red_packet_id);
}