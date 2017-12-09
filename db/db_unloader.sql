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

