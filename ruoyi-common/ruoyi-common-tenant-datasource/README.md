# 租户数据源

基于动态数据源实现租户数据隔离，可以根据配置自动切换和刷新数据源。

+ 租户数据源配置表，若租户没有配置，则使用默认数据源。
+ 自动模式，默认开启，根据请求的当前租户，自动切换数据源。
  + 支持 servlet 调用
  + 支持 dubbo 调用

+ 手动模式， 传递租户编号， 切换数据源。
+ 支持分布式应用，可基于事件自动刷新。
+ 可能存在的问题，同 [动态数据源 | dynamic-datasource](https://github.com/baomidou/dynamic-datasource)



```sql
CREATE TABLE `sys_tenant_datasource` (
  `id` bigint(20) NOT NULL,
  `module` varchar(50) NOT NULL COMMENT '所属模块',
  `tenant_id` varchar(20) NOT NULL DEFAULT '000000' COMMENT '租户编号',
  `url` varchar(500) NOT NULL COMMENT '数据源连接地址',
  `username` varchar(30) NOT NULL COMMENT '数据源账号',
  `password` varchar(50) NOT NULL COMMENT '数据源密码',
  `driver_class_name` varchar(100) NOT NULL DEFAULT 'com.mysql.cj.jdbc.Driver' COMMENT '数据库驱动名',
  `ds_properties` varchar(1000) DEFAULT NULL COMMENT '数据源的其他配置',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint(20) DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '上传人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户数据源配置';
```



```sql
// 手动模式示例

// 默认数据源
List<SysNotice> list1 = sysNoticeMapper.selectList();
System.out.println("list1: " + list1);

// 切换租户数据源
TenantDataSourceHelper.exec("205949", () -> {
    List<SysNotice> list2 = sysNoticeMapper.selectList();
    System.out.println("list2: " + list2);
});
```



