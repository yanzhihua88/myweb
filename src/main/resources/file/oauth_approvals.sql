/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.176
Source Server Version : 50637
Source Host           : 192.168.2.176:3306
Source Database       : shengye_dhl

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-05-30 14:22:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userid` varchar(256) DEFAULT NULL,
  `clientid` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresat` timestamp NULL DEFAULT NULL,
  `lastmodifiedat` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------
