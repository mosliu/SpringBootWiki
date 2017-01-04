/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : spring

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-12-15 11:43:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------


-- ----------------------------
-- Table structure for sprki_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sprki_authorities`;
CREATE TABLE `sprki_authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(30) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hpaossout1kdwdp73r985jndj` (`username`),
  CONSTRAINT `FK_hpaossout1kdwdp73r985jndj` FOREIGN KEY (`username`) REFERENCES `sprki_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_authorities
-- ----------------------------
INSERT INTO `sprki_authorities` VALUES ('1', 'ROLE_USER', 'admin');
INSERT INTO `sprki_authorities` VALUES ('2', 'ROLE_USER', 'user');
INSERT INTO `sprki_authorities` VALUES ('3', 'ROLE_AA', 'aaa');
INSERT INTO `sprki_authorities` VALUES ('4', 'ROLE_ADMIN', 'admin');

-- ----------------------------
-- Table structure for sprki_banner
-- ----------------------------
DROP TABLE IF EXISTS `sprki_banner`;
CREATE TABLE `sprki_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_banner
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_cms_category
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_category`;
CREATE TABLE `sprki_cms_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` bit(1) NOT NULL,
  `english_name` varchar(50) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `page_size` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mcq3s46so9tuxa4wi0difayy` (`parent_id`),
  CONSTRAINT `FK_mcq3s46so9tuxa4wi0difayy` FOREIGN KEY (`parent_id`) REFERENCES `sprki_cms_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_category
-- ----------------------------
INSERT INTO `sprki_cms_category` VALUES ('1', '\0', 'BaseKnowledge', '基础知识', '10', '/category', null);
INSERT INTO `sprki_cms_category` VALUES ('2', '\0', 'dataAssociat', '数据相关', '10', '/category2', null);
INSERT INTO `sprki_cms_category` VALUES ('3', '\0', 'electrical', '电气相关', '10', '/cat_elec', null);
INSERT INTO `sprki_cms_category` VALUES ('4', '\0', 'software', '软件相关', '10', '/cat_software', null);

