package org.dromara.common.teanant.datasource.provider.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import lombok.RequiredArgsConstructor;
import org.dromara.common.teanant.datasource.core.TenantDataSourceRoutePlanner;
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
@AutoConfiguration(after = DynamicDataSourceAutoConfiguration.class)
public class TenantDatasourceProviderConfiguration {

    private final TenantDataSourceRoutePlanner tenantDataSourceRoutePlanner;
    private final SysTenantDatasourceMapper sysTenantDatasourceMapper;

    @Bean
    public TenantDatasourceRepository tenantDatasourceRepository() {
        return new DbTenantDatasourceRepository(tenantDataSourceRoutePlanner, sysTenantDatasourceMapper);
    }

    @Bean
    public TenantDatasourceProviderApplicationRunner tenantDatasourceApplicationRunner(TenantDatasourceRepository tenantDatasourceRepository) {
        return new TenantDatasourceProviderApplicationRunner(tenantDatasourceRepository);
    }

}
