/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : bmsys

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 05/03/2020 21:18:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bmsys_menu
-- ----------------------------
CREATE TABLE `bmsys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) DEFAULT '' COMMENT '权限标识',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

INSERT INTO `bmsys`.`bmsys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `remark`) VALUES (1, '综合管理', 0, 0, '#', 'C', '0', 'manage', '');
INSERT INTO `bmsys`.`bmsys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `remark`) VALUES (100, '用户管理', 1, 0, '/user/list', 'M', '0', 'userManage', '');
INSERT INTO `bmsys`.`bmsys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `remark`) VALUES (101, '用户新增', 2, 0, '/user/register', 'F', '0', 'user:add', '');
INSERT INTO `bmsys`.`bmsys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `remark`) VALUES (102, '用户删除', 2, 0, '#', 'F', '0', 'user:del', '');
INSERT INTO `bmsys`.`bmsys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `remark`) VALUES (103, '用户修改', 2, 0, '#', 'F', '0', 'user:upd', '');
INSERT INTO `bmsys`.`bmsys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `url`, `menu_type`, `visible`, `perms`, `remark`) VALUES (104, '用户查询', 2, 0, '#', 'F', '0', 'user:get', '');


-- ----------------------------
-- Table structure for bmsys_role
-- ----------------------------
DROP TABLE IF EXISTS `bmsys_role`;
CREATE TABLE `bmsys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称(中文)',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色字符串',
  `role_sort` int(4) NULL DEFAULT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `role_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色类型,G 角色组，R 角色',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父id',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

INSERT INTO `bmsys`.`bmsys_role` (`role_name`, `role_key`, `role_sort`, `status`, `role_type`, `parent_id`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('超级管理员', 'ADMIN', 1, '0', 'R', 0, '', NULL, '', NULL, '');
INSERT INTO `bmsys`.`bmsys_role` (`role_name`, `role_key`, `role_sort`, `status`, `role_type`, `parent_id`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES ('普通用户', 'COMMON', 2, '0', 'R', 0, '', NULL, '', NULL, '');


-- ----------------------------
-- Table structure for bmsys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `bmsys_role_menu`;
CREATE TABLE `bmsys_role_menu`  (
  `role_menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

INSERT INTO `bmsys`.`bmsys_role_menu` (`role_id`, `menu_id`) VALUES ( 1, 1);
INSERT INTO `bmsys`.`bmsys_role_menu` (`role_id`, `menu_id`) VALUES ( 1, 100);
INSERT INTO `bmsys`.`bmsys_role_menu` (`role_id`, `menu_id`) VALUES ( 1, 101);
INSERT INTO `bmsys`.`bmsys_role_menu` (`role_id`, `menu_id`) VALUES ( 1, 102);
INSERT INTO `bmsys`.`bmsys_role_menu` (`role_id`, `menu_id`) VALUES ( 1, 103);
INSERT INTO `bmsys`.`bmsys_role_menu` (`role_id`, `menu_id`) VALUES ( 1, 104);


-- ----------------------------
-- Table structure for bmsys_user
-- ----------------------------
CREATE TABLE `bmsys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) NOT NULL COMMENT '姓名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `gender` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 ）',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `unit` varchar(255) DEFAULT NULL COMMENT '所属公司',
  `department` varchar(255) DEFAULT NULL COMMENT '部门',
  `status` varchar(255) DEFAULT '0' COMMENT '账号状态 0正常，1停用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否有效，1有效，0无效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for bmsys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `bmsys_user_role`;
CREATE TABLE `bmsys_user_role`  (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

INSERT INTO `bmsys`.`bmsys_user_role` (`user_id`, `role_id`) VALUES (2, 1);

SET FOREIGN_KEY_CHECKS = 1;
