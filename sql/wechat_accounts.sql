/*
Navicat MySQL Data Transfer

Source Server         : wechat
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : wechat_accounts

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-10-29 21:59:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for income_detail
-- ----------------------------
DROP TABLE IF EXISTS `income_detail`;
CREATE TABLE `income_detail` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `income` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `date` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `month_account_id` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `openid` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL,
  `location_x` varchar(255) DEFAULT NULL,
  `location_y` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL COMMENT '地理位置信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for month_account
-- ----------------------------
DROP TABLE IF EXISTS `month_account`;
CREATE TABLE `month_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL COMMENT '??id',
  `month` varchar(255) DEFAULT NULL COMMENT '??',
  `income` varchar(255) DEFAULT NULL COMMENT '??',
  `outlay` varchar(255) DEFAULT NULL COMMENT '??',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for outlay_detail
-- ----------------------------
DROP TABLE IF EXISTS `outlay_detail`;
CREATE TABLE `outlay_detail` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `outlay` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `month_account_id` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
