package com.hwt.domain.mapper.price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hwt.domain.entity.price.HwPrice;
import com.hwt.domain.entity.price.vo.HwPriceVo;

@Mapper
public interface HwPriceMapper {


    int insert(HwPrice record);

    int insertSelective(HwPrice record);

    int updateByPrimaryKeySelective(HwPrice record);

    int updateByPrimaryKey(HwPrice record);

	/**
	 * 查询时间段导游的价格
	 * @param user_id
	 * @param start
	 * @param end
	 * @return
	 */
	@Select("select  adult_price, child_price, company, time_str, time, status, person_num from hw_price where time>=#{start} and time<=#{end} and name_id = #{user_id} and name_type = 2")
	List<HwPriceVo> cicerone_price_mouth_query(@Param("user_id")Long user_id, @Param("start")Long start, @Param("end")Long end);

	/**
	 * 查询所有 导游
	 * @param user_id
	 * @return
	 */
	@Select("select  adult_price, child_price, company, time_str, time, status, person_num from hw_price "
			+ "where name_id = #{user_id} and name_type = 2")
	List<HwPriceVo> cicerone_price_query_all(@Param("user_id")Long user_id);

	/**
	 * 根据时间集查询价格  导游
	 * @param set
	 * @param user_id
	 * @return
	 */
	List<HwPrice> selectCiceronePriceByTimesAndUserId(@Param("set")Set<Long> set, @Param("user_id")Long user_id);

	/**
	 * 多条添加
	 * @param inset
	 */
	void insertList(List<HwPrice> list);

	/**
	 * 多条修改 导游
	 * @param update
	 * @param currentTimeMillis
	 * @param user_id
	 * @param child_price 
	 * @param adult_price 
	 */
	void updateCiceroneList(@Param("list")List<Long> update, @Param("update_time")long currentTimeMillis, 
			@Param("user_id")Long user_id, 
			@Param("adult_price")BigDecimal adult_price, @Param("child_price")BigDecimal child_price);

	/**
	 * 根据时间集状态查询价格  导游
	 * @param set
	 * @param user_id
	 * @return
	 */
	List<HwPrice> selectCiceronePriceByTimesAndUserIdAndStatus(@Param("set")Set<Long> set, @Param("user_id")Long user_id, @Param("status")int i);

	/**
	 * 根据时间集状态删除价格  导游
	 * @param set
	 * @param user_id
	 */
	void deleteCiceronePriceBySetAndUserId(@Param("set")Set<Long> set, @Param("user_id")Long user_id);

	/**
	 * 通过开始时间和，结束时间查询价格 导游
	 * @param start_time
	 * @param end_time
	 * @param user_id
	 * @return
	 */
	@Select("select name_id, name_type, time, adult_price, child_price, company, time_str, status, person_num, create_time, update_time from hw_price "
			+ " where name_id = #{user_id} and name_type = 2 and time >=#{start_time} and time <= #{end_time}")
	List<HwPrice> selectCiceronePriceByStartTimeAndEndTime(@Param("start_time")Long start_time, @Param("end_time")Long end_time,
			@Param("user_id")Long user_id);

	/**
	 * 修改导游的工作状态
	 * @param start_time
	 * @param end_time
	 * @param user_id
	 * @param status 	0-可被下单 1-已被下单
	 */
	@Update("update hw_price set status = #{status} where name_id = #{user_id} and name_type = 2 and time >=#{start_time} and time <= #{end_time}")
	void updateCiceroneWorkPriceByStartTimeAndEndTime(@Param("start_time")Long start_time, @Param("end_time")Long end_time,
			@Param("user_id")Long user_id, @Param("status")int status);
	
}