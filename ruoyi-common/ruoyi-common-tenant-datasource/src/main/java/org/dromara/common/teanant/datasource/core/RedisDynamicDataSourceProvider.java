package org.dromara.common.teanant.datasource.core;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.teanant.datasource.constant.TenantDatasourceConstant;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * redis 数据源提供者
 *
 * @author zhangwei
 * @since 2025/8/13
 */
public class RedisDynamicDataSourceProvider extends AbstractDataSourceProvider {

    /**
     * 所有数据源
     */
    private final Map<String, DataSourceProperty> dataSourcePropertiesMap = new HashMap<>();

    public RedisDynamicDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator) {
        super(defaultDataSourceCreator);
    }


    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertyMap = RedisUtils.getCacheMap(TenantDatasourceConstant.CACHE);
        return createDataSourceMap(dataSourcePropertyMap);
    }
}
