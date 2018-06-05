/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 10.2.6-MariaDB-10.2.6+maria~trusty-log : Database - huo
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `Ta` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cname` varchar(10) DEFAULT NULL COMMENT '名称',
  `mea` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

insert  into `users`(`cname`,`mea`) values ('egan','https://www.oschina.net/p/pay-java-parent');
insert  into `users`(`cname`,`mea`) values ('egan','https://github.com/egzosn/mybatis-page-plug');
insert  into `users`(`cname`,`mea`) values ('egan','https://gitee.com/egzosn');
insert  into `users`(`cname`,`mea`) values ('egan','https://gitee.com/egzosn/pay-java-parent');
insert  into `users`(`cname`,`mea`) values ('egan','https://gitee.com/egzosn/spring-jdbc-orm');
insert  into `users`(`cname`,`mea`) values ('egan','https://github.com/egzosn/spring-jdbc-orm');
insert  into `users`(`cname`,`mea`) values ('egan','https://gitee.com/egzosn/eg-validation');
insert  into `users`(`cname`,`mea`) values ('egan','https://github.com/egzosn/eg-validation');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
