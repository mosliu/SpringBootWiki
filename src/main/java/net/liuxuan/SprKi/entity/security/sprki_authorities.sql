/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : spring

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-03-03 14:32:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sprki_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sprki_authorities`;
CREATE TABLE `sprki_authorities` (
  `id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `authority` varchar(30) NOT NULL,
  PRIMARY KEY (`username`,`authority`),
  CONSTRAINT `FK_hpaossout1kdwdp73r985jndj` FOREIGN KEY (`username`) REFERENCES `sprki_user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sprki_authorities
-- ----------------------------
INSERT INTO `sprki_authorities` VALUES ('3', 'aaa', 'ROLE_AA');
INSERT INTO `sprki_authorities` VALUES ('0', 'admin', 'ROLE_ADMIN');
INSERT INTO `sprki_authorities` VALUES ('1', 'admin', 'ROLE_USER');
INSERT INTO `sprki_authorities` VALUES ('2', 'user', 'ROLE_USER');

-- ----------------------------
-- Table structure for sprki_user
-- ----------------------------
DROP TABLE IF EXISTS `sprki_user`;
CREATE TABLE `sprki_user` (
  `username` varchar(30) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `regdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `username_alias` varchar(30) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sprki_user
-- ----------------------------
INSERT INTO `sprki_user` VALUES ('aaa', 'aaa', '', '2016-02-25 08:56:04', '1');
INSERT INTO `sprki_user` VALUES ('admin', 'admin', '', '2016-02-17 00:00:00', 'admin');
INSERT INTO `sprki_user` VALUES ('bbb', 'bbb', '', '0000-00-00 00:00:00', 'b');
INSERT INTO `sprki_user` VALUES ('user', 'user', '', '2016-02-17 00:00:00', 'user');
