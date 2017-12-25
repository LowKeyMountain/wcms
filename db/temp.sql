select c.OneTask as 'ÕÍ≥…¡ø', o.*  from 

( 

select t.*, c.cargo_type as 'cargoName' from (SELECT cabin.task_id , cabin.cabin_no AS 'cabinNo', 
cabin.cargo_id AS 'cargoId', cabin.preunloading AS 'total', 0 AS 'finished', cabin.preunloading AS 
'remainder',0 AS 'clearance', cabin.start_position AS 'startPosition', cabin.end_position AS 'endPosition',
 cabin.status AS 'status', task.begin_time, task.end_time, cabin.clear_time FROM tab_task task LEFT JOIN
 tab_cabin cabin ON task.id = cabin.task_id ) t LEFT 
JOIN tab_cargo c ON t.cargoId = c.id 

) o

LEFT JOIN

(
select t1.Cmsid, t1.Time, t1.unloaderMove, t1.OneTask from tab_unloader_1 t1
UNION
select t2.Cmsid, t2.Time, t2.unloaderMove, t2.OneTask from tab_unloader_2 t2
UNION
select t3.Cmsid, t3.Time, t3.unloaderMove, t3.OneTask from tab_unloader_3 t3
UNION
select t4.Cmsid, t4.Time, t4.unloaderMove, t4.OneTask from tab_unloader_4 t4
UNION
select t5.Cmsid, t5.Time, t5.unloaderMove, t5.OneTask from tab_unloader_5 t5
UNION
select t6.Cmsid, t6.Time, t6.unloaderMove, t6.OneTask from tab_unloader_6 t6
) c

ON

o.begin_time <= c.Time
and 
o.startPosition <= c.unloaderMove
and
o.endPosition >= c.unloaderMove

