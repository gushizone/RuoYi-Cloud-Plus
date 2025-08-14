package org.dromara.common.teanant.datasource.core;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * redis 租户数据源提供者
 *
 * @author gushizone
 * @since 2025/8/14
 */
public class RedisTenantDataSourceProvider extends AbstractDataSourceProvider {

    private final RedisDataSourcePropertyProvider dataSourcePropertyProvider;

    public RedisTenantDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator, RedisDataSourcePropertyProvider dataSourcePropertyProvider) {
        super(defaultDataSourceCreator);
        this.dataSourcePropertyProvider = dataSourcePropertyProvider;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertyMap = dataSourcePropertyProvider.getPropertyMap();
        return createDataSourceMap(dataSourcePropertyMap);
    }
}
