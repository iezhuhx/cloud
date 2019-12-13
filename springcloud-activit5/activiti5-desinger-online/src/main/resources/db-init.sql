CREATE TABLE `t_xtsz_yh` (
  `WW_XH` varchar(36) NOT NULL COMMENT '序号',
  `WW_YHM` varchar(50) DEFAULT NULL COMMENT '用户名',
  `WW_MM` varchar(100) DEFAULT NULL COMMENT '密码',
  `WW_XM` varchar(50) DEFAULT NULL COMMENT '姓名',
  `WW_GW` varchar(50) DEFAULT NULL COMMENT '岗位',
  `WW_JB` varchar(50) DEFAULT NULL COMMENT '级别',
  `WW_ZC` varchar(50) DEFAULT NULL COMMENT '职称',
  `WW_GZZC` varchar(200) DEFAULT NULL COMMENT '工作职责',
  `WW_DQ_BM` varchar(20) DEFAULT NULL COMMENT '地区编码',
  `WW_DW_BM` varchar(36) DEFAULT NULL COMMENT '单位编码',
  `WW_BZ` varchar(200) DEFAULT NULL COMMENT '备注',
  `WW_DM_ID` varchar(100) DEFAULT NULL COMMENT '达梦Id',
  `WW_SFQY` bit(1) DEFAULT NULL COMMENT '是否启用',
  `WW_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `WW_XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`WW_XH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

CREATE TABLE `t_xtsz_js` (
  `WW_XH` varchar(36) NOT NULL COMMENT '序号',
  `WW_JSMC` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `WW_BZ` varchar(200) DEFAULT NULL COMMENT '备注',
  `WW_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `WW_XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`WW_XH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';



CREATE TABLE `t_xtsz_yhjs` (
  `WW_XH` varchar(36) NOT NULL COMMENT '序号',
  `WW_YH_BM` varchar(36) DEFAULT NULL COMMENT '用户编码',
  `WW_JS_BM` varchar(36) DEFAULT NULL COMMENT '角色编码',
  `WW_CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `WW_XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`WW_XH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

