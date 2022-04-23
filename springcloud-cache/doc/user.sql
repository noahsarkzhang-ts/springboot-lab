DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`
(
  `id`       int(12) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name`     varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `sex`      varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;