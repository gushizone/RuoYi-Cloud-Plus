package org.dromara.common.teanant.datasource.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.dromara.common.teanant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.teanant.datasource.core.RedisDataSourcePropertyProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
@AutoConfiguration(after = DynamicDataSourceAutoConfiguration.class)
public class TenantDatasourceConfiguration {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;
    private final DynamicRoutingDataSource dynamicRoutingDataSource;
    private final DefaultDataSourceCreator dataSourceCreator;

    @Bean
    public DynamicDataSourceManager dynamicDataSourceManager(RedisDataSourcePropertyProvider dataSourcePropertyProvider) {
        return new DynamicDataSourceManager(dynamicDataSourceProperties, dynamicRoutingDataSource, dataSourceCreator, dataSourcePropertyProvider);
    }

}
