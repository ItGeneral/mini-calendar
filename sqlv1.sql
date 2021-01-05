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

create table calendar_user(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `open_id` varchar(128) NOT NULL COMMENT '微信用户唯一标识',
    `nick_name` varchar(128) NOT NULL DEFAULT '' COMMENT '昵称',
    `gender` int(2) not null DEFAULT 0 COMMENT '性别 1:男 2:女',
    `country` varchar(256) NOT NULL DEFAULT '' COMMENT '所在国家',
    `province` varchar(256) NOT NULL DEFAULT '' COMMENT '所在省',
    `city` varchar(256) NOT NULL DEFAULT '' COMMENT '所在市',
    `avatarUrl` varchar(256) NOT NULL DEFAULT '' COMMENT '微信头像地址',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

create table calendar_diary(
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `calendar_info_id` int(11) NOT NULL COMMENT '日历表id',
 `user_id` int(11) NOT NULL COMMENT '用户表id',
 `solar_date` varchar(32) NOT NULL COMMENT '日期:2021-01-01',
 `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
 `content` MEDIUMTEXT COMMENT '内容',
 `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
 `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`),
 UNIQUE KEY `user_calendar_info_id` (`user_id`,`calendar_info_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;