DELIMITER $$

USE `wcms`$$

DROP TRIGGER /*!50032 IF EXISTS */ `trig_insert_X`$$

CREATE
    /*!50017 DEFINER = 'root'@'%' */
    TRIGGER `trig_insert_X` AFTER INSERT ON `tab_unloader_X` 
    FOR EACH ROW BEGIN 
    # CALL `p_sync_data`(new.Cmsid, new.id, new.time); 
    CALL `p_sync_data`(new.id, new.Cmsid, new.operationType, new.Time, new.PushTime, new.direction, new.unloaderMove, new.OneTask); 
    END;
$$

DELIMITER ;