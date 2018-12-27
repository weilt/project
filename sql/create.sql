
ALTER TABLE `hx_user_video` ADD `name_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联的id  0为没有关联';

ALTER TABLE `hx_user_video` ADD `name_type` int(2) NOT NULL DEFAULT '0' COMMENT '关联的类型   1-景点 2-导游  3- 线路 4-资讯  0为没有关联';

ALTER TABLE `hx_user_video` ADD `video_type` int(2) NOT NULL DEFAULT '1' COMMENT '1-小视频，2-长视频';