/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : game

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-07 20:49:43
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
INSERT INTO `account` VALUES ('a1007', '{\"countId\":\"a1007\",\"countName\":\"test07\",\"scene\":{\"accountSet\":[{\"$ref\":\"$\"}],\"neighbors\":\"s2002\",\"npcSet\":[],\"sceneId\":\"s2001\",\"sceneName\":\"区庄\"}}');
INSERT INTO `account` VALUES ('a1008', '{\"countId\":\"a1008\",\"countName\":\"test1008\"}');
INSERT INTO `account` VALUES ('a1009', '{\"countId\":\"a1009\",\"countName\":\"test009\",\"scene\":{\"accountSet\":[],\"npcSet\":[],\"sceneId\":\"s1001\"}}');
INSERT INTO `account` VALUES ('a1010', '{\"countId\":\"a1010\",\"countName\":\"test1010\",\"scene\":{\"accountSet\":[],\"npcSet\":[],\"sceneId\":\"s1001\"}}');
INSERT INTO `account` VALUES ('a1011', '{\"countId\":\"a1011\",\"countName\":\"test011\",\"scene\":{\"accountSet\":[],\"npcSet\":[],\"sceneId\":\"s1001\"}}');
