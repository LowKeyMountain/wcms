SELECT c.task_id, w.cmsid
	, CASE w.cmsid
		WHEN 'ABB_GSU_1' THEN '#1'
		WHEN 'ABB_GSU_2' THEN '#2'
		WHEN 'ABB_GSU_3' THEN '#3'
		WHEN 'ABB_GSU_4' THEN '#4'
		WHEN 'ABB_GSU_5' THEN '#5'
		WHEN 'ABB_GSU_6' THEN '#6'
		ELSE w.cmsid
	END AS 'unloaderName', round(w.usedTime, 2) AS 'usedTime'
	, round(w.unloading, 2) AS 'unloading'
	, round(w.unloading / w.usedTime, 2) AS 'efficiency'
FROM (
	SELECT cabinId, cmsid, MIN(Time) AS 'startTime'
		, MAX(Time) AS 'endTime'
		, timestampdiff(SECOND, MIN(Time), MAX(Time)) / 3600 AS 'usedTime'
		, SUM(OneTask) AS 'unloading'
	FROM v_work_info
	WHERE 1 = 1
		AND UNIX_TIMESTAMP(Time) BETWEEN UNIX_TIMESTAMP(?) AND UNIX_TIMESTAMP(?)
	GROUP BY Cmsid
) w
	LEFT JOIN v_cabin_info c ON w.cabinId = c.cabin_id
WHERE 1 = 1
	AND c.task_id = ?
	ORDER BY w.cmsid ASC
	