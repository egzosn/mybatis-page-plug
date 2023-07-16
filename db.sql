
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