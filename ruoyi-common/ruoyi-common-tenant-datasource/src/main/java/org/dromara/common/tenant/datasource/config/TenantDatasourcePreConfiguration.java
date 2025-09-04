package org.dromara.common.tenant.datasource.config;

import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import lombok.RequiredArgsConstructor;
import org.dromara.common.tenant.datasource.constant.TenantDatasourceConstant;
import org.dromara.common.tenant.datasource.core.RedisDataSourcePropertyProvider;
import org.dromara.common.tenant.datasource.core.TenantDataSourceProvider;
import org.springframework.context.annotation.Bean;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
public class TenantDatasourcePreConfiguration {

    private final DefaultDataSourceCreator dataSourceCreator;

    @Bean
    public RedisDataSourcePropertyProvider dataSourcePropertyProvider() {
        return new RedisDataSourcePropertyProvider(TenantDatasourceConstant.CACHE, TenantDatasourceConstant.KEY_PREFIX);
    }

    @Bean
    public DynamicDataSourceProvider redisDynamicDataSourceProvider(RedisDataSourcePropertyProvider dataSourcePropertyProvider) {
        return new TenantDataSourceProvider(dataSourceCreator, dataSourcePropertyProvider);
    }

}
