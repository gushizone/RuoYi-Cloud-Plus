CREATE TABLE `seq_no`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `key`                varchar(100) NOT NULL COMMENT '序列键',
    `no`                 bigint(20)   NOT NULL COMMENT '序号',
    `retention_deadline` datetime              DEFAULT NULL COMMENT '留存截止时间',
    `create_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uidx_key` (`key`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='序列单号生成';
