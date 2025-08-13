package org.dromara.common.teanant.datasource.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dromara.common.teanant.datasource.domain.entity.SysTenantDatasource;

/**
 * 租户数据源Mapper接口
 *
 * @author gushizone
 * @since 2025/8/11
 */
@InterceptorIgnore(tenantLine = "true", dataPermission = "true")
public interface SysTenantDatasourceMapper extends BaseMapper<SysTenantDatasource> {

}
