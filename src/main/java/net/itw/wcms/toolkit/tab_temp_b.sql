CREATE TABLE `tab_temp_b` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `Cmsid` varchar(100) NOT NULL COMMENT 'Cms卸船机编号',
  `operationType` int(2) DEFAULT NULL COMMENT '操作类型(0大车位置，1作业量信息。)',
  `Time` datetime DEFAULT NULL COMMENT '操作时间(YYYY—MM—dd  HH_mi_SS)',
  `PushTime` datetime DEFAULT NULL COMMENT '推送时间',
  `direction` varchar(1) DEFAULT NULL COMMENT '方向(0正方向，1反方向，设定编码器一个方向为正方向，如果无需设定，默认正方向)',
  `unloaderMove` double DEFAULT NULL COMMENT '卸船机移动位置(移动位置)',
  `OneTask` double DEFAULT NULL COMMENT '一次抓钩作业量(一次抓钩作业量)',
  `offset` double DEFAULT NULL COMMENT '船舶偏移量',
  `groupId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`Cmsid`)
) ENGINE=InnoDB AUTO_INCREMENT=35413 DEFAULT CHARSET=utf8;