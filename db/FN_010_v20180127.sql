SELECT c.task_id, w.cmsid, c.cabin_id, c.cabin_no
	, CASE w.cmsid
		WHEN 'ABB_GSU_1' THEN '#1'
		WHEN 'ABB_GSU_2' THEN '#2'
		WHEN 'ABB_GSU_3' THEN '#3'
		WHEN 'ABB_GSU_4' THEN '#4'
		WHEN 'ABB_GSU_5' THEN '#5'
		WHEN 'ABB_GSU_6' THEN '#6'
		ELSE w.cmsid
	END AS 'unloaderName', DATE_FORMAT(w.startTime, '%Y-%m-%d %H:%i:%s') AS 'startTime'
	, DATE_FORMAT(w.endTime, '%Y-%m-%d %H:%i:%s') AS 'endTime'
	, round(w.usedTime, 2) AS 'usedTime'
	, round(w.unloading, 2) AS 'unloading'
	, round(w.unloading / w.usedTime, 2) AS 'efficiency'
FROM (
	SELECT c.cabinId, b.cmsid, MIN(b.Time) AS 'startTime'
		, MAX(b.Time) AS 'endTime'
		, timestampdiff(SECOND, MIN(b.Time), MAX(b.Time)) / 3600 AS 'usedTime'
		, SUM(b.OneTask) AS 'unloading'
	FROM `tab_temp_c_71` `c`, `tab_temp_b_71` `b`
	WHERE 1 = 1
		AND `c`.`id` = `b`.`groupId`
	GROUP BY b.Cmsid, b.groupId
) w
	LEFT JOIN v_cabin_info c ON w.cabinId = c.cabin_id
WHERE 1 = 1
ORDER BY w.startTime ASC