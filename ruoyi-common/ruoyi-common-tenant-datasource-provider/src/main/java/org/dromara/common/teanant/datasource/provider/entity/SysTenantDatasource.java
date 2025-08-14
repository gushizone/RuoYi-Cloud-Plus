package org.dromara.common.teanant.datasource.provider.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 租户数据源对象 sys_tenant_datasource
 *
 * @author gushizone
 * @date 2025-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
     * 所属模块
     */
    private String module;

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

    /**
     * 版本
     */
    @Version
    private Long version;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;


}
