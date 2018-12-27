CREATE TABLE `hx_use_real` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `real_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `identity_no` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `identity_positive` varchar(200) DEFAULT NULL COMMENT '身份证正面',
  `identity_negative` varchar(200) NOT NULL COMMENT '身份证反面',
  `identity_hold` varchar(200) DEFAULT NULL COMMENT '手持证件照',
  `present_address` varchar(300) DEFAULT NULL COMMENT '现住址',
  `admin_user_id` bigint(20) DEFAULT NULL COMMENT '处理人',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0-未审核 1-通过 2-未通过',
  `fail_reason` varchar(300) DEFAULT NULL COMMENT '未通过原因',
  `often_phone` varchar(20) DEFAULT NULL COMMENT '常用联系电话',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户真实信息';

CREATE TABLE `hx_user_video_grade_apply` (
  `grade_apply_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请id',
  `user_id` bigint(20) NOT NULL COMMENT '申请人',
  `apply_grade` int(2) NOT NULL DEFAULT '2' COMMENT '申请的等级  2-中等（60秒） 3-高级（能直播）',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0-未处理 1-通过 2-未通过',
  `admin_user_id` bigint(20) DEFAULT NULL COMMENT '处理人',
  `fail_reason` varchar(300) DEFAULT NULL COMMENT '未通过原因',
  `create_time` bigint(20) DEFAULT NULL COMMENT '申请时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`grade_apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小视频等级申请表';


ALTER TABLE `hx_user_info` ADD  `viode_grade` int(2) NOT NULL DEFAULT '1' COMMENT '1-初级（15秒） 2-中等（60秒） 3-高级（能直播）';

ALTER TABLE `hx_user_info` ADD  `video_cover` varchar(200) DEFAULT NULL COMMENT '小视频封面';

ALTER TABLE `hx_user_video` ADD `music_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '音频id';

ALTER TABLE `hx_user_video` ADD  `transmit_number` int(100) DEFAULT NULL COMMENT '转发次数';

CREATE TABLE `hx_music` (
  `music_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '音乐id',
  `music_url` varchar(255) NOT NULL COMMENT '音乐地址',
  `music_title` varchar(255) NOT NULL COMMENT '音乐标题',
  `create_time` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `use_account` bigint(20) NOT NULL DEFAULT '1' COMMENT '引用次数',
  `user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '上传人id 0-平台',
  `is_open` int(10) NOT NULL DEFAULT '0' COMMENT '是否公开 0-公开 1-隐藏 2-删除',
  `music_tag` int(20) DEFAULT NULL COMMENT '音乐标签 1-影视原声,2-日韩,3-生活,4-搞怪,5-流行,6-说唱,7-民谣,8-欧美音乐标签 1-影视原声,2-日韩,3-生活,4-搞怪,5-流行,6-说唱,7-民谣,8-欧美,null-其他(无标签)',
  `music_cover` varchar(255) DEFAULT NULL COMMENT '音乐封面',
  `music_time` bigint(20) DEFAULT NULL COMMENT '音乐时长',
  `music_writer` varchar(64) DEFAULT '平台' COMMENT '音乐作者',
  PRIMARY KEY (`music_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='音乐表';