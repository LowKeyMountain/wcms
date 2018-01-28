CREATE TABLE `tab_temp_c` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `cabinId` int(11) DEFAULT NULL COMMENT '船舱id',
  `Cmsid` varchar(50) DEFAULT NULL COMMENT '卸船机编号',
  `startTime` datetime DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  `firstTime` datetime DEFAULT NULL COMMENT '第一次抓钩作业时间',
  `lastTime` datetime DEFAULT NULL COMMENT '最后一次抓钩作业时间',
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=494 DEFAULT CHARSET=utf8;