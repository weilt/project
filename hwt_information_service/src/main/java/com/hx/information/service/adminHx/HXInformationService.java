package com.hx.information.service.adminHx;

import java.util.Map;

import com.hwt.domain.entity.information.vo.HwInformationCommentAdminVo;
import com.hwt.domain.entity.mg.information.HwInformation;
import com.hx.core.page.asyn.PageResult;

/**
 * 资讯后台管理
 * @author Administrator
 *
 */
public interface HXInformationService {

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	PageResult<Map<String, Object>> query(Map<String, Object> map);

	/**
	 * 添加
	 * @param hwInformation
	 */
	void insert(HwInformation hwInformation) throws Exception ;

	/**
	 * 查看详情
	 * @param information_id
	 * @return
	 */
	Map<String, Object> queryInfo(Long information_id);

	/**
	 * 更新
	 * @param update	內容
	 * @param filter	条件
	 */
	void update(Map<String, Object> update, Map<String, Object> filter);

	/**
	 * 查询评论
	 * @param map
	 * @return
	 */
	PageResult<HwInformationCommentAdminVo> query_comment(Map<String, Object> map);

	/**
	 * 隐藏评论
	 * @param comment_id
	 */
	void comment_hide(Long comment_id);

	/**
	 * 恢复评论
	 * @param comment_id
	 */
	void comment_not_hide(Long comment_id);

	/**
	 * 删除评论
	 * @param comment_id
	 */
	void delete(Long comment_id);

	/**
	 * 修改esq
	 * @param update
	 * @param filter1
	 */
	void updateEsq(Map<String, Object> update, Map<String, Object> filter1);

	

}
