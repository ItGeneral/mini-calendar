CREATE TABLE `calendar_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `solar_year` int(4) NOT NULL COMMENT '阳历年',
  `solar_month` int(4) NOT NULL COMMENT '阳历月',
  `solar_day` int(4) NOT NULL COMMENT '阳历日',
  `lunar_year` int(4) NOT NULL COMMENT '阴历年 如',
  `lunar_month` varchar(16) NOT NULL COMMENT '阴历月 如：一',
  `lunar_day` varchar(16) NOT NULL COMMENT '阴历日 如：初一',
  `gz_year` varchar(16) NOT NULL COMMENT '干支年,如庚子',
  `gz_month` varchar(16) NOT NULL COMMENT '干支月,如戊寅',
  `gz_day` varchar(16) NOT NULL COMMENT '干支日,如壬寅',
  `animal` varchar(16) NOT NULL COMMENT '生肖',
  `avoid` varchar(512) NOT NULL DEFAULT '' COMMENT '忌事',
  `suit` varchar(512) NOT NULL DEFAULT '' COMMENT '宜事',
  `week_day` varchar(16) NOT NULL COMMENT '星期',
  `remark` varchar(128) NOT NULL COMMENT '节日名称',
  `status` int(1) unsigned zerofill NOT NULL COMMENT '0默认，1：表示休息，2：调休上班 ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `time_index` (`solar_year`,`solar_month`,`solar_day`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `calendar_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(128) NOT NULL COMMENT '微信用户唯一标识',
  `nick_name` varchar(128) DEFAULT '' COMMENT '昵称',
  `gender` int(2) DEFAULT '0' COMMENT '性别 1:男 2:女',
  `country` varchar(256) DEFAULT '' COMMENT '所在国家',
  `province` varchar(256) DEFAULT '' COMMENT '所在省',
  `city` varchar(256) DEFAULT '' COMMENT '所在市',
  `avatar_url` varchar(256) DEFAULT '' COMMENT '微信头像地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `calendar_diary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calendar_info_id` int(11) NOT NULL COMMENT '日历表id',
  `open_id` varchar(64) NOT NULL COMMENT '微信openId',
  `user_id` int(11) NOT NULL COMMENT '用户表id',
  `solar_date` varchar(32) NOT NULL COMMENT '日期:2021-01-01',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
  `content` mediumtext COMMENT '内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_calendar_info_id` (`open_id`,`calendar_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `domain_space` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户表id',
  `space_name` varchar(64) NOT NULL COMMENT '领域/空间名称',
  `avatar_url` varchar(256) NOT NULL COMMENT '领域/空间头像',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id_name_key` (`user_id`,`space_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_user_relate(
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) NOT NULL COMMENT '用户表id',
`domain_space_id` int(11) NOT NULL COMMENT '空间表id',
`type` TINYINT not null DEFAULT 0 COMMENT '0：表示自己创建的，1:表示邀请加入的',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 UNIQUE KEY `user_space_id`(`user_id`, `domain_space_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_diary_relate(
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_id` int(11) NOT NULL COMMENT '用户表id',
`domain_space_id` int(11) NOT NULL COMMENT '空间表id',
`diary_id` int(11) NOT NULL COMMENT '主题日历表id',
`deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除 0未删除 1已删除',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (`id`),
KEY `space_user_key` (`domain_space_id`, `user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '空间、主题和用户关联表';

create table space_subject(
`id` int(11) NOT NULL AUTO_INCREMENT,
`space_id` int(11) NOT NULL COMMENT '空间id',
`user_id` int(11) NOT NULL COMMENT '用户表id',
`title` varchar(64) DEFAULT '' COMMENT '标题',
`content` mediumtext NOT NULL COMMENT '内容',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 KEY space_user_key(`space_id`, `user_id`)
)ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 comment '空间主题表';

create table subject_comment(
`id` int(11) NOT NULL AUTO_INCREMENT,
`space_id` int(11) NOT NULL COMMENT '空间id',
`subject_id` int(11) NOT NULL COMMENT '主题id',
`user_id` int(11) NOT NULL COMMENT '用户表id',
`content` mediumtext NOT NULL COMMENT '评论内容',
`deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除 0未删除  1已删除',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 KEY space_subject_key(`space_id`, `subject_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '主题评论表';

create table subject_praise(
`id` int(11) NOT NULL AUTO_INCREMENT,
`space_id` int(11) NOT NULL COMMENT '空间id',
`subject_id` int(11) NOT NULL COMMENT '主题id',
`user_id` int(11) NOT NULL COMMENT '用户表id',
`deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除 0未删除  1已删除',
`create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 KEY space_user_key(`space_id`, `subject_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '主题点赞表';