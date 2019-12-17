/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : demo_hrm

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-11-18 19:10:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dep
-- ----------------------------
DROP TABLE IF EXISTS `dep`;
CREATE TABLE `dep` (
  `depId` int(10) NOT NULL AUTO_INCREMENT,
  `depLeader` varchar(255) NOT NULL,
  `depName` varchar(255) NOT NULL,
  PRIMARY KEY (`depId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dep
-- ----------------------------
INSERT INTO `dep` VALUES ('1', '小何', '生活部');
INSERT INTO `dep` VALUES ('2', '小何', '纪检部');
INSERT INTO `dep` VALUES ('3', '小何', '后勤部');
INSERT INTO `dep` VALUES ('4', '小何', '人事部');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `empId` int(10) NOT NULL AUTO_INCREMENT,
  `empName` varchar(255) NOT NULL,
  `empEmail` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `depId` int(10) NOT NULL,
  `hiredate` date NOT NULL,
  `phone` varchar(255) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES ('6', '小甘', '972193026@qq.com', '女', '3', '2019-08-10', '13000009999', '23141241');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `authority` int(1) NOT NULL DEFAULT '1' COMMENT '用户类型:(1.为普通用户,2.为管理员)',
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('b7b8d4cdaa1148b3aeeca976d329c17b', 'control', '管理员', '/imgs/icon/user_default_icon.jpg', 'WdIavbdvL176QWlwYiXoJg==', '14236566542', '江西南昌', '2', '1057559465@qq.com');
