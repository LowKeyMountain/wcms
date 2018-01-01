  select * from (

SELECT
	t.*, CASE
WHEN round(qcl.clearance, 1) IS NULL THEN
	0
ELSE
	round(qcl.clearance, 1)
END AS 'clearance'
FROM
	(
		SELECT
			z_xcqk.*, CASE
		WHEN round(c_syl.finished, 1) IS NULL THEN
			0
		ELSE
			round(c_syl.finished, 1)
		END AS 'finished',
		CASE
	WHEN (
		round(
			z_xcqk.total - c_syl.finished,
			1
		)
	) IS NULL THEN
		z_xcqk.total
	ELSE
		(
			round(
				z_xcqk.total - c_syl.finished,
				1
			)
		)
	END AS 'remainder'
	FROM
(
	SELECT
		cabin.task_id,
		cabin.cargo_type AS 'cargoName',
		cabin.cabin_no AS 'cabinNo',
		cabin.cargo_id AS 'cargoId',
		cabin.preunloading AS 'total',
		cabin.start_position AS 'startPosition',
		cabin.end_position AS 'endPosition',
		cabin. STATUS AS 'status',
		task.begin_time,
		task.end_time,
		cabin.clear_time
	FROM
		tab_task task
	LEFT JOIN (
		SELECT
			cabin.*, cargo.task_id,
			cargo.cargo_type
		FROM
			tab_cabin cabin
		LEFT JOIN tab_cargo cargo ON cabin.cargo_id = cargo.id
	) cabin ON task.id = cabin.task_id
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
	cabin.cargo_type AS 'cargoName',
		cabin.cabin_no AS 'cabinNo',
		cabin.cargo_id AS 'cargoId',
		cabin.preunloading AS 'total',
		cabin.start_position AS 'startPosition',
		cabin.end_position AS 'endPosition',
		cabin. STATUS AS 'status',
		task.begin_time,
		CASE
	WHEN task.end_time IS NULL THEN
		CURRENT_TIMESTAMP ()
	ELSE
		task.end_time
	END AS 'end_time',
	cabin.clear_time
FROM
	tab_task task
LEFT JOIN (
	SELECT
		cabin.*, cargo.task_id,
		cargo.cargo_type
	FROM
		tab_cabin cabin
	LEFT JOIN tab_cargo cargo ON cabin.cargo_id = cargo.id
) cabin ON task.id = cabin.task_id
) o
		LEFT JOIN (
			SELECT
				t1.Cmsid,
				t1.Time,
				t1.unloaderMove,
				t1.OneTask
			FROM
				tab_unloader_1 t1
			UNION
				SELECT
					t2.Cmsid,
					t2.Time,
					t2.unloaderMove,
					t2.OneTask
				FROM
					tab_unloader_2 t2
				UNION
					SELECT
						t3.Cmsid,
						t3.Time,
						t3.unloaderMove,
						t3.OneTask
					FROM
						tab_unloader_3 t3
					UNION
						SELECT
							t4.Cmsid,
							t4.Time,
							t4.unloaderMove,
							t4.OneTask
						FROM
							tab_unloader_4 t4
						UNION
							SELECT
								t5.Cmsid,
								t5.Time,
								t5.unloaderMove,
								t5.OneTask
							FROM
								tab_unloader_5 t5
							UNION
								SELECT
									t6.Cmsid,
									t6.Time,
									t6.unloaderMove,
									t6.OneTask
								FROM
									tab_unloader_6 t6
		) c ON o.begin_time <= c.Time
		AND o.end_time >= c.Time
		AND o.startPosition < c.unloaderMove
		AND o.endPosition > c.unloaderMove
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
		(SELECT
		cabin.task_id,
		cabin.cabin_no AS 'cabinNo',
		cabin.cargo_id AS 'cargoId',
		cabin.preunloading AS 'total',
		cabin.start_position AS 'startPosition',
		cabin.end_position AS 'endPosition',
		cabin. STATUS AS 'status',
		task.begin_time,
		CASE
	WHEN task.end_time IS NULL THEN
		CURRENT_TIMESTAMP ()
	ELSE
		task.end_time
	END AS 'end_time',
	cabin.clear_time
FROM
	tab_task task
LEFT JOIN (
	SELECT
		cabin.*, cargo.task_id,
		cargo.cargo_type
	FROM
		tab_cabin cabin
	LEFT JOIN tab_cargo cargo ON cabin.cargo_id = cargo.id
) cabin ON task.id = cabin.task_id
			WHERE
				cabin.clear_time IS NOT NULL) o
	LEFT JOIN (
		SELECT
			t1.Cmsid,
			t1.Time,
			t1.unloaderMove,
			t1.OneTask
		FROM
			tab_unloader_1 t1
		UNION
			SELECT
				t2.Cmsid,
				t2.Time,
				t2.unloaderMove,
				t2.OneTask
			FROM
				tab_unloader_2 t2
			UNION
				SELECT
					t3.Cmsid,
					t3.Time,
					t3.unloaderMove,
					t3.OneTask
				FROM
					tab_unloader_3 t3
				UNION
					SELECT
						t4.Cmsid,
						t4.Time,
						t4.unloaderMove,
						t4.OneTask
					FROM
						tab_unloader_4 t4
					UNION
						SELECT
							t5.Cmsid,
							t5.Time,
							t5.unloaderMove,
							t5.OneTask
						FROM
							tab_unloader_5 t5
						UNION
							SELECT
								t6.Cmsid,
								t6.Time,
								t6.unloaderMove,
								t6.OneTask
							FROM
								tab_unloader_6 t6
	) c ON o.clear_time <= c.Time
	AND o.end_time >= c.Time
	AND o.startPosition < c.unloaderMove
	AND o.endPosition > c.unloaderMove
	GROUP BY
		o.cabinNo,
		o.task_id
) qcl ON t.task_id = qcl.task_id
AND t.cabinNo = qcl.cabinNo  

) t where 1=1  