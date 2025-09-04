package org.dromara.common.tenant.datasource.provider.service;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;

import java.util.List;

/**
 * 租户数据源仓储
 *
 * @author gushizone
 * @since 2025/8/13
 */
public interface TenantDatasourceService {

    List<DataSourceProperty> getList();
}
