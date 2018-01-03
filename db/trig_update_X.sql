DELIMITER $$

USE `wcms`$$

DROP TRIGGER /*!50032 IF EXISTS */ `trig_update_X`$$

CREATE
    /*!50017 DEFINER = 'root'@'%' */
    TRIGGER `trig_update_X` AFTER UPDATE ON `tab_unloader_X` 
    FOR EACH ROW BEGIN 
    UPDATE tab_unloader_all t SET t.Time= new.Time, t.PushTime=new.PushTime WHERE t.Cmsid='ABB_GSU_1' AND t.operationType=2;
    END;
$$

DELIMITER ;