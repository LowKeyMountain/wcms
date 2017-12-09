/*
Navicat MySQL Data Transfer

Source Server         : otc
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : wcms

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-12-03 15:31:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for query_interface_config
-- ----------------------------
DROP TABLE IF EXISTS `query_interface_config`;
CREATE TABLE `query_interface_config` (
  `fuction_type` varchar(100) NOT NULL,
  `param_check` varchar(500) DEFAULT NULL,
  `sql` varchar(2000) DEFAULT NULL,
  `extend_config` varchar(2000) DEFAULT NULL,
  `notes` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of query_interface_config
-- ----------------------------
INSERT INTO `query_interface_config` VALUES ('FN_001', '{\"order\":\"section|ASC、DESC\",\"sort\":\"section|\",\"page\":\"number|1\",\"rows\":\"number|10\"}', ' select s.id as \'id\', s.ship_cname as \'shipName\', s.ship_ename as \'shipEname\',\r\ns.imo_no as \'imoNo\', t.berthing_time as \'berthingTime\', t.departure_time as \'departureTime\',\r\nt.begin_time as \'beginTime\', t.end_time as \'endTime\', t.cargo_load as \'cargoLoad\',\r\ns.cabin_num as \'cabinNum\', tb.remarks as \'berthName\' , t.`status` as \'status\'\r\nfrom tab_ship s, tab_task t , tab_task_berth tb where s.id = t.ship_id \r\nand t.id = tb.task_id and (t.`status` = 0 or t.`status` = 1) ', '{\"isPaging\":\"0\",\"queryFields\":{\"XH\":\"number\",\"id\":\"string\",\"shipName\":\"string\",\"shipEname\":\"string\",\"imoNo\":\"string\",\"berthingTime\":\"time\",\"departureTime\":\"time\",\"beginTime\":\"time\",\"endTime\":\"time\",\"cargoLoad\":\"double\",\"cabinNum\":\"integer\",\"cabinNum\":\"string\",\"status\":\"integer\"}}', null);

-- ----------------------------
-- Table structure for tab_berth
-- ----------------------------
DROP TABLE IF EXISTS `tab_berth`;
CREATE TABLE `tab_berth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `berth_name` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_berth
-- ----------------------------
INSERT INTO `tab_berth` VALUES ('1', '泊位1', '泊位1', '0', '2017-12-12 14:07:39', 'admin');
INSERT INTO `tab_berth` VALUES ('2', '泊位2', '泊位1', '0', '2017-12-03 14:08:14', 'admin');

-- ----------------------------
-- Table structure for tab_ship
-- ----------------------------
DROP TABLE IF EXISTS `tab_ship`;
CREATE TABLE `tab_ship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `breadth` float DEFAULT NULL,
  `build_date` datetime DEFAULT NULL,
  `cabin_num` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `imo_no` varchar(255) DEFAULT NULL,
  `length` float DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `ship_ename` varchar(255) DEFAULT NULL,
  `ship_cname` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_ship
-- ----------------------------
INSERT INTO `tab_ship` VALUES ('1', '20', '2017-12-01 14:11:50', '10', '2017-12-03 14:12:07', 'admin', '1000', '100', '天津号_01', 'TianJingHao01', '天津号_01', '2017-12-03 14:14:07', 'admin');
INSERT INTO `tab_ship` VALUES ('2', '20', '2017-12-01 14:11:50', '10', '2017-12-03 14:12:07', 'admin', '1001', '100', '天津号_02', 'TianJingHao02', '天津号_02', '2017-12-03 14:14:07', 'admin');

-- ----------------------------
-- Table structure for tab_ship_unloader
-- ----------------------------
DROP TABLE IF EXISTS `tab_ship_unloader`;
CREATE TABLE `tab_ship_unloader` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remarks` varchar(255) DEFAULT NULL,
  `ship_unloader_name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_ship_unloader
-- ----------------------------
INSERT INTO `tab_ship_unloader` VALUES ('1', '卸船机1', '卸船机1', '0', '2017-12-03 14:09:06', 'admin');
INSERT INTO `tab_ship_unloader` VALUES ('2', '卸船机2', '卸船机2', '0', '2017-12-03 14:09:06', 'admin');
INSERT INTO `tab_ship_unloader` VALUES ('3', '卸船机3', '卸船机3', '0', '2017-12-03 14:09:06', 'admin');
INSERT INTO `tab_ship_unloader` VALUES ('4', '卸船机4', '卸船机4', '0', '2017-12-03 14:09:06', 'admin');
INSERT INTO `tab_ship_unloader` VALUES ('5', '卸船机5', '卸船机5', '0', '2017-12-03 14:09:06', 'admin');
INSERT INTO `tab_ship_unloader` VALUES ('6', '卸船机6', '卸船机6', '0', '2017-12-03 14:09:06', 'admin');

-- ----------------------------
-- Table structure for tab_task
-- ----------------------------
DROP TABLE IF EXISTS `tab_task`;
CREATE TABLE `tab_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `begin_time` datetime DEFAULT NULL,
  `berthing_time` datetime DEFAULT NULL,
  `cargo_load` double DEFAULT NULL,
  `departure_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `ship_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_quo7v8m31lllx6epxo7yhqls8` (`ship_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_task
-- ----------------------------
INSERT INTO `tab_task` VALUES ('1', '2017-12-03 14:15:33', '2017-12-03 14:16:42', '500', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '', '0', '2017-12-03 14:17:29', 'admin', '1');
INSERT INTO `tab_task` VALUES ('2', '2017-12-03 14:15:33', '2017-12-03 14:16:42', '500', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '', '2', '2017-12-03 14:17:29', 'admin', '2');

-- ----------------------------
-- Table structure for tab_task_berth
-- ----------------------------
DROP TABLE IF EXISTS `tab_task_berth`;
CREATE TABLE `tab_task_berth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_set_position` tinyint(1) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `berth_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tftql6xhrbufjx5fupiclj1a6` (`berth_id`),
  KEY `FK_rlp6d2vh1wvllac283mpvw747` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_task_berth
-- ----------------------------
INSERT INTO `tab_task_berth` VALUES ('1', '1', '泊位一', '2017-12-03 14:22:43', 'admin', '1', '1');
INSERT INTO `tab_task_berth` VALUES ('2', '1', '泊位一', '2017-12-03 14:22:43', 'admin', '2', '2');

-- ----------------------------
-- Table structure for tab_task_cabin_detail
-- ----------------------------
DROP TABLE IF EXISTS `tab_task_cabin_detail`;
CREATE TABLE `tab_task_cabin_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cabin_no` varchar(255) NOT NULL,
  `cargo_type` varchar(255) DEFAULT NULL,
  `isFinish` tinyint(1) DEFAULT NULL,
  `preunloading` double DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5eokr7t72prbbs03p9qsla812` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_task_cabin_detail
-- ----------------------------
INSERT INTO `tab_task_cabin_detail` VALUES ('1', '1', '煤粉', '0', '10', '舱一', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('2', '2', '煤粉', '0', '10', '舱二', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('3', '3', '煤粉', '0', '10', '舱三', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('4', '4', '煤粉', '0', '10', '舱四', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('5', '5', '煤粉', '0', '10', '舱五', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('6', '6', '煤粉', '0', '10', '舱六', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('7', '7', '煤粉', '0', '10', '舱七', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('8', '8', '煤粉', '0', '10', '舱八', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('9', '9', '煤粉', '0', '10', '舱九', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('10', '10', '煤粉', '0', '10', '舱十', '2017-12-03 14:19:31', 'admin', '1');
INSERT INTO `tab_task_cabin_detail` VALUES ('11', '1', '煤粉', '0', '10', '舱一', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('12', '2', '煤粉', '0', '10', '舱二', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('13', '3', '煤粉', '0', '10', '舱三', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('14', '4', '煤粉', '0', '10', '舱四', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('15', '5', '煤粉', '0', '10', '舱五', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('16', '6', '煤粉', '0', '10', '舱六', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('17', '7', '煤粉', '0', '10', '舱七', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('18', '8', '煤粉', '0', '10', '舱八', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('19', '9', '煤粉', '0', '10', '舱九', '2017-12-03 14:19:31', 'admin', '2');
INSERT INTO `tab_task_cabin_detail` VALUES ('20', '10', '煤粉', '0', '10', '舱十', '2017-12-03 14:19:31', 'admin', '2');

-- ----------------------------
-- Table structure for tab_task_cabin_position
-- ----------------------------
DROP TABLE IF EXISTS `tab_task_cabin_position`;
CREATE TABLE `tab_task_cabin_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cabin_no` varchar(255) NOT NULL,
  `end_position` double DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `start_position` double DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `task_berth_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_smwr70ieap1w6o2wlivk9hd1t` (`task_berth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_task_cabin_position
-- ----------------------------
INSERT INTO `tab_task_cabin_position` VALUES ('1', '1', '0', null, '9', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('2', '2', '10', null, '19', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('3', '3', '20', null, '29', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('4', '4', '30', null, '39', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('5', '5', '40', null, '49', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('6', '6', '50', null, '59', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('7', '7', '60', null, '69', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('8', '8', '70', null, '79', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('9', '9', '80', null, '89', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('10', '10', '90', null, '100', '2017-12-03 14:25:00', 'admin', '1');
INSERT INTO `tab_task_cabin_position` VALUES ('11', '1', '0', '', '9', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('12', '2', '10', '', '19', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('13', '3', '20', '', '29', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('14', '4', '30', '', '39', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('15', '5', '40', '', '49', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('16', '6', '50', '', '59', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('17', '7', '60', '', '69', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('18', '8', '70', '', '79', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('19', '9', '80', '', '89', '2017-12-03 14:25:00', 'admin', '2');
INSERT INTO `tab_task_cabin_position` VALUES ('20', '10', '90', '', '100', '2017-12-03 14:25:00', 'admin', '2');

-- ----------------------------
-- Table structure for tab_task_unload_ship_detail
-- ----------------------------
DROP TABLE IF EXISTS `tab_task_unload_ship_detail`;
CREATE TABLE `tab_task_unload_ship_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `end_time` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `ship_unloader_id` int(11) DEFAULT NULL,
  `berth_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c4byae0a4ytfwfq5c919e4ulb` (`ship_unloader_id`),
  KEY `FK_f1rcxra2tnbb5pwpnmt1at6pt` (`berth_id`),
  CONSTRAINT `FK_f1rcxra2tnbb5pwpnmt1at6pt` FOREIGN KEY (`berth_id`) REFERENCES `tab_task_berth` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_task_unload_ship_detail
-- ----------------------------
INSERT INTO `tab_task_unload_ship_detail` VALUES ('1', null, null, '2017-12-03 14:32:31', '2017-12-03 14:31:49', 'admin', '1', '1');
INSERT INTO `tab_task_unload_ship_detail` VALUES ('2', null, null, '2017-12-03 14:32:35', '2017-12-03 14:31:54', 'admin', '2', '1');
INSERT INTO `tab_task_unload_ship_detail` VALUES ('3', null, null, '2017-12-03 14:32:39', '2017-12-03 14:31:57', 'admin', '3', '1');
INSERT INTO `tab_task_unload_ship_detail` VALUES ('5', '0000-00-00 00:00:00', '', '2017-12-03 14:32:31', '2017-12-03 14:31:49', 'admin', '4', '2');
INSERT INTO `tab_task_unload_ship_detail` VALUES ('6', '0000-00-00 00:00:00', '', '2017-12-03 14:32:35', '2017-12-03 14:31:54', 'admin', '5', '2');
INSERT INTO `tab_task_unload_ship_detail` VALUES ('7', '0000-00-00 00:00:00', '', '2017-12-03 14:32:39', '2017-12-03 14:31:57', 'admin', '6', '2');

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
