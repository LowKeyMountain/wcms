 SELECT
	*
FROM
	(
		SELECT
			t.*, 
		CASE 
			WHEN round(qcl.clearance, 1) IS NULL THEN 0
		  ELSE round(qcl.clearance, 1)
		END AS 'clearance'
		FROM
			(
				SELECT
					z_xcqk.*, 
				CASE
					WHEN round(c_syl.finished, 1) IS NULL THEN 0
					ELSE round(c_syl.finished, 1)
				END AS 'finished',
				CASE
					WHEN ( round( z_xcqk.total - c_syl.finished, 1 ) ) IS NULL THEN z_xcqk.total
					ELSE ( round( z_xcqk.total - c_syl.finished, 1 ) )
				END AS 'remainder'
			FROM
				(
					SELECT
						cabin.task_id,
						cabin.cargo_id AS 'cargoId',
						cabin.cabin_id AS 'cabinId',
						cabin.cabin_no AS 'cabinNo',
						cabin.cargo_type AS 'cargoName',
						cabin.preunloading AS 'total',
						cabin.start_position AS 'startPosition',
						cabin.end_position AS 'endPosition',
						cabin. STATUS AS 'status'
					FROM
						v_cabin_info cabin
				) z_xcqk
			LEFT JOIN (
				SELECT
					SUM(c.OneTask) AS 'finished',
					o.cabinNo,
					o.task_id
				FROM
					(
						SELECT
							cabin.task_id,
							cabin.begin_time,
							cabin.cabin_no AS 'cabinNo',
							cabin.cabin_id,
							CASE
								WHEN (cabin.end_time IS NULL && cabin.clear_time IS NULL) THEN CURRENT_TIMESTAMP ()
								WHEN (cabin.end_time IS NULL && cabin.clear_time IS NOT NULL) THEN cabin.clear_time
								WHEN (cabin.end_time IS NOT NULL && cabin.clear_time IS NULL) THEN cabin.end_time
								WHEN (cabin.end_time  IS NOT NULL && cabin.clear_time IS NOT NULL) THEN cabin.clear_time
							END AS 'end_time'
						FROM
							v_cabin_info cabin
					) o
				LEFT JOIN v_work_info c ON o.begin_time <= c.Time
				AND o.end_time >= c.Time
				AND o.cabin_id = c.cabinId
				GROUP BY
					o.cabinNo,
					o.task_id
			) c_syl ON z_xcqk.task_id = c_syl.task_id
			AND z_xcqk.cabinNo = c_syl.cabinNo
			) t
		LEFT JOIN (
			SELECT
				SUM(c.OneTask) AS 'clearance',
				o.cabinNo,
				o.task_id
			FROM
				(
					SELECT
						cabin.task_id,
						cabin.cabin_id,
						cabin.cabin_no AS 'cabinNo',
						CASE
							WHEN cabin.end_time IS NULL THEN CURRENT_TIMESTAMP ()
							ELSE cabin.end_time
						END AS 'end_time',
					cabin.clear_time
				FROM
					v_cabin_info cabin
				) o
			LEFT JOIN v_work_info c ON o.clear_time <= c.Time
			AND o.end_time >= c.Time
			AND o.cabin_id = c.cabinId
			GROUP BY
				o.cabinNo,
				o.task_id
		) qcl ON t.task_id = qcl.task_id
		AND t.cabinNo = qcl.cabinNo
	) t
WHERE
	1 = 1 