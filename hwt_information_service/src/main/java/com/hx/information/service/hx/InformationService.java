/**
 * 
 */
package com.hx.information.service.hx;

import java.util.List;
import java.util.Map;

import com.hwt.domain.entity.information.HwInformationComment;

/**
 * 资讯
 * @author Administrator
 *
 */
public interface InformationService {

	/**
	 * 查询
	 * @param map 
	 * @return
	 */
	List<Map<String, Object>> qurey(Map<String, Object> map);

	/**
	 * 查看详情
	 * @param user_id
	 * @param information_id
	 * @return
	 */
	Map<String, Object> qureyInfo(Long user_id, Long information_id);

	/**
	 * 资讯点赞
	 * @param user_id
	 * @param information_id
	 */
	void information_good(Long user_id, Long information_id);

	/**
	 * 资讯取消赞
	 * @param user_id
	 * @param information_id
	 */
	void information_not_good(Long user_id, Long information_id);

	/**
	 * 评论
	 * @param user_id
	 * @param information_id
	 * @param parent_user_id
	 * @param parent_comment_id
	 * @param content
	 */
	HwInformationComment information_comment_insert(Long user_id, Long information_id, Long parent_user_id, Long parent_comment_id,
			String content);

	/**
	 * 评论删除
	 * @param user_id
	 * @param comment_id
	 */
	void information_comment_delete(Long user_id, Long comment_id);

	/**
	 * 评论点赞
	 * @param user_id
	 * @param comment_id
	 */
	void information_comment_good(Long user_id, Long comment_id);


	/**
	 * 评论点赞 取消
	 * @param user_id
	 * @param comment_id
	 */
	void information_comment_not_good(Long user_id, Long comment_id);

}