-- ----------------------------
-- Table structure for sprki_cms_comment
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_comment`;
CREATE TABLE `sprki_cms_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_kind` varchar(20) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `commentip` varchar(40) DEFAULT NULL,
  `comment_title` varchar(40) DEFAULT NULL,
  `disabled` bit(1) NOT NULL,
  `publish_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(30) NOT NULL,
  `content` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4uy1in8gbfaihrknt96rlyxft` (`author`),
  KEY `FK_qqurak0flldy5wiqlls01vrao` (`content`),
  KEY `FK_fq2hx5s983ig1v4hx448ly0rv` (`parent`),
  CONSTRAINT `FK_4uy1in8gbfaihrknt96rlyxft` FOREIGN KEY (`author`) REFERENCES `sprki_user` (`username`),
  CONSTRAINT `FK_fq2hx5s983ig1v4hx448ly0rv` FOREIGN KEY (`parent`) REFERENCES `sprki_cms_comment` (`id`),
  CONSTRAINT `FK_qqurak0flldy5wiqlls01vrao` FOREIGN KEY (`content`) REFERENCES `sprki_cms_content` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_comment
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_cms_content
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_content`;
CREATE TABLE `sprki_cms_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `clicks` int(11) DEFAULT NULL,
  `comments_count` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `diggs` int(11) DEFAULT NULL,
  `disabled` bit(1) NOT NULL,
  `full_title` varchar(1000) DEFAULT NULL,
  `info_text` longtext NOT NULL,
  `is_copied` bit(1) NOT NULL,
  `last_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `meta_keywords` varchar(255) DEFAULT NULL,
  `publish_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `score` int(11) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `source_url` varchar(200) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `subtitle` varchar(200) DEFAULT NULL,
  `title` varchar(200) NOT NULL,
  `author` varchar(30) NOT NULL,
  `category` bigint(20) NOT NULL,
  `last_update_user` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p7db0eu9e2dex594s13ywbnl0` (`author`),
  KEY `FK_92v5x3fsink8uq7968do91xr8` (`category`),
  KEY `FK_hqpe8d0f7dsgfdmq1n9pafgii` (`last_update_user`),
  CONSTRAINT `FK_92v5x3fsink8uq7968do91xr8` FOREIGN KEY (`category`) REFERENCES `sprki_cms_category` (`id`),
  CONSTRAINT `FK_hqpe8d0f7dsgfdmq1n9pafgii` FOREIGN KEY (`last_update_user`) REFERENCES `sprki_user` (`username`),
  CONSTRAINT `FK_p7db0eu9e2dex594s13ywbnl0` FOREIGN KEY (`author`) REFERENCES `sprki_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_content
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_cms_contentfaq
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_contentfaq`;
CREATE TABLE `sprki_cms_contentfaq` (
  `answer` longtext NOT NULL,
  `question` longtext NOT NULL,
  `question_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `faq_id` bigint(20) NOT NULL,
  `department` bigint(20) DEFAULT NULL,
  `devices` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`faq_id`),
  KEY `FK_hfo0f762jhntnq6ppm4ejqkn4` (`department`),
  KEY `FK_4gl4gcpkq0vo2rbk4chj6gf2d` (`devices`),
  CONSTRAINT `FK_4gl4gcpkq0vo2rbk4chj6gf2d` FOREIGN KEY (`devices`) REFERENCES `sprki_labthink_devices` (`id`),
  CONSTRAINT `FK_hfo0f762jhntnq6ppm4ejqkn4` FOREIGN KEY (`department`) REFERENCES `sprki_labthink_department` (`id`),
  CONSTRAINT `FK_jyqdfj6eiu9c2c3t1xcdf6axq` FOREIGN KEY (`faq_id`) REFERENCES `sprki_cms_content` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_contentfaq
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_cms_contents_tags
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_contents_tags`;
CREATE TABLE `sprki_cms_contents_tags` (
  `content_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  PRIMARY KEY (`content_id`,`tags_id`),
  KEY `FK_nj1g0b3kjw5tbrbdrr07o14mw` (`tags_id`),
  CONSTRAINT `FK_2ecrcq6fflkk5nadlf846v93t` FOREIGN KEY (`content_id`) REFERENCES `sprki_cms_content` (`id`),
  CONSTRAINT `FK_nj1g0b3kjw5tbrbdrr07o14mw` FOREIGN KEY (`tags_id`) REFERENCES `sprki_cms_tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_contents_tags
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_cms_content_ticket
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_content_ticket`;
CREATE TABLE `sprki_cms_content_ticket` (
  `resolved` bit(1) NOT NULL,
  `resolved_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `submit_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ticket_id` bigint(20) NOT NULL,
  `department` bigint(20) DEFAULT NULL,
  `devices` bigint(20) DEFAULT NULL,
  `question` longtext NOT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `FK_mp8o8or6wh2gwmj7sbxlyyx4l` (`department`),
  KEY `FK_7jt0y2uvy1u3arcutbs09wx6i` (`devices`),
  CONSTRAINT `FK_7jt0y2uvy1u3arcutbs09wx6i` FOREIGN KEY (`devices`) REFERENCES `sprki_labthink_devices` (`id`),
  CONSTRAINT `FK_mp8o8or6wh2gwmj7sbxlyyx4l` FOREIGN KEY (`department`) REFERENCES `sprki_labthink_department` (`id`),
  CONSTRAINT `FK_qokkrqfpfxl8nk3060sky9yw0` FOREIGN KEY (`ticket_id`) REFERENCES `sprki_cms_content` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_content_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_cms_tags
-- ----------------------------
DROP TABLE IF EXISTS `sprki_cms_tags`;
CREATE TABLE `sprki_cms_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `tagtype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_cms_tags
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_labthink_department
-- ----------------------------
DROP TABLE IF EXISTS `sprki_labthink_department`;
CREATE TABLE `sprki_labthink_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(80) NOT NULL,
  `department_namecn` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_labthink_department
-- ----------------------------
INSERT INTO `sprki_labthink_department` VALUES ('1', '售后', '售后');
INSERT INTO `sprki_labthink_department` VALUES ('2', '实验室', '实验室');
INSERT INTO `sprki_labthink_department` VALUES ('3', '研发制造', '研发制造');
INSERT INTO `sprki_labthink_department` VALUES ('7', '生产', '生产');
INSERT INTO `sprki_labthink_department` VALUES ('8', '销售部', '销售部');

-- ----------------------------
-- Table structure for sprki_labthink_devices
-- ----------------------------
DROP TABLE IF EXISTS `sprki_labthink_devices`;
CREATE TABLE `sprki_labthink_devices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `devicename` varchar(100) NOT NULL,
  `devicenamecn` varchar(100) NOT NULL,
  `devicenameen` varchar(100) DEFAULT NULL,
  `device_kind` bigint(20) NOT NULL,
  `device_type` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c4hqbke2teisuu7lbpjtcpxbj` (`device_kind`),
  KEY `FK_hvlqj0cn7hnu9tkg0udtocnun` (`device_type`),
  CONSTRAINT `FK_c4hqbke2teisuu7lbpjtcpxbj` FOREIGN KEY (`device_kind`) REFERENCES `sprki_labthink_device_kind` (`id`),
  CONSTRAINT `FK_hvlqj0cn7hnu9tkg0udtocnun` FOREIGN KEY (`device_type`) REFERENCES `sprki_labthink_device_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=421 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_labthink_devices
-- ----------------------------
INSERT INTO `sprki_labthink_devices` VALUES ('279', 'XLW-B', '智能电子拉力试验机', '智能电子拉力试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('280', 'BLD-200N', '电子剥离试验机', '电子剥离试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('281', 'XLW', '智能电子拉力试验机', '智能电子拉力试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('282', 'XLW-M', '智能电子拉力试验机', '智能电子拉力试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('283', 'XLW-PC', '智能电子拉力试验机', '智能电子拉力试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('284', 'XYD-15K', '纸箱抗压试验机', '纸箱抗压试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('285', 'XYD-45K', '纸箱抗压试验机', '纸箱抗压试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('286', '150', '纸箱抗压试验机', '纸箱抗压试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('287', '151', '纸箱抗压试验机', '纸箱抗压试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('288', 'NJY-20', '扭矩仪', '扭矩仪', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('289', 'NLW-20', '胶粘剂拉伸剪切试验机', '胶粘剂拉伸剪切试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('290', 'i-STRENTEK 1100', '智能电子拉力试验机', '智能电子拉力试验机', '2', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('291', 'XLW-EC', '智能电子拉力试验机', '智能电子拉力试验机', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('292', 'i-STRENTEK 1200', '智能电子拉力试验机', '智能电子拉力试验机', '2', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('293', 'i-MEDITEK 1300', '医药包装性能测试仪', '医药包装性能测试仪', '2', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('294', 'MED-01', '医药包装性能测试仪', '医药包装性能测试仪', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('295', 'i-STRENTEK 1400', '智能电子拉力试验机', '智能电子拉力试验机', '2', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('296', 'XLW(G6)', '六工位拉力试验仪', '六工位拉力试验仪', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('297', 'MEGA 1500', '六工位拉力试验仪', '六工位拉力试验仪', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('298', 'i-STRENTEK 1510', '万能试验仪', '万能试验仪', '2', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('299', 'PROCESS 1600', '扭矩仪', '扭矩仪', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('300', 'i-BOXTEK 1700', '纸箱抗压试验机', '纸箱抗压试验机', '2', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('301', 'i-BOXTEK 1710', '纸箱抗压试验机', '纸箱抗压试验机', '2', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('302', 'MXD-01', '摩擦系数仪', '摩擦系数仪', '1', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('303', 'MXD-01A', '摩擦系数仪', '摩擦系数仪', '1', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('304', 'MXD-02', '摩擦系数仪', '摩擦系数仪', '1', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('305', 'COF-P01', '斜面摩擦系数仪', '斜面摩擦系数仪', '1', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('306', 'FPT-F1', '摩擦系数/剥离试验仪', '摩擦系数/剥离试验仪', '1', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('307', 'i-COFTEK 3200', '摩擦系数仪', '摩擦系数仪', '2', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('308', 'i-COFTEK 3300', '摩擦系数仪', '摩擦系数仪', '2', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('309', 'i-COFTEK 3500', '摩擦系数/剥离试验仪', '摩擦系数/剥离试验仪', '2', '2');
INSERT INTO `sprki_labthink_devices` VALUES ('310', 'W3-010', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('311', 'i-HYDRO 7300', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('312', 'W3-030', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('313', 'i-HYDRO 7310', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('314', 'W3-031', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('315', 'W3-060', '水蒸气透过率测试系统', '水蒸气透过率测试系统', '1', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('316', 'i-HYDRO 7320', '水蒸气透过率测试系统', '水蒸气透过率测试系统', '2', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('317', 'W3-062', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('318', 'W3-0120', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('319', 'i-HYDRO 7330', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('320', 'MEGA 7340', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '31');
INSERT INTO `sprki_labthink_devices` VALUES ('321', 'Basic 201', '氧气透过率测试仪', '氧气透过率测试仪', '1', '32');
INSERT INTO `sprki_labthink_devices` VALUES ('322', 'Basic 202', '气体渗透测试仪', '气体渗透测试仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('323', 'VAC-VBS', '压差法气体渗透仪', '压差法气体渗透仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('324', 'i-GASTRA 7110', '压差法气体渗透仪', '压差法气体渗透仪', '2', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('325', 'VAC-V1', '压差法气体渗透仪', '压差法气体渗透仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('326', 'i-GASTRA 7100', '压差法气体渗透仪', '压差法气体渗透仪', '2', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('327', 'CLASSIC 212', '压差法气体渗透仪', '压差法气体渗透仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('328', 'VAC-V2', '压差法气体渗透仪', '压差法气体渗透仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('329', 'VAC-V3', '压差法气体渗透仪', '压差法气体渗透仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('330', 'G2-130', '压差法容器气体透过率测试仪', '压差法容器气体透过率测试仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('331', 'BTY-B2P', '透气性测试仪 ', '透气性测试仪 ', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('332', 'CLASSIC 216', '压差法气体渗透仪', '压差法气体渗透仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('333', 'i-GASTRA 7210', '气体渗透测试仪', '气体渗透测试仪', '2', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('334', 'G2-131', '气体渗透测试仪', '气体渗透测试仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('335', 'i-GASTRA 7200', '气体渗透测试仪', '气体渗透测试仪', '2', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('336', 'G2-132', '气体渗透测试仪', '气体渗透测试仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('337', 'i-OXTRA 7700', '氧气透过率测试仪', '氧气透过率测试仪', '2', '32');
INSERT INTO `sprki_labthink_devices` VALUES ('338', 'OX2-230', '氧气透过率测试仪', '氧气透过率测试仪', '1', '32');
INSERT INTO `sprki_labthink_devices` VALUES ('339', 'i-OXTRA 7600', '氧气透过率测试仪', '氧气透过率测试仪', '2', '32');
INSERT INTO `sprki_labthink_devices` VALUES ('340', 'OX2-231', '氧气透过率测试仪', '氧气透过率测试仪', '1', '32');
INSERT INTO `sprki_labthink_devices` VALUES ('341', 'Basic 301', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '34');
INSERT INTO `sprki_labthink_devices` VALUES ('342', 'Basic 302', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '1');
INSERT INTO `sprki_labthink_devices` VALUES ('343', 'i-HYDRO 7500', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '34');
INSERT INTO `sprki_labthink_devices` VALUES ('344', 'W3-330', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '34');
INSERT INTO `sprki_labthink_devices` VALUES ('345', 'TSY-W3', '电解法水蒸气透过率测试仪', '电解法水蒸气透过率测试仪', '1', '34');
INSERT INTO `sprki_labthink_devices` VALUES ('346', 'i-HYDRO 7900', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '35');
INSERT INTO `sprki_labthink_devices` VALUES ('347', 'W3-230', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '35');
INSERT INTO `sprki_labthink_devices` VALUES ('348', 'i-HYDRO 7400', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '2', '36');
INSERT INTO `sprki_labthink_devices` VALUES ('349', 'W3-130', '水蒸气透过率测试仪', '水蒸气透过率测试仪', '1', '36');
INSERT INTO `sprki_labthink_devices` VALUES ('350', 'OR2-410', '有机气体透过率测试系统', '有机气体透过率测试系统', '1', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('351', 'i-SEPAMATE 7800', '膜分离测试分析仪', '膜分离测试分析仪', '2', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('352', 'G2-110', '膜分离测试分析仪', '膜分离测试分析仪', '1', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('353', 'i-GASMATE 7810', '有机气体渗透测试分析仪', '有机气体渗透测试分析仪', '2', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('354', 'i-GASMATE 7820', '混合气体渗透测试分析仪', '混合气体渗透测试分析仪', '2', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('355', 'MGT-01', '混合气体渗透测试分析仪', '混合气体渗透测试分析仪', '1', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('356', 'i-GASMATE 7830', '膜分离渗透测试分析仪', '膜分离渗透测试分析仪', '2', '39');
INSERT INTO `sprki_labthink_devices` VALUES ('357', 'HST-H6', '热封试验仪', '热封试验仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('358', 'HST-H3', '热封试验仪', '热封试验仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('359', 'GHS-03', '双五点梯度热封仪', '双五点梯度热封仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('360', 'CLASSIC 513', '梯度热封仪', '梯度热封仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('361', 'PSST-100', '便携式热封强度仪', '便携式热封强度仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('362', 'HTT-L1', '热粘拉力试验仪', '热粘拉力试验仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('363', 'RSY-R2', '热缩试验仪', '热缩试验仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('364', 'i-THERMOTEK 2400', '热封与热粘性能试验仪', '热封与热粘性能试验仪', '2', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('365', 'i-THERMOTEK2500', '热粘性能试验仪', '热粘性能试验仪', '2', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('366', 'i-THERMOTEK 2710', '薄膜热缩性能测试仪', '薄膜热缩性能测试仪', '2', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('367', 'FST-02', '薄膜热缩性能测试仪', '薄膜热缩性能测试仪', '1', '4');
INSERT INTO `sprki_labthink_devices` VALUES ('368', 'CHY-C2A', '测厚仪', '测厚仪', '1', '5');
INSERT INTO `sprki_labthink_devices` VALUES ('369', 'CHY-CA', '测厚仪', '测厚仪', '1', '5');
INSERT INTO `sprki_labthink_devices` VALUES ('370', 'i-THICKNESS 4100', '测厚仪', '测厚仪', '2', '5');
INSERT INTO `sprki_labthink_devices` VALUES ('371', 'CHY-CB', '测厚仪', '测厚仪', '1', '5');
INSERT INTO `sprki_labthink_devices` VALUES ('372', 'i-THICKNESS 4400', '测厚仪', '测厚仪', '2', '5');
INSERT INTO `sprki_labthink_devices` VALUES ('373', 'i-THICKNESS 4410', '测厚仪', '测厚仪', '2', '5');
INSERT INTO `sprki_labthink_devices` VALUES ('374', 'i-THICKNESS 4120', '球面测厚仪', '球面测厚仪', '2', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('375', 'i-LACERA 5500', '撕裂度仪', '撕裂度仪', '2', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('376', 'i-LACERA 5510', '撕裂度仪', '撕裂度仪', '2', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('377', 'SLY-S1', '撕裂度仪', '撕裂度仪', '1', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('378', 'i-FREEFALL 5100', '落镖冲击试验仪', '落镖冲击试验仪', '2', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('379', 'i-FREEFALL 5110', '落镖冲击试验仪', '落镖冲击试验仪', '2', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('380', 'BMC-B1', '落镖冲击试验仪', '落镖冲击试验仪', '1', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('381', 'FDI-01', '落镖冲击试验仪', '落镖冲击试验仪', '1', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('382', 'i-PENDUM 5200', '薄膜冲击试验仪', '薄膜冲击试验仪', '2', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('383', 'i-PENDUM 5210', '薄膜冲击试验仪', '薄膜冲击试验仪', '2', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('384', 'FIT-01', '薄膜冲击试验仪', '薄膜冲击试验仪', '1', '6');
INSERT INTO `sprki_labthink_devices` VALUES ('385', 'PROCESS 6200', '泄漏与密封强度测试仪', '泄漏与密封强度测试仪', '2', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('386', 'i-LEAKTEK 6600', '泄漏与密封强度测试仪', '泄漏与密封强度测试仪', '2', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('387', 'LSSD-01', '泄漏与密封强度测试仪', '泄漏与密封强度测试仪', '1', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('388', 'PROCESS 6610', '密封试验仪', '密封试验仪', '2', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('389', 'MFY-01', '密封试验仪', '密封试验仪', '1', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('391', 'i-VACUPACK 6100', '真空包装残氧仪', '真空包装残氧仪', '2', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('392', 'RGT-01', '真空包装残氧仪', '真空包装残氧仪', '1', '7');
INSERT INTO `sprki_labthink_devices` VALUES ('393', 'HGA-02', '顶空气体分析仪', '顶空气体分析仪', '1', '8');
INSERT INTO `sprki_labthink_devices` VALUES ('394', 'HGA-03', '顶空气体分析仪', '顶空气体分析仪', '1', '8');
INSERT INTO `sprki_labthink_devices` VALUES ('395', 'i-GASATEK 6900', '顶空气体分析仪', '顶空气体分析仪', '2', '8');
INSERT INTO `sprki_labthink_devices` VALUES ('396', 'PROCESS 6910', '顶空气体分析仪', '顶空气体分析仪', '2', '8');
INSERT INTO `sprki_labthink_devices` VALUES ('397', '9100', '揉搓试验仪', '揉搓试验仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('398', 'FDT-02', '揉搓试验仪', '揉搓试验仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('399', 'CZY-6S', '持粘性测试仪', '持粘性测试仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('400', 'CLASSIC 920', '持粘性测试仪', '持粘性测试仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('401', 'CZY-2S', '持粘性测试仪', '持粘性测试仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('402', 'CZY-G', '初粘性测试仪', '初粘性测试仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('403', 'CLASSIC 923', '初粘性测试仪(药典)', '初粘性测试仪(药典)', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('404', 'CLASSIC 924', '斜槽滚球法初粘性测试仪', '斜槽滚球法初粘性测试仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('405', 'MCJ-01A', '磨擦试验机', '磨擦试验机', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('406', 'RT-01', '磨擦试验仪', '磨擦试验仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('407', 'BLJ-02', '圆盘剥离试验机', '圆盘剥离试验机', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('408', 'YGJ-02', '胶粘带压滚机', '胶粘带压滚机', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('409', 'SBG-80', 'D65/A 标准光源', 'D65/A 标准光源', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('410', 'SPS-80P', '双光源配色看版台', '双光源配色看版台', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('411', 'FT-F1', '雾化测试仪', '雾化测试仪', '1', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('412', 'TQD-G1', '透气度测试仪', '透气度测试仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('413', '961', '透气度测试仪', '透气度测试仪', '1', '33');
INSERT INTO `sprki_labthink_devices` VALUES ('414', 'i-RESITEST 8100', '蒸发残渣恒重仪', '蒸发残渣恒重仪', '2', '10');
INSERT INTO `sprki_labthink_devices` VALUES ('415', 'ERT-01', '蒸发残渣恒重仪', '蒸发残渣恒重仪', '1', '10');
INSERT INTO `sprki_labthink_devices` VALUES ('416', 'MEGA 8200', '蒸发残渣恒重仪', '蒸发残渣恒重仪', '2', '10');
INSERT INTO `sprki_labthink_devices` VALUES ('417', 'i-RESITEST 8300', '蒸发残渣恒重仪', '蒸发残渣恒重仪', '2', '10');
INSERT INTO `sprki_labthink_devices` VALUES ('418', 'PROCESS 9200', '容积与重量分析测试仪', '容积与重量分析测试仪', '2', '9');
INSERT INTO `sprki_labthink_devices` VALUES ('420', 'CLASSIC 632', '密封试验仪', '密封试验仪', '1', '7');

-- ----------------------------
-- Table structure for sprki_labthink_device_kind
-- ----------------------------
DROP TABLE IF EXISTS `sprki_labthink_device_kind`;
CREATE TABLE `sprki_labthink_device_kind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_kind_name` varchar(100) NOT NULL,
  `device_kind_namecn` varchar(100) NOT NULL,
  `device_kind_nameen` varchar(100) NOT NULL,
  `enable` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_labthink_device_kind
-- ----------------------------
INSERT INTO `sprki_labthink_device_kind` VALUES ('1', 'national', '国标设备', '', '');
INSERT INTO `sprki_labthink_device_kind` VALUES ('2', 'international', '美标设备', '', '');

-- ----------------------------
-- Table structure for sprki_labthink_device_type
-- ----------------------------
DROP TABLE IF EXISTS `sprki_labthink_device_type`;
CREATE TABLE `sprki_labthink_device_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_type_name` varchar(100) NOT NULL,
  `device_type_namecn` varchar(100) NOT NULL,
  `device_type_nameen` varchar(100) NOT NULL DEFAULT '',
  `enable` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_labthink_device_type
-- ----------------------------
INSERT INTO `sprki_labthink_device_type` VALUES ('1', '力与强度', '力与强度', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('2', '摩擦系数', '摩擦系数', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('4', '热性能', '热性能', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('5', '测厚', '测厚', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('6', '撕裂/冲击', '撕裂/冲击', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('7', '泄漏/密封', '泄漏/密封', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('8', '顶空分析', '顶空分析', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('9', '其他', '其他', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('10', '蒸发残渣', '蒸发残渣', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('11', '容积与重量分析', '容积与重量分析', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('30', '透过', '透过性能', '', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('31', '称重', ' 阻隔性-水透过-重量', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('32', '库伦', ' 阻隔性-氧透过-库伦', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('33', '压差', ' 阻隔性-氧透过-压差', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('34', '电解', ' 阻隔性-水透过-电解', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('35', '红外', ' 阻隔性-水透过-红外', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('36', '湿度', ' 阻隔性-水透过-湿度', ' ', '');
INSERT INTO `sprki_labthink_device_type` VALUES ('39', '色谱', '阻隔性-色谱', ' ', '');

-- ----------------------------
-- Table structure for sprki_labthink_security_log
-- ----------------------------
DROP TABLE IF EXISTS `sprki_labthink_security_log`;
CREATE TABLE `sprki_labthink_security_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` bit(1) NOT NULL,
  `info` varchar(1000) NOT NULL,
  `logip` varchar(40) NOT NULL,
  `loguser` varchar(40) NOT NULL,
  `operation` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_labthink_security_log
-- ----------------------------

-- ----------------------------
-- Table structure for sprki_labthink_user_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `sprki_labthink_user_detail_info`;
CREATE TABLE `sprki_labthink_user_detail_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disabled` bit(1) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `department` bigint(20) DEFAULT NULL,
  `users` varchar(30) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pt7b39vrxj3oq1y1j2jppe5xj` (`users`),
  KEY `FK_pvmg3rkm3bkocbdl6itveev98` (`department`),
  CONSTRAINT `FK_pt7b39vrxj3oq1y1j2jppe5xj` FOREIGN KEY (`users`) REFERENCES `sprki_user` (`username`),
  CONSTRAINT `FK_pvmg3rkm3bkocbdl6itveev98` FOREIGN KEY (`department`) REFERENCES `sprki_labthink_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_labthink_user_detail_info
-- ----------------------------
INSERT INTO `sprki_labthink_user_detail_info` VALUES ('2', '\0', 'admin', '1', 'admin', '1', 'admin', 'admin@labthink.com');

-- ----------------------------
-- Table structure for sprki_user
-- ----------------------------
DROP TABLE IF EXISTS `sprki_user`;
CREATE TABLE `sprki_user` (
  `username` varchar(30) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(50) NOT NULL,
  `regdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `username_alias` varchar(30) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sprki_user
-- ----------------------------
INSERT INTO `sprki_user` VALUES ('aaa', '', 'aaa', '2016-02-25 08:56:04', '1');
INSERT INTO `sprki_user` VALUES ('admin', '', 'admin', '2016-02-17 00:00:00', 'admin');
INSERT INTO `sprki_user` VALUES ('user', '', 'user', '2016-02-17 00:00:00', 'user  别名');
