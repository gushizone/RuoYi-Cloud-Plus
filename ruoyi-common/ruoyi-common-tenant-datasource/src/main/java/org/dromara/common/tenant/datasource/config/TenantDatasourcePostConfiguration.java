package org.dromara.common.tenant.datasource.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.dromara.common.tenant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.tenant.datasource.core.RedisDataSourcePropertyProvider;
import org.dromara.common.tenant.datasource.interceptor.TenantDatasourceInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

/**
 * @author gushizone
 * @since 2025/8/11
 */
@AutoConfigureAfter(TenantDatasourceConfiguration.class)
@RequiredArgsConstructor
public class TenantDatasourcePostConfiguration {

    private final RedisDataSourcePropertyProvider dataSourcePropertyProvider;
    private final DynamicDataSourceManager dynamicDataSourceManager;

    private final MybatisPlusInterceptor mybatisPlusInterceptor;

    @PostConstruct
    public void init() {
        mybatisPlusInterceptor.addInnerInterceptor(new TenantDatasourceInterceptor(dynamicDataSourceManager, dataSourcePropertyProvider));
    }

}
