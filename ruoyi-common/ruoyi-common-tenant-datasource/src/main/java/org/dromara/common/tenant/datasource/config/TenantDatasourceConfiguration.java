package org.dromara.common.tenant.datasource.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.dromara.common.tenant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.tenant.datasource.core.RedisDataSourcePropertyProvider;
import org.dromara.common.tenant.datasource.properties.TenantDatasourceProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
@AutoConfiguration(after = DynamicDataSourceAutoConfiguration.class)
@EnableConfigurationProperties(TenantDatasourceProperties.class)
@Import(value = {TenantDatasourceServletConfiguration.class, TenantDatasourceEventConfiguration.class})
public class TenantDatasourceConfiguration {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;
    private final DynamicRoutingDataSource dynamicRoutingDataSource;
    private final DefaultDataSourceCreator dataSourceCreator;

    @Bean
    public DynamicDataSourceManager dynamicDataSourceManager(RedisDataSourcePropertyProvider dataSourcePropertyProvider) {
        return new DynamicDataSourceManager(dynamicDataSourceProperties, dynamicRoutingDataSource, dataSourceCreator, dataSourcePropertyProvider);
    }

}
