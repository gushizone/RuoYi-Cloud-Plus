package org.dromara.common.teanant.datasource.config;

import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import lombok.RequiredArgsConstructor;
import org.dromara.common.teanant.datasource.core.RedisDynamicDataSourceProvider;
import org.springframework.context.annotation.Bean;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@RequiredArgsConstructor
public class TenantDatasourceCustomerConfiguration {

    private final DefaultDataSourceCreator dataSourceCreator;

    @Bean
    public DynamicDataSourceProvider redisDynamicDataSourceProvider() {
        return new RedisDynamicDataSourceProvider(dataSourceCreator);
    }

}
