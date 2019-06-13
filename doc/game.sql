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
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `accountId` varchar(255) NOT NULL COMMENT '主键',
  `accountData` varchar(1000) DEFAULT NULL COMMENT '对象的json格式',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES ('a1007', '{\"acountId\":\"a1007\",\"acountName\":\"test07\",\"scene\":{\"playerSet\":[{\"$ref\":\"$\"}],\"neighbors\":\"s2002\",\"npcSet\":[],\"mapId\":\"s2001\",\"sceneName\":\"区庄\"}}');
INSERT INTO `player` VALUES ('a1008', '{\"acountId\":\"a1008\",\"acountName\":\"test1008\"}');
