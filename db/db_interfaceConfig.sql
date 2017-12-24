/*
Navicat MySQL Data Transfer

Source Server         : 云端
Source Server Version : 50638
Source Host           : 47.95.205.78:3306
Source Database       : wcms

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2017-12-19 20:47:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for query_interface_config
-- ----------------------------
DROP TABLE IF EXISTS `query_interface_config`;
CREATE TABLE `query_interface_config` (
  `fuction_type` varchar(100) NOT NULL,
  `param_check` varchar(500) DEFAULT NULL,
  `sql` varchar(2000) DEFAULT NULL,
  `extend_config` varchar(2000) DEFAULT NULL,
  `notes` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of query_interface_config
-- ----------------------------
INSERT INTO `query_interface_config` VALUES ('FN_001_1', '{\"order\":\"section|ASC、DESC\",\"sort\":\"section|\",\"page\":\"number|1\",\"rows\":\"number|50\"}', 'SELECT t.id AS \'id\', s.ship_cname AS \'shipName\', s.ship_ename AS \'shipEname\',  s.imo_no AS \'imoNo\', t.berth AS \'berthName\' FROM tab_task t LEFT JOIN tab_ship s ON t.`ship_id` = s.`id` WHERE t.`status` = 0', '{\"isPaging\":\"0\",\"queryFields\":{\"id\":\"string\",\"shipName\":\"string\",\"shipEname\":\"string\",\"imoNo\":\"string\",\"berthName\":\"string\"}}', '获取船舶作业列表（预靠船舶）');
INSERT INTO `query_interface_config` VALUES ('FN_001_2', '{\"order\":\"section|ASC、DESC\",\"sort\":\"section|\",\"page\":\"number|1\",\"rows\":\"number|50\"}', 'SELECT t.id AS \'id\', s.ship_cname AS \'shipName\', s.ship_ename AS \'shipEname\',  s.imo_no AS \'imoNo\', t.berth AS \'berthName\' FROM tab_task t LEFT JOIN tab_ship s ON t.`ship_id` = s.`id`  WHERE t.`status` = 1', '{\"isPaging\":\"0\",\"queryFields\":{\"id\":\"string\",\"shipName\":\"string\",\"shipEname\":\"string\",\"imoNo\":\"string\",\"berthName\":\"string\"}}', '获取船舶作业列表（作业船舶）');
INSERT INTO `query_interface_config` VALUES ('FN_001_3', '{\"order\":\"section|ASC、DESC\",\"sort\":\"section|\",\"page\":\"number|1\",\"rows\":\"number|50\"}', 'SELECT t.id AS \'id\', s.ship_cname AS \'shipName\', s.ship_ename AS \'shipEname\',  s.imo_no AS \'imoNo\', t.berth AS \'berthName\' FROM tab_task t LEFT JOIN tab_ship s ON t.`ship_id` = s.`id`  WHERE t.`status` = 2', '{\"isPaging\":\"0\",\"queryFields\":{\"id\":\"string\",\"shipName\":\"string\",\"shipEname\":\"string\",\"imoNo\":\"string\",\"berthName\":\"string\"}}', '获取船舶作业列表（离港船舶）');
INSERT INTO `query_interface_config` VALUES ('FN_004', '{\"order\":\"section|ASC、DESC\",\"sort\":\"section|CABINNO\",\"page\":\"number|1\",\"rows\":\"number|50\"}', ' select t.*, c.cargo_type as \'cargoName\' from (SELECT cabin.task_id , cabin.cabin_no AS \'cabinNo\', \r\ncabin.cargo_id AS \'cargoId\', \r\ncabin.preunloading AS \'total\', 0 AS \'finished\', cabin.preunloading AS \'remainder\',0 AS \r\n\'clearance\', cabin.start_position AS \'startPosition\', cabin.end_position AS \r\n\'endPosition\', cabin.status AS \'status\' FROM tab_task task LEFT JOIN \r\ntab_cabin cabin ON task.id = cabin.task_id ) t LEFT JOIN tab_cargo c ON t.cargoId = c.id where 1=1 ', '{\"isPaging\": \"0\", \"queryFields\": {\"cabinNo\": \"integer\", \"cargoName\": \"string\",\"cargoId\":\"integer\",\"total\": \"double\", \"finished\": \"double\", \"remainder\": \"double\", \"clearance\": \"double\", \"startPosition\": \"double\", \"endPosition\": \"double\", \"status\": \"string\"}}', '获取船舱信息');
INSERT INTO `query_interface_config` VALUES ('FN_003', '{}', 'SELECT s.id AS \'id\', s.ship_cname AS \'shipName\', s.ship_ename AS \'shipEname\', s.imo_no AS \'imoNo\', t.berthing_time AS \'berthingTime\', t.departure_time AS \'departureTime\', t.begin_time AS \'beginTime\', t.end_time AS \'endTime\', s.build_date AS \'buildDate\', s.length AS \'length\', s.breadth AS \'breadth\', s.moulded_depth AS \'depth\', s.cabin_num AS \'cabinNum\', s.hatch AS \'hatch\', s.wire AS \'cable\', t.freeboard_depth AS \'freeboardHeight\', t.depth AS \'draft\', t.cargo_load AS \'load\', s.special_cabin_type AS \'cabintype\' FROM tab_task t LEFT JOIN   tab_ship s  ON s.id = t.ship_id WHERE 1=1', '{\"isPaging\": \"1\", \"queryFields\": { \"id\": \"string\", \"shipName\": \"string\", \"shipEname\": \"string\", \"imoNo\": \"string\", \"berthingTime\": \"time\", \"departureTime\": \"time\", \"beginTime\": \"time\", \"endTime\": \"time\", \"buildDate\": \"date\", \"length\": \"double\", \"breadth\": \"double\", \"depth\": \"double\", \"cabinNum\": \"integer\", \"hatch\": \"integer\", \"cable\": \"string\", \"freeboardHeight\": \"string\", \"draft\": \"string\", \"load\": \"double\", \"cabintype\": \"string\" }}', '获取船舶详情');
INSERT INTO `query_interface_config` VALUES ('FN_002', '{}', ' select t.*, c.cargo_type as \'cargoName\' from (SELECT cabin.task_id , cabin.cabin_no \r\nAS \'cabinNo\', cabin.`cargo_id` AS \'cargoId\', \r\ncabin.preunloading AS \'total\', 0 AS \'finished\', cabin.preunloading AS \'remainder\',0 AS \r\n\'clearance\', cabin.status AS \'status\' FROM tab_task task LEFT JOIN tab_cabin\r\n cabin ON task.id = cabin.task_id) t , tab_cargo c where t.task_id = c.task_id and t.cargoId = c.id ', '{\"isPaging\": \"1\", \"queryFields\": {\"cabinNo\": \"integer\", \"cargoName\": \"string\", \"cargoId\": \"integer\", \"total\": \"double\", \"finished\": \"double\", \"remainder\": \"double\", \"clearance\": \"double\", \"status\": \"string\"}}', '获取所有船舱位置信息');
INSERT INTO `query_interface_config` VALUES ('FN_005', '{}', 'SELECT cargo.cargo_type AS \'cargoType\', cargo.cargo_category AS \'cargoCategory\', cargo.loading_port AS \'loadingPort\', cargo.quality AS \'quality\', cargo.moisture AS \'moisture\', cargo.`cargo_owner` AS \'owner\', cargo.stowage AS \'stowage\', cargo.warehouse AS \'warehouse\' FROM tab_task task LEFT JOIN tab_cargo cargo ON task.id = cargo.task_id', '{\"isPaging\": \"1\",\"queryFields\": {\"cargoType\": \"string\",\"cargoCategory\": \"string\",\"loadingPort\": \"string\",\"quality\": \"string\",\"moisture\": \"string\",\"owner\": \"string\",\"stowage\": \"double\",\"warehouse\": \"string\"}}', '获取货物信息');
