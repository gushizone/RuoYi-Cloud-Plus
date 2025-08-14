package org.dromara.common.teanant.datasource.provider.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.dromara.common.teanant.datasource.config.TenantDatasourceConfiguration;
import org.dromara.common.teanant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.teanant.datasource.provider.mapper.SysTenantDatasourceMapper;
import org.dromara.common.teanant.datasource.provider.repository.DbTenantDatasourceRepository;
import org.dromara.common.teanant.datasource.provider.repository.TenantDatasourceRepository;
import org.dromara.common.teanant.datasource.provider.runner.TenantDatasourceProviderApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
@AutoConfiguration(after = {DynamicDataSourceAutoConfiguration.class, TenantDatasourceConfiguration.class})
public class TenantDatasourceProviderConfiguration {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;
    private final DynamicDataSourceManager dynamicDataSourceManager;

    private final SysTenantDatasourceMapper sysTenantDatasourceMapper;

    @Bean
    public TenantDatasourceRepository tenantDatasourceRepository() {
        return new DbTenantDatasourceRepository(dynamicDataSourceProperties, sysTenantDatasourceMapper);
    }

    @Bean
    public TenantDatasourceProviderApplicationRunner tenantDatasourceApplicationRunner(TenantDatasourceRepository tenantDatasourceRepository) {
        return new TenantDatasourceProviderApplicationRunner(tenantDatasourceRepository, dynamicDataSourceManager);
    }

}
