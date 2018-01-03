DELIMITER $$

USE `wcms`$$

DROP PROCEDURE IF EXISTS `p_sync_temp_b`$$

CREATE DEFINER=`root`@`%` PROCEDURE `p_sync_temp_b`(IN id INT, IN Cmsid VARCHAR(50), IN operationType INT, IN dTime DATETIME, IN pushTime DATETIME, IN direction VARCHAR(2), 
IN unloaderMove FLOAT, IN OneTask FLOAT)
BEGIN
    DECLARE  v_id INT;
    DECLARE  v_cabinId INT;
    DECLARE  v_startPosition FLOAT;
    DECLARE  v_endPosition FLOAT;
    
    DECLARE  v_cmsid VARCHAR(30); -- 卸船机编号
    DECLARE  v_groupid VARCHAR(30); -- 组编号
    DECLARE  v_move VARCHAR(30); -- 位移数据
  
    -- 结束标志
    DECLARE done INT DEFAULT FALSE;
    -- 获取作业船舶的所有舱位信息
    DECLARE cur_cabin CURSOR FOR SELECT cabin.`id`,cabin.`start_position`,cabin.`end_position` FROM (SELECT cab.id, cab.`start_position`, cab.`end_position`, ca.`task_id` FROM  tab_cabin cab LEFT JOIN tab_cargo ca
     ON cab.`cargo_id`=ca.`id`) cabin WHERE cabin.task_id IN (SELECT t.id FROM tab_task t WHERE t.`status` = 1);
    -- 绑定结束标志到游标
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    -- open cursor
    OPEN cur_cabin;
    -- 遍历
    cabin_loop: LOOP
	-- 与卸船机实时传输的数据进行匹配
	FETCH NEXT FROM cur_cabin INTO v_cabinId, v_startPosition, v_endPosition;
	IF unloaderMove >= v_startPosition AND unloaderMove <= v_endPosition THEN
		-- 查询作业船舱已同步的最新数据
		SELECT tmp.CmsId, tmp.groupId, tmp.unloaderMove INTO v_cmsid, v_groupid, v_move FROM tab_temp_b tmp WHERE tmp.`cabinId`=v_cabinId ORDER BY tmp.`Time` DESC LIMIT 1;
		IF OneTask > 0 THEN
			-- 检查是否已有分组
			IF v_cmsid IS NULL THEN
				INSERT INTO tab_temp_b(`cabinId`, `groupId`, `unloaderId`, `Cmsid`, `operationType`, `time`, `PushTime`, `direction`, `unloaderMove`, `OneTask`) 
				VALUES (v_cabinId, 1, id, Cmsid, operationType, dTime, pushTime, direction, unloaderMove, OneTask);
				INSERT INTO tab_temp_c(`cabinId`, `groupId`, `Cmsid`, `startTime`, `endTime`, `firstTime`) VALUES (v_cabinId, 1, Cmsid, dTime, NOW(), dTime); 
			ELSE
				IF v_move != unloaderMove THEN
					INSERT INTO tab_temp_b(`cabinId`, `groupId`, `unloaderId`, `Cmsid`, `operationType`, `time`, `PushTime`, `direction`, `unloaderMove`, `OneTask`) 
					VALUES (v_cabinId, v_groupid + 1, id, Cmsid, operationType, dTime, pushTime, direction, unloaderMove, OneTask);
					INSERT INTO tab_temp_c(`cabinId`, `groupId`, `Cmsid`, `startTime`, `endTime`, `firstTime`) VALUES (v_cabinId, v_groupid + 1, Cmsid, dTime, NOW(), dTime); 
				ELSE
					INSERT INTO tab_temp_b(`cabinId`, `groupId`, `unloaderId`, `Cmsid`, `operationType`, `time`, `PushTime`, `direction`, `unloaderMove`, `OneTask`) 
					VALUES (v_cabinId, v_groupid, id, Cmsid, operationType, dTime, pushTime, direction, unloaderMove, OneTask);
					UPDATE tab_temp_c c SET c.`endTime` = dTime,c.`lastTime` = dTime WHERE c.`cabinId`= v_cabinId AND c.`groupId`= v_groupid; 
				END IF; 
			END IF; 
			LEAVE cabin_loop;
		ELSEIF OneTask = 0 AND v_cmsid IS NOT NULL THEN
			IF v_move != unloaderMove THEN
				UPDATE tab_temp_c c SET c.`endTime` = dTime,c.`lastTime` = dTime WHERE c.`cabinId`= v_cabinId AND c.`groupId`= v_groupid; 			
			END IF;
			LEAVE cabin_loop;			
		END IF;
	ELSEIF done THEN 
		LEAVE cabin_loop; 
	END IF;
    END LOOP cabin_loop; 
    CLOSE cur_cabin; 
END$$

DELIMITER ;