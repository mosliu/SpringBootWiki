/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spring`;

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spring`;

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sprki_user` */

insert  into `sprki_user`(`username`,`enabled`,`password`,`regdate`,`username_alias`) values ('11122','\0','222','2016-03-29 13:25:15','11122');
insert  into `sprki_user`(`username`,`enabled`,`password`,`regdate`,`username_alias`) values ('aaa','','aaa','2016-02-25 08:56:04','1');
insert  into `sprki_user`(`username`,`enabled`,`password`,`regdate`,`username_alias`) values ('admin','','admin','2016-02-17 00:00:00','admin');
insert  into `sprki_user`(`username`,`enabled`,`password`,`regdate`,`username_alias`) values ('bbb','','bbb','2016-02-17 00:00:00','b');
insert  into `sprki_user`(`username`,`enabled`,`password`,`regdate`,`username_alias`) values ('user','','user','2016-02-17 00:00:00','user');


/*Data for the table `sprki_authorities` */

insert  into `sprki_authorities`(`id`,`authority`,`username`) values (1,'ROLE_USER','admin');
insert  into `sprki_authorities`(`id`,`authority`,`username`) values (2,'ROLE_USER','user');
insert  into `sprki_authorities`(`id`,`authority`,`username`) values (3,'ROLE_AA','aaa');
insert  into `sprki_authorities`(`id`,`authority`,`username`) values (4,'ROLE_ADMIN','admin');
insert  into `sprki_authorities`(`id`,`authority`,`username`) values (5,'ROLE_ADMIN','admin');
insert  into `sprki_authorities`(`id`,`authority`,`username`) values (6,'ROLE_USER','11122');


insert  into `sprki_cms_category`(`id`,`disabled`,`english_name`,`name`,`page_size`,`url`,`parent_id`) values (1,'\0','category1','分类1',10,'/category',NULL);
insert  into `sprki_cms_category`(`id`,`disabled`,`english_name`,`name`,`page_size`,`url`,`parent_id`) values (2,'\0','category2','分类2',10,'/category2',NULL);


/*Data for the table `sprki_labthink_department` */

insert  into `sprki_labthink_department`(`id`,`department_name`,`department_namecn`) values (1,'售后','售后');
insert  into `sprki_labthink_department`(`id`,`department_name`,`department_namecn`) values (2,'实验室','实验室');

/*Data for the table `sprki_labthink_device_kind` */

insert  into `sprki_labthink_device_kind`(`id`,`device_kind_name`,`enable`,`device_kind_namecn`,`device_kind_nameen`) values (1,'national','','国标设备','');
insert  into `sprki_labthink_device_kind`(`id`,`device_kind_name`,`enable`,`device_kind_namecn`,`device_kind_nameen`) values (2,'international','','美标设备','');

/*Data for the table `sprki_labthink_device_type` */

INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (1, '力与强度', '力与强度', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (2, '摩擦系数', '摩擦系数', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (4, '热封', '热性能', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (5, '测厚', '测厚', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (6, '撕裂/冲击', '撕裂/冲击', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (7, '泄漏/密封', '泄漏/密封', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (8, '顶空分析', '顶空分析', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (9, '其他', '其他', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (10, '蒸发残渣', '蒸发残渣', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (11, '容积与重量分析', '容积与重量分析', '', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (31, 'weight', ' 阻隔性-水透过-重量', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (32, '库伦', ' 阻隔性-氧透过-库伦', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (33, 'Pressure', ' 阻隔性-氧透过-压差', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (34, 'dianjie', ' 阻隔性-水透过-电解', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (35, '红外', ' 阻隔性-水透过-红外', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (36, '湿度', ' 阻隔性-水透过-湿度', ' ', '');
INSERT INTO `sprki_labthink_device_type` (`id`, `device_type_name`, `device_type_namecn`, `device_type_nameen`, `enable`) VALUES (39, '色谱', '阻隔性-色谱', ' ', '');

/*Data for the table `sprki_labthink_devices` */

INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (1, 'W3/330', '', '', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (2, '氧透过', '', '', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (279, 'XLW-B', '智能电子拉力试验机', '智能电子拉力试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (280, 'BLD-200N', '电子剥离试验机', '电子剥离试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (281, 'XLW', '智能电子拉力试验机', '智能电子拉力试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (282, 'XLW-M', '智能电子拉力试验机', '智能电子拉力试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (283, 'XLW-PC', '智能电子拉力试验机', '智能电子拉力试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (284, 'XYD-15K', '纸箱抗压试验机', '纸箱抗压试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (285, 'XYD-45K', '纸箱抗压试验机', '纸箱抗压试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (286, '150', '纸箱抗压试验机', '纸箱抗压试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (287, '151', '纸箱抗压试验机', '纸箱抗压试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (288, 'NJY-20', '扭矩仪', '扭矩仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (289, 'NLW-20', '胶粘剂拉伸剪切试验机', '胶粘剂拉伸剪切试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (290, 'i-STRENTEK 1100', '智能电子拉力试验机', '智能电子拉力试验机', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (291, 'XLW-EC', '智能电子拉力试验机', '智能电子拉力试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (292, 'i-STRENTEK 1200', '智能电子拉力试验机', '智能电子拉力试验机', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (293, 'i-MEDITEK 1300', '医药包装性能测试仪', '医药包装性能测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (294, 'MED-01', '医药包装性能测试仪', '医药包装性能测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (295, 'i-STRENTEK 1400', '智能电子拉力试验机', '智能电子拉力试验机', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (296, 'XLW(G6)', '六工位拉力试验仪', '六工位拉力试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (297, 'MEGA 1500', '六工位拉力试验仪', '六工位拉力试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (298, 'i-STRENTEK 1510', '万能试验仪', '万能试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (299, 'PROCESS 1600', '扭矩仪', '扭矩仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (300, 'i-BOXTEK 1700', '纸箱抗压试验机', '纸箱抗压试验机', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (301, 'i-BOXTEK 1710', '纸箱抗压试验机', '纸箱抗压试验机', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (302, 'MXD-01', '摩擦系数仪', '摩擦系数仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (303, 'MXD-01A', '摩擦系数仪', '摩擦系数仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (304, 'MXD-02', '摩擦系数仪', '摩擦系数仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (305, 'COF-P01', '斜面摩擦系数仪', '斜面摩擦系数仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (306, 'FPT-F1', '摩擦系数/剥离试验仪', '摩擦系数/剥离试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (307, 'i-COFTEK 3200', '摩擦系数仪', '摩擦系数仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (308, 'i-COFTEK 3300', '摩擦系数仪', '摩擦系数仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (309, 'i-COFTEK 3500', '摩擦系数/剥离试验仪', '摩擦系数/剥离试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (310, 'W3-010', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (311, 'i-HYDRO 7300', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (312, 'W3-030', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (313, 'i-HYDRO 7310', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (314, 'W3-031', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (315, 'W3-060', '水蒸气透过率测试系统', '水蒸气透过率测试系统', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (316, 'i-HYDRO 7320', '水蒸气透过率测试系统', '水蒸气透过率测试系统', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (317, 'W3-062', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (318, 'W3-0120', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (319, 'i-HYDRO 7330', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (320, 'MEGA 7340', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (321, 'Basic 201', '氧气透过率测试仪', '氧气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (322, 'Basic 202', '气体渗透测试仪', '气体渗透测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (323, 'VAC-VBS', '压差法气体渗透仪', '压差法气体渗透仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (324, 'i-GASTRA 7110', '压差法气体渗透仪', '压差法气体渗透仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (325, 'VAC-V1', '压差法气体渗透仪', '压差法气体渗透仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (326, 'i-GASTRA 7100', '压差法气体渗透仪', '压差法气体渗透仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (327, 'CLASSIC 212', '压差法气体渗透仪', '压差法气体渗透仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (328, 'VAC-V2', '压差法气体渗透仪', '压差法气体渗透仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (329, 'VAC-V3', '压差法气体渗透仪', '压差法气体渗透仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (330, 'G2-130', '压差法容器气体透过率测试仪', '压差法容器气体透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (331, 'BTY-B2P', '透气性测试仪 ', '透气性测试仪 ', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (332, 'CLASSIC 216', '压差法气体渗透仪', '压差法气体渗透仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (333, 'i-GASTRA 7210', '气体渗透测试仪', '气体渗透测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (334, 'G2-131', '气体渗透测试仪', '气体渗透测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (335, 'i-GASTRA 7200', '气体渗透测试仪', '气体渗透测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (336, 'G2-132', '气体渗透测试仪', '气体渗透测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (337, 'i-OXTRA 7700', '氧气透过率测试仪', '氧气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (338, 'OX2-230', '氧气透过率测试仪', '氧气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (339, 'i-OXTRA 7600', '氧气透过率测试仪', '氧气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (340, 'OX2-231', '氧气透过率测试仪', '氧气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (341, 'Basic 301', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (342, 'Basic 302', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (343, 'i-HYDRO 7500', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (344, 'W3-330', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (345, 'TSY-W3', '电解法水蒸气透过率测试仪', '电解法水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (346, 'i-HYDRO 7900', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (347, 'W3-230', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (348, 'i-HYDRO 7400', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (349, 'W3-130', '水蒸气透过率测试仪', '水蒸气透过率测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (350, 'OR2-410', '有机气体透过率测试系统', '有机气体透过率测试系统', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (351, 'i-SEPAMATE 7800', '膜分离测试分析仪', '膜分离测试分析仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (352, 'G2-110', '膜分离测试分析仪', '膜分离测试分析仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (353, 'i-GASMATE 7810', '有机气体渗透测试分析仪', '有机气体渗透测试分析仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (354, 'i-GASMATE 7820', '混合气体渗透测试分析仪', '混合气体渗透测试分析仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (355, 'MGT-01', '混合气体渗透测试分析仪', '混合气体渗透测试分析仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (356, 'i-GASMATE 7830', '膜分离渗透测试分析仪', '膜分离渗透测试分析仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (357, 'HST-H6', '热封试验仪', '热封试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (358, 'HST-H3', '热封试验仪', '热封试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (359, 'GHS-03', '双五点梯度热封仪', '双五点梯度热封仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (360, 'CLASSIC 513', '梯度热封仪', '梯度热封仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (361, 'PSST-100', '便携式热封强度仪', '便携式热封强度仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (362, 'HTT-L1', '热粘拉力试验仪', '热粘拉力试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (363, 'RSY-R2', '热缩试验仪', '热缩试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (364, 'i-THERMOTEK 2400', '热封与热粘性能试验仪', '热封与热粘性能试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (365, 'i-THERMOTEK2500', '热粘性能试验仪', '热粘性能试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (366, 'i-THERMOTEK 2710', '薄膜热缩性能测试仪', '薄膜热缩性能测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (367, 'FST-02', '薄膜热缩性能测试仪', '薄膜热缩性能测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (368, 'CHY-C2A', '测厚仪', '测厚仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (369, 'CHY-CA', '测厚仪', '测厚仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (370, 'i-THICKNESS 4100', '测厚仪', '测厚仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (371, 'CHY-CB', '测厚仪', '测厚仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (372, 'i-THICKNESS 4400', '测厚仪', '测厚仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (373, 'i-THICKNESS 4410', '测厚仪', '测厚仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (374, 'i-THICKNESS 4120', '球面测厚仪', '球面测厚仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (375, 'i-LACERA 5500', '撕裂度仪', '撕裂度仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (376, 'i-LACERA 5510', '撕裂度仪', '撕裂度仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (377, 'SLY-S1', '撕裂度仪', '撕裂度仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (378, 'i-FREEFALL 5100', '落镖冲击试验仪', '落镖冲击试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (379, 'i-FREEFALL 5110', '落镖冲击试验仪', '落镖冲击试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (380, 'BMC-B1', '落镖冲击试验仪', '落镖冲击试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (381, 'FDI-01', '落镖冲击试验仪', '落镖冲击试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (382, 'i-PENDUM 5200', '薄膜冲击试验仪', '薄膜冲击试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (383, 'i-PENDUM 5210', '薄膜冲击试验仪', '薄膜冲击试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (384, 'FIT-01', '薄膜冲击试验仪', '薄膜冲击试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (385, 'PROCESS 6200', '泄漏与密封强度测试仪', '泄漏与密封强度测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (386, 'i-LEAKTEK 6600', '泄漏与密封强度测试仪', '泄漏与密封强度测试仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (387, 'LSSD-01', '泄漏与密封强度测试仪', '泄漏与密封强度测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (388, 'PROCESS 6610', '密封试验仪', '密封试验仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (389, 'MFY-01', '密封试验仪', '密封试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (390, '632', '密封试验仪', '密封试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (391, 'i-VACUPACK 6100', '真空包装残氧仪', '真空包装残氧仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (392, 'RGT-01', '真空包装残氧仪', '真空包装残氧仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (393, 'HGA-02', '顶空气体分析仪', '顶空气体分析仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (394, 'HGA-03', '顶空气体分析仪', '顶空气体分析仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (395, 'i-GASATEK 6900', '顶空气体分析仪', '顶空气体分析仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (396, 'PROCESS 6910', '顶空气体分析仪', '顶空气体分析仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (397, '9100', '揉搓试验仪', '揉搓试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (398, 'FDT-02', '揉搓试验仪', '揉搓试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (399, 'CZY-6S', '持粘性测试仪', '持粘性测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (400, 'CLASSIC 920', '持粘性测试仪', '持粘性测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (401, 'CZY-2S', '持粘性测试仪', '持粘性测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (402, 'CZY-G', '初粘性测试仪', '初粘性测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (403, 'CLASSIC 923', '初粘性测试仪(药典)', '初粘性测试仪(药典)', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (404, 'CLASSIC 924', '斜槽滚球法初粘性测试仪', '斜槽滚球法初粘性测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (405, 'MCJ-01A', '磨擦试验机', '磨擦试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (406, 'RT-01', '磨擦试验仪', '磨擦试验仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (407, 'BLJ-02', '圆盘剥离试验机', '圆盘剥离试验机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (408, 'YGJ-02', '胶粘带压滚机', '胶粘带压滚机', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (409, 'SBG-80', 'D65/A 标准光源', 'D65/A 标准光源', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (410, 'SPS-80P', '双光源配色看版台', '双光源配色看版台', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (411, 'FT-F1', '雾化测试仪', '雾化测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (412, 'TQD-G1', '透气度测试仪', '透气度测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (413, '961', '透气度测试仪', '透气度测试仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (414, 'i-RESITEST 8100', '蒸发残渣恒重仪', '蒸发残渣恒重仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (415, 'ERT-01', '蒸发残渣恒重仪', '蒸发残渣恒重仪', 1, 1);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (416, 'MEGA 8200', '蒸发残渣恒重仪', '蒸发残渣恒重仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (417, 'i-RESITEST 8300', '蒸发残渣恒重仪', '蒸发残渣恒重仪', 2, 2);
INSERT INTO `sprki_labthink_devices` (`id`, `devicename`, `devicenamecn`, `devicenameen`, `device_kind`, `device_type`) VALUES (418, 'PROCESS 9200', '容积与重量分析测试仪', '容积与重量分析测试仪', 2, 2);



/*Data for the table `sprki_cms_content` */

insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (1,0,0,NULL,0,'\0',NULL,'','\0','2016-03-23 16:39:31',NULL,'2016-03-23 16:39:31',0,'','',0,NULL,'支持系统第一个问题！','admin',1,'admin');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (2,0,0,NULL,0,'\0',NULL,'','\0','2016-03-21 16:30:49',NULL,'2016-03-21 16:30:49',0,'','',0,NULL,'123123','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (3,1,0,NULL,0,'\0',NULL,'','\0','2016-03-21 16:31:05',NULL,'2016-03-21 16:31:05',0,'','',0,NULL,'123123','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (4,0,0,NULL,0,'\0',NULL,'','\0','2016-03-25 15:05:51',NULL,'2016-03-21 16:32:00',0,'','',0,NULL,'123123fff','user',1,'admin');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (5,0,0,NULL,0,'\0',NULL,'','\0','2016-03-21 16:53:19',NULL,'2016-03-21 16:53:18',0,'','',0,NULL,'14341','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (6,0,0,NULL,0,'\0',NULL,'','\0','2016-03-22 15:20:30',NULL,'2016-03-22 15:20:30',0,'','',0,NULL,'44444444','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (7,0,0,NULL,0,'\0',NULL,'','\0','2016-03-22 15:27:39',NULL,'2016-03-22 15:27:39',0,'','',0,NULL,'3333333','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (8,0,0,NULL,0,'\0',NULL,'','\0','2016-03-24 09:34:47',NULL,'2016-03-22 15:29:18',0,'','',0,NULL,'1100老美标怎么改那个试验ID','user',1,'admin');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (9,0,0,NULL,0,'\0',NULL,'','\0','2016-03-22 15:30:16',NULL,'2016-03-22 15:30:16',0,'','',0,NULL,'eeeeeee','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (10,0,0,NULL,0,'\0',NULL,'','\0','2016-03-25 13:35:48',NULL,'2016-03-22 15:33:56',0,'','',0,NULL,'eeeeeee','user',2,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (11,0,0,NULL,0,'',NULL,'','\0','2016-03-22 15:34:15',NULL,'2016-03-22 15:34:15',0,'','',0,NULL,'33','user',1,'user');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (12,0,0,NULL,0,'',NULL,'','\0','2016-03-24 08:28:54',NULL,'2016-03-24 08:28:53',0,'','',0,NULL,'vdf','admin',1,'admin');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (13,0,0,NULL,0,'\0',NULL,'','\0','2016-03-24 09:25:37',NULL,'2016-03-23 07:50:40',0,'','',0,NULL,'麻烦帮确认下2013年7月份的XLW(M)与现在使用的主板是一样的吗？','admin',1,'admin');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (14,0,0,NULL,0,'\0',NULL,'','\0','2016-03-24 08:09:09',NULL,'2016-03-24 08:09:08',0,'','',0,NULL,'W3/031腔内的接近开关  型号？','admin',1,'admin');
insert  into `sprki_cms_content`(`id`,`clicks`,`comments_count`,`description`,`diggs`,`disabled`,`full_title`,`info_text`,`is_copied`,`last_update_date`,`meta_keywords`,`publish_date`,`score`,`source`,`source_url`,`state`,`subtitle`,`title`,`author`,`category`,`last_update_user`) values (15,2,0,NULL,0,'\0',NULL,'','\0','2016-03-24 08:28:18',NULL,'2016-03-24 08:28:18',0,'','',0,NULL,'have a test','admin',1,'admin');

/*Data for the table `sprki_cms_contentfaq` */

insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>理论上没问题</p><p>我找工程师确认下</p><p>找王连佳确认：没问题</p><p><img src=\"/ueditor/jsp/upload/image/20160323/1458722333434075676.jpg\" title=\"1458722333434075676.jpg\" alt=\"http_imgloadCA2IU2WB.jpg\"/></p>','<div>* 单 位：广东电网公司电力科学研究院（培训）郭思嘉 工程师上门再把低温箱培训下，另外两台客户会有人上门调试&nbsp;</div><div>地 址：广州市番禺区南村镇兴业大道南侧南村工业区A区骏拓工业园一号楼&nbsp;</div><div>联系人：赵耀洪18826469062&nbsp;</div><div>产 品： MGT-01订制编号：DZ201508040003 不含气相色谱仪&nbsp;</div><div>送货前请先联系郭思嘉13631382021、GT-7008-TR低温回缩试验机 厂家直发、GT-7017-URP换气式老化试验机厂家直发、DW-50W255低温箱厂家直发&nbsp;</div><div>发 货： 德邦304420118 10/9一个木箱二个纸箱 送货前请先联系郭思嘉13631382021，售后请提前联系客户准备事宜，客户测试气体为六氟化硫，并请让客户预约安捷伦工程师仪器上门安装调试，费用由客户承担。&nbsp;</div><div><br></div><div>这台设备可以用氩气做载气吗</div>',1,1,1,'2016-03-11 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('','123',2,1,1,'2016-03-11 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('','123',3,1,1,'2016-03-11 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('','123',4,1,1,'2016-03-11 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('','513451435',5,1,1,'2016-03-24 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>fffffffffff</p>','44444444',6,2,1,'2016-03-25 14:56:16');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('','4444',7,1,1,'2016-03-03 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>在userdata 下的 Sysini.xml 里 S_TestID</p>','你知道1100老美标怎么改那个试验ID吗？售后借我们设备 回来以后软件给换了 现在iD从21开始了 &nbsp;但是原来都到9582了 &nbsp;如果不改回去 会导致云检测这边数据重复&nbsp;',8,1,1,'2016-03-03 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>555</p>','444',9,1,1,'2016-03-24 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>555</p>','444',10,1,1,'2016-03-24 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>55</p>','44',11,1,1,'2016-03-24 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>gh</p>','dsfg',12,1,1,'2016-03-18 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>确定不是刘红旗的板子</p><p>现在用的都是带拉线位移传感器的，和以前不带拉线位移传感器的是不通用的</p><p>刘红旗的除了一台英国的，其他都是新的拉绳的</p><p>是不是刘红旗的可以查看关于以知道</p><p><br/></p>','麻烦帮确认下2013年7月份的XLW(M)与现在使用的主板是一样的吗？',13,1,1,'2016-03-10 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>LJ8A3-1-Z／BX(平头,35mm) &nbsp;刘思广处获取</p><p>24V &nbsp;杨继伟处获取</p><p><br/></p>','<div>W3/031腔内的接近开关 &nbsp;型号是这个吗？</div><div>供电是12V的吗？</div>',14,1,2,'2016-03-02 00:00:00');
insert  into `sprki_cms_contentfaq`(`answer`,`question`,`faq_id`,`department`,`devices`,`question_date`) values ('<p>have a test</p>','have a test',15,1,1,'2016-03-05 00:00:00');
