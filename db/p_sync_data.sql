DELIMITER $$

USE `wcms`$$

DROP PROCEDURE IF EXISTS `p_sync_data`$$
--同步各卸船机表的数据到一张表中
CREATE DEFINER=`root`@`%` PROCEDURE `p_sync_data`(IN id INT, IN Cmsid VARCHAR(50), IN operationType INT, IN dTime DATETIME, IN pushTime DATETIME, IN direction VARCHAR(2), 
IN unloaderMove FLOAT, IN OneTask FLOAT)
BEGIN  
    INSERT INTO tab_unloader_all(`uid`, `Cmsid`, `operationType`, `time`, `PushTime`, `direction`, `unloaderMove`, `OneTask`) 
    VALUES (id, Cmsid, operationType, dTime, pushTime, direction, unloaderMove, OneTask);
END$$

DELIMITER ;