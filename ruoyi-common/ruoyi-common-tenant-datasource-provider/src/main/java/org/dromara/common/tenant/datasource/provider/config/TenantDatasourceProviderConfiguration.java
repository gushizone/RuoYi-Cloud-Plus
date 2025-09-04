package org.dromara.common.tenant.datasource.provider.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.factory.YmlPropertySourceFactory;
import org.dromara.common.tenant.datasource.config.TenantDatasourceConfiguration;
import org.dromara.common.tenant.datasource.event.TenantDatasourceEventPub;
import org.dromara.common.tenant.datasource.provider.mapper.SysTenantDatasourceMapper;
import org.dromara.common.tenant.datasource.provider.service.impl.TenantDatasourceServiceImpl;
import org.dromara.common.tenant.datasource.provider.service.TenantDatasourceService;
import org.dromara.common.tenant.datasource.provider.runner.TenantDatasourceProviderApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
@AutoConfiguration(after = {DynamicDataSourceAutoConfiguration.class, TenantDatasourceConfiguration.class})
@PropertySource(value = "classpath:common-tenant-datasource.yml", factory = YmlPropertySourceFactory.class)
public class TenantDatasourceProviderConfiguration {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    private final TenantDatasourceEventPub tenantDatasourceEventPub;

    private final SysTenantDatasourceMapper sysTenantDatasourceMapper;

    @Bean
    public TenantDatasourceService tenantDatasourceRepository() {
        return new TenantDatasourceServiceImpl(dynamicDataSourceProperties, sysTenantDatasourceMapper);
    }

    @Bean
    public TenantDatasourceProviderApplicationRunner tenantDatasourceApplicationRunner(TenantDatasourceService tenantDatasourceRepository) {
        return new TenantDatasourceProviderApplicationRunner(tenantDatasourceRepository, tenantDatasourceEventPub);
    }

}
