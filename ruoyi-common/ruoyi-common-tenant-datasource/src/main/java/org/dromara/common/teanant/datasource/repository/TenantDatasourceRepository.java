package org.dromara.common.teanant.datasource.repository;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;

import java.util.List;

/**
 * 租户数据源仓储
 *
 * @author gushizone
 * @since 2025/8/13
 */
public interface TenantDatasourceRepository {

    List<DataSourceProperty> getList();
}
