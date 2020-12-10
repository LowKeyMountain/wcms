--函数更新 -- start
BEGIN  
    INSERT INTO tab_unloader_all(`uid`, `Cmsid`, `operationType`, `time`, `PushTime`, `direction`, `unloaderMove`, `OneTask`, `deliveryRate`, `doumenOpeningDegree`, `hopperLoad`) 
    VALUES (id, Cmsid, operationType, dTime, pushTime, direction, unloaderMove, OneTask, deliveryRate, doumenOpeningDegree, hopperLoad);
END

IN id INT, IN Cmsid VARCHAR(50), IN operationType INT, IN dTime DATETIME, IN pushTime DATETIME, IN direction VARCHAR(2), IN unloaderMove FLOAT, IN OneTask FLOAT, IN deliveryRate FLOAT, IN doumenOpeningDegree FLOAT, IN hopperLoad FLOAT
-- end

BEGIN 
	 # CALL `p_sync_data`(new.Cmsid, new.id, new.time); 
     #CALL `p_sync_data`(new.id, new.Cmsid, new.operationType, new.Time, new.PushTime, new.direction, new.unloaderMove, new.OneTask); 
     CALL `p_sync_data`(new.id, new.Cmsid, new.operationType, new.Time, new.PushTime, new.direction, new.unloaderMove, new.OneTask, new.deliveryRate, new.doumenOpeningDegree, new.hopperLoad); 
END
