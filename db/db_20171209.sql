/*
Navicat MySQL Data Transfer

Source Server         : otc
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : wcms

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-12-09 15:20:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for x27_join_user_resource
-- ----------------------------
DROP TABLE IF EXISTS `x27_join_user_resource`;
CREATE TABLE `x27_join_user_resource` (
  `USER_ID` int(11) NOT NULL,
  `RESOURCE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`RESOURCE_ID`),
  KEY `FK_8x0ol43wft9jpdttu7qtnhyru` (`RESOURCE_ID`),
  KEY `FK_ocil4188njvpih3c86aeqy4y0` (`USER_ID`),
  CONSTRAINT `FK_8x0ol43wft9jpdttu7qtnhyru` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `x27_resources` (`id`),
  CONSTRAINT `FK_ocil4188njvpih3c86aeqy4y0` FOREIGN KEY (`USER_ID`) REFERENCES `x27_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of x27_join_user_resource
-- ----------------------------

-- ----------------------------
-- Table structure for x27_organizations
-- ----------------------------
DROP TABLE IF EXISTS `x27_organizations`;
CREATE TABLE `x27_organizations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `ORG_CODE` varchar(255) DEFAULT NULL,
  `ORG_NAME` varchar(255) DEFAULT NULL,
  `ORG_SHORT_NAME` varchar(255) DEFAULT NULL,
  `FK_PARENT_ORG_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of x27_organizations
-- ----------------------------

-- ----------------------------
-- Table structure for x27_resources
-- ----------------------------
DROP TABLE IF EXISTS `x27_resources`;
CREATE TABLE `x27_resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_PERSON` varchar(255) DEFAULT NULL,
  `MODULE_FLAG` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `SORT_NO` int(11) DEFAULT NULL,
  `structure` varchar(255) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `UPDATE_PERSON` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of x27_resources
-- ----------------------------

-- ----------------------------
-- Table structure for x27_roles
-- ----------------------------
DROP TABLE IF EXISTS `x27_roles`;
CREATE TABLE `x27_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remark` varchar(255) DEFAULT NULL,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `UPDATE_PERSION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of x27_roles
-- ----------------------------

-- ----------------------------
-- Table structure for x27_users
-- ----------------------------
DROP TABLE IF EXISTS `x27_users`;
CREATE TABLE `x27_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_PERSON` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  `isDelete` tinyint(1) DEFAULT NULL,
  `isLock` tinyint(1) DEFAULT NULL,
  `LAST_LOGIN_IP` varchar(255) DEFAULT NULL,
  `LAST_LOGIN_TIME` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `REAL_NAME` varchar(255) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `UPDATE_PERSON` varchar(255) DEFAULT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of x27_users
-- ----------------------------
INSERT INTO `x27_users` VALUES ('1', '2017-09-02 17:13:41', ' admin', 'admin@gbicc.net', '1', '1', '0', '0', '127.0.0.1', '2017-09-28 00:00:00', 'c62d929e7b7e7b6165923a5dfc60cb56', '张三', '2017-10-11 15:11:37', 'admin', 'admin');
INSERT INTO `x27_users` VALUES ('10', '2017-09-28 15:42:19', 'admin', null, '0', '0', '0', '0', '127.0.0.1', null, 'c62d929e7b7e7b6165923a5dfc60cb56', '肖立', '2017-09-28 15:42:19', 'admin', '185003001');
INSERT INTO `x27_users` VALUES ('12', '2017-09-28 15:46:37', 'admin', null, '1', '0', '0', '0', '127.0.0.1', null, 'c62d929e7b7e7b6165923a5dfc60cb56', '中银', '2017-09-29 11:05:16', 'admin', '131101001');
INSERT INTO `x27_users` VALUES ('62', '2017-11-05 03:09:27', 'admin', null, '1', '0', '0', '0', '127.0.0.1', null, 'e10adc3949ba59abbe56e057f20f883e', '8888888', '2017-11-25 18:20:48', 'admin', 'zhangsan');
INSERT INTO `x27_users` VALUES ('63', '2017-11-19 14:21:36', 'admin', null, '1', '0', '0', '0', '127.0.0.1', null, 'e10adc3949ba59abbe56e057f20f883e', 'aaa', '2017-11-25 18:19:56', 'admin', '9999');


-- ----------------------------
-- Table structure for tab_unloader_1
-- ----------------------------
DROP TABLE IF EXISTS `tab_unloader_1`;
CREATE TABLE `tab_unloader_1` (
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) NOT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime NOT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  PRIMARY KEY (`Cmsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for tab_unloader_2
-- ----------------------------
DROP TABLE IF EXISTS `tab_unloader_2`;
CREATE TABLE `tab_unloader_2` (
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) NOT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime NOT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  PRIMARY KEY (`Cmsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tab_unloader_3
-- ----------------------------
DROP TABLE IF EXISTS `tab_unloader_3`;
CREATE TABLE `tab_unloader_3` (
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) NOT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime NOT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  PRIMARY KEY (`Cmsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for tab_unloader_4
-- ----------------------------
DROP TABLE IF EXISTS `tab_unloader_4`;
CREATE TABLE `tab_unloader_4` (
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) NOT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime NOT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  PRIMARY KEY (`Cmsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for tab_unloader_5
-- ----------------------------
DROP TABLE IF EXISTS `tab_unloader_5`;
CREATE TABLE `tab_unloader_5` (
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) NOT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime NOT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  PRIMARY KEY (`Cmsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for tab_unloader_6
-- ----------------------------
DROP TABLE IF EXISTS `tab_unloader_6`;
CREATE TABLE `tab_unloader_6` (
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) NOT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime NOT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  PRIMARY KEY (`Cmsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

