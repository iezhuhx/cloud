/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-11-12 17:06:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_table
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table` (
  `id` int(11) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sys_zuul_route` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'router Id',
  `path` varchar(255) NOT NULL COMMENT '路由路径',
  `service_id` varchar(255) NOT NULL COMMENT '服务名称',
  `url` varchar(255) DEFAULT NULL COMMENT 'url代理',
  `strip_prefix` char(1) DEFAULT '1' COMMENT '转发去掉前缀',
  `retryable` char(1) DEFAULT '1' COMMENT '是否重试',
  `enabled` char(1) DEFAULT '1' COMMENT '是否启用',
  `sensitiveHeaders_list` varchar(255) DEFAULT NULL COMMENT '敏感请求头',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='动态路由配置表';

/*
Navicat MySQL Data Transfer

Source Server         : 66.103
Source Server Version : 50615
Source Host           : 10.70.66.103:3306
Source Database       : centralized_auth

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2020-11-10 12:20:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cyb_tab2
-- ----------------------------
DROP TABLE IF EXISTS `cyb_tab2`;
CREATE TABLE `cyb_tab2` (
  `id` varchar(100) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_company
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_company`;
CREATE TABLE `t_sys_company` (
  `id` int(50) NOT NULL COMMENT '主键',
  `subcompanyname` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `companyid` int(100) DEFAULT NULL COMMENT '公司描述',
  `supsubcomid` int(32) NOT NULL DEFAULT '0' COMMENT '上级公司ID',
  `showorder` int(11) DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_department
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_department`;
CREATE TABLE `t_sys_department` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `departmentname` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `subcompanyid1` int(32) DEFAULT NULL COMMENT '公司ID',
  `supdepid` int(32) DEFAULT NULL COMMENT '上级部门ID',
  `showorder` int(11) DEFAULT '0' COMMENT '显示顺序',
  PRIMARY KEY (`id`),
  KEY `fk_department_company` (`subcompanyid1`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_employee`;
CREATE TABLE `t_sys_employee` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `loginid` varchar(20) DEFAULT NULL COMMENT '员工登陆账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `lastname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `departmentid` int(32) DEFAULT NULL COMMENT '部门ID',
  `subcompanyid1` int(32) DEFAULT NULL COMMENT '公司ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1883 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) DEFAULT NULL,
  `client_ip` varchar(255) DEFAULT NULL,
  `visit_time` varchar(50) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `param` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1086434 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` bigint(20) DEFAULT NULL,
  `cretatePerson` varchar(50) DEFAULT NULL,
  `isLeaf` int(11) DEFAULT NULL,
  `menuDesc` varchar(200) DEFAULT NULL,
  `menuName` varchar(100) DEFAULT NULL,
  `modifyPerson` varchar(50) DEFAULT NULL,
  `modifyTime` bigint(20) DEFAULT NULL,
  `ordor` int(11) DEFAULT NULL,
  `parentId` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `description` varchar(200) NOT NULL,
  `rolename` varchar(100) NOT NULL,
  `groupname` varchar(100) DEFAULT NULL COMMENT '角色分组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123563 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `menuid` int(50) NOT NULL,
  `roleid` int(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9202 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `empno` varchar(200) NOT NULL,
  `isEffect` int(11) DEFAULT NULL,
  `lastLoginIp` varchar(30) DEFAULT NULL,
  `loginTime` bigint(20) DEFAULT NULL,
  `loginIp` varchar(30) DEFAULT NULL,
  `loginSum` int(11) DEFAULT NULL,
  `operateID` varchar(255) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `lastLoginTime` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1270 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(50) NOT NULL,
  `userid` int(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1476 DEFAULT CHARSET=utf8;
