package org.dromara.common.teanant.datasource.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.dromara.common.teanant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.teanant.datasource.core.TenantDataSourceRoutePlanner;
import org.dromara.common.teanant.datasource.mapper.SysTenantDatasourceMapper;
import org.dromara.common.teanant.datasource.repository.DbTenantDatasourceRepository;
import org.dromara.common.teanant.datasource.repository.TenantDatasourceRepository;
import org.dromara.common.teanant.datasource.runner.TenantDatasourceApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
@AutoConfiguration
@AutoConfigureAfter(DynamicDataSourceAutoConfiguration.class)
public class TenantDatasourceConfiguration {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;
    private final DynamicRoutingDataSource dynamicRoutingDataSource;
    private final DefaultDataSourceCreator dataSourceCreator;

    @Bean
    public DynamicDataSourceManager dynamicDataSourceManager() {
        return new DynamicDataSourceManager(dynamicDataSourceProperties, dynamicRoutingDataSource, dataSourceCreator);
    }

    @Bean
    public TenantDataSourceRoutePlanner tenantDataSourceRoutePlanner(DynamicDataSourceManager dynamicDataSourceManager) {
        return new TenantDataSourceRoutePlanner(dynamicDataSourceManager);
    }

    @Bean
    public TenantDatasourceRepository tenantDatasourceRepository(TenantDataSourceRoutePlanner tenantDataSourceRoutePlanner,
                                                                 SysTenantDatasourceMapper sysTenantDatasourceMapper) {
        return new DbTenantDatasourceRepository(tenantDataSourceRoutePlanner, sysTenantDatasourceMapper);
    }

    @Bean
    public TenantDatasourceApplicationRunner tenantDatasourceApplicationRunner(DynamicDataSourceManager dynamicDataSourceManager,
                                                                               TenantDatasourceRepository tenantDatasourceRepository) {
        return new TenantDatasourceApplicationRunner(dynamicDataSourceManager, tenantDatasourceRepository);
    }

}
