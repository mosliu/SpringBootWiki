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

insert  into `sprki_labthink_device_type`(`id`,`device_type_name`,`enable`,`device_type_namecn`,`device_type_nameen`) values (1,'permebality','','阻隔性','');
insert  into `sprki_labthink_device_type`(`id`,`device_type_name`,`enable`,`device_type_namecn`,`device_type_nameen`) values (2,'strength','','力值设备','');

/*Data for the table `sprki_labthink_devices` */

insert  into `sprki_labthink_devices`(`id`,`devicename`,`device_kind`,`device_type`,`devicenamecn`,`devicenameen`) values (1,'W3/330',1,1,'','');
insert  into `sprki_labthink_devices`(`id`,`devicename`,`device_kind`,`device_type`,`devicenamecn`,`devicenameen`) values (2,'氧透过',2,2,'','');



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
