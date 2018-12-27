package com.hx.advertisement.service.adminHx;

import java.util.Map;

import com.hwt.domain.entity.advertisement.HwAdvertisement;
import com.hwt.domain.entity.information.vo.HwInformationCommentAdminVo;
import com.hwt.domain.entity.mg.information.HwInformation;
import com.hx.core.page.asyn.PageResult;

/**
 * 广告管理  后台
 * @author Administrator
 *
 */
public interface AdminAdvertisementService {

	/**
	 * 按条件查询
	 * @param map
	 * @return
	 */
	PageResult<Map<String, Object>> queryByMap(Map<String, Object> map);

	/**
	 * 添加
	 * @param advertisement
	 */
	void insert(HwAdvertisement advertisement);

	/**
	 * 广告属性查询
	 * @param map
	 */
	Map<String, Object> queryAdvertisementAttribute(Map<String, Object> map);

	/**
	 * 广告信息查询
	 * @param ad_id
	 * @return
	 */
	HwAdvertisement queryInfo(Long ad_id);

	/**
	 * 修改
	 * @param advertisement
	 */
	void update(HwAdvertisement advertisement);

	/**
	 * 上线
	 * @param ad_id
	 */
	void online(Long ad_id);

	/**
	 * 下线
	 * @param ad_id
	 */
	void not_online(Long ad_id);

	/**
	 * 删除
	 * @param ad_id
	 */
	void is_hide(Long ad_id);

	/**
	 * 恢复
	 * @param ad_id
	 */
	void is_not_hide(Long ad_id);
}
