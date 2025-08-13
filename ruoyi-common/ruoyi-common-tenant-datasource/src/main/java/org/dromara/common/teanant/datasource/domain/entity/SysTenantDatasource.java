package org.dromara.common.teanant.datasource.domain.entity;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 租户数据源对象 sys_tenant_datasource
 *
 * @author gushizone
 * @date 2025-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
//@TableName("sys_tenant_datasource")
@TableName("zt_sys_tenant_datasource")
public class SysTenantDatasource extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源连接地址
     */
    private String url;

    /**
     * 数据源账号
     */
    private String username;

    /**
     * 数据源密码
     */
    private String password;

    /**
     * 数据库驱动名
     */
    private String driverClassName;

    /**
     * 数据源的其他配置
     */
    private String dsProperties;

    /**
     * 备注
     */
    private String remark;


}
