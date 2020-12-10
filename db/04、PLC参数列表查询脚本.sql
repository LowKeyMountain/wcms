select * from (select '#1' unloaderId, '1号卸船机' unloaderName, t.deliveryRate, t.doumenOpeningDegree, t.hopperLoad, t.Time updateTime, '在线' systemState from tab_unloader_all t where t.Cmsid = 'ABB_GSU_1' AND t.operationType = '3' ORDER BY t.Time desc LIMIT 1) t1
UNION ALL
select * from (select '#2' unloaderId, '2号卸船机' unloaderName, t.deliveryRate, t.doumenOpeningDegree, t.hopperLoad, t.Time updateTime, '在线' systemState from tab_unloader_all t where t.Cmsid = 'ABB_GSU_2' AND t.operationType = '3' ORDER BY t.Time desc LIMIT 1) t2
UNION ALL
select * from (select '#3' unloaderId, '3号卸船机' unloaderName, t.deliveryRate, t.doumenOpeningDegree, t.hopperLoad, t.Time updateTime, '在线' systemState from tab_unloader_all t where t.Cmsid = 'ABB_GSU_3' AND t.operationType = '3' ORDER BY t.Time desc LIMIT 1) t3
UNION ALL
select * from (select '#4' unloaderId, '4号卸船机' unloaderName, t.deliveryRate, t.doumenOpeningDegree, t.hopperLoad, t.Time updateTime, '在线' systemState from tab_unloader_all t where t.Cmsid = 'ABB_GSU_4' AND t.operationType = '3' ORDER BY t.Time desc LIMIT 1) t4
UNION ALL
select * from (select '#5' unloaderId, '5号卸船机' unloaderName, t.deliveryRate, t.doumenOpeningDegree, t.hopperLoad, t.Time updateTime, '在线' systemState from tab_unloader_all t where t.Cmsid = 'ABB_GSU_5' AND t.operationType = '3' ORDER BY t.Time desc LIMIT 1) t5
UNION ALL
select * from (select '#6' unloaderId, '6号卸船机' unloaderName, t.deliveryRate, t.doumenOpeningDegree, t.hopperLoad, t.Time updateTime, '在线' systemState from tab_unloader_all t where t.Cmsid = 'ABB_GSU_6' AND t.operationType = '3' ORDER BY t.Time desc LIMIT 1) t6