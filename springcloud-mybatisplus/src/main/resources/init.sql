DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) DEFAULT NULL COMMENT '唯一标示',
  `code` varchar(20) DEFAULT NULL COMMENT '编码',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `status` char(1) DEFAULT '1' COMMENT '状态 1启用 0 停用',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;