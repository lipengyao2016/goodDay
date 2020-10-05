/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : goodday

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2017-12-07 17:41:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` varchar(16) NOT NULL COMMENT '角色编号',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_alias` varchar(30) NOT NULL COMMENT '角色别名',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1709120927180000', '超级管理员', 'root');
INSERT INTO `sys_role` VALUES ('1709120927180001', '系统管理员', 'admin');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(16) NOT NULL COMMENT '用户编号',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `user_login_name` varchar(30) NOT NULL COMMENT '用户登录名',
  `user_password` varchar(100) NOT NULL COMMENT '用户密码',
  `user_create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `user_update_time` datetime DEFAULT NULL COMMENT '修改日期',
  `user_status` varchar(1) NOT NULL DEFAULT '1' COMMENT '用户状态 0-锁定 1-正常',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1709121331320000', '超级管理员', 'root', 'root123456', '2017-12-07 13:33:01', null, '1');
INSERT INTO `sys_user` VALUES ('1709121331320001', '管理员', 'admin', 'admin123456', '2017-12-07 13:31:42', null, '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(16) NOT NULL COMMENT '主键编号',
  `user_id` varchar(16) NOT NULL COMMENT '用户编号',
  `role_id` varchar(16) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1709121333290007', '1709121331320001', '1709120927180001');
INSERT INTO `sys_user_role` VALUES ('1709121333290009', '1709121331320000', '1709120927180000');
