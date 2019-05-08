/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : game

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-08 10:19:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `accountId` varchar(255) NOT NULL COMMENT '主键',
  `accountData` varchar(1000) DEFAULT NULL COMMENT '对象的json格式',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('a1007', '{\"acountId\":\"a1007\",\"acountName\":\"test07\",\"scene\":{\"accountSet\":[{\"$ref\":\"$\"}],\"neighbors\":\"s2002\",\"npcSet\":[],\"sceneId\":\"s2001\",\"sceneName\":\"区庄\"}}');
INSERT INTO `account` VALUES ('a1008', '{\"acountId\":\"a1008\",\"acountName\":\"test1008\"}');