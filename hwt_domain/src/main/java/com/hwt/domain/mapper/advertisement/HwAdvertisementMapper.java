package com.hwt.domain.mapper.advertisement;

import com.hwt.domain.entity.advertisement.HwAdvertisement;
import com.hwt.domain.entity.advertisement.vo.HwAdvertisementVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface HwAdvertisementMapper {

    int deleteByPrimaryKey(Long ad_id);

    int insert(HwAdvertisement record);

    int insertSelective(HwAdvertisement record);


    HwAdvertisement selectByPrimaryKey(Long ad_id);


    int updateByPrimaryKeySelective(HwAdvertisement record);

    int updateByPrimaryKey(HwAdvertisement record);

    /**
     * 通过条件查询总条数
     * @param map
     * @return
     */
	long queryCountAdminByMap(Map<String, Object> map);

	/**
	 * 查询数据给后台
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryAdminByMap(Map<String, Object> map);

	/**
	 * 上线
	 * @param ad_id
	 */
	@Update("update hw_advertisement set is_online = 1 where is_online = 0 and ad_id = #{ad_id}")
	void online(@Param("ad_id")Long ad_id);

	/**
	 * 下线
	 * @param ad_id
	 */
	@Update("update hw_advertisement set is_online = 0 where is_online = 1 and ad_id = #{ad_id}")
	void not_online(@Param("ad_id")Long ad_id);

	/**
	 * 隐藏
	 * @param ad_id
	 */
	@Update("update hw_advertisement set is_hide = 1 where is_hide = 0 and ad_id = #{ad_id}")
	void is_hide(@Param("ad_id")Long ad_id);

	/**
	 * 恢复
	 * @param ad_id
	 */
	@Update("update hw_advertisement set is_hide = 0 where is_hide = 1 and ad_id = #{ad_id}")
	void is_not_hide(@Param("ad_id")Long ad_id);

	/**
	 * 通过位置id查询
	 * @param ad_position
	 * @return
	 */
	@Select("select ad_id,ad_type,title,real_id,ad_url,image from hw_advertisement where ad_position = #{ad_position} and is_hide = 0 and is_online = 1 ")
			//+ "and (((start_time = null) or(start_time>=#{time})) and ((end_time = null) or(end_time<#{time})))")
	List<HwAdvertisementVo> selectByPosition(@Param("ad_position")Long ad_position,@Param("time")Long time);

	/**
	 * 广告点击次数加1
	 * @param ad_id
	 */
	@Update("update hw_advertisement set click_num = (click_num + 1) where ad_id = #{ad_id}")
	void lookAdd(Long ad_id);
    
    
}