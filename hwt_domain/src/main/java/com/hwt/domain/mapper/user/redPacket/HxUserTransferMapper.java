package com.hwt.domain.mapper.user.redPacket;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hwt.domain.entity.user.redPacket.HxUserTransfer;
import com.hwt.domain.entity.user.redPacket.vo.HxUserTransferVo;

@Mapper
public interface HxUserTransferMapper {

    int deleteByPrimaryKey(Long transfer_id);

    int insert(HxUserTransfer record);

    int insertSelective(HxUserTransfer record);


    HxUserTransfer selectByPrimaryKey(Long transfer_id);


    int updateByPrimaryKeySelective(HxUserTransfer record);

    int updateByPrimaryKey(HxUserTransfer record);

    
    /**
     * 返回主键添加
     * @param hxUserTransfer
     */
	void insertBackId(HxUserTransfer hxUserTransfer);

	
	/**
	 * 通过id查询
	 * @param transfer_id
	 * @return
	 */
	@Select(" select transfer_id, user_id, accept_user_id, send_amount, title, "
			+ "content,transfer_type, creat_time, linqu_time, is_accept,refuse_time, is_pay, pay_time from hx_user_transfer where transfer_id = #{transfer_id}")
	HxUserTransferVo queryHxUserTransferVoById(@Param("transfer_id")Long transfer_id);
}