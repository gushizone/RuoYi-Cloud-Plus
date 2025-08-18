package org.dromara.common.tenant.datasource.config;

import lombok.RequiredArgsConstructor;
import org.dromara.common.tenant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.tenant.datasource.event.TenantDatasourceEventPub;
import org.dromara.common.tenant.datasource.event.TenantDatasourceEventSub;
import org.springframework.context.annotation.Bean;

/**
 * 租户数据源 servlet 自动配置
 *
 * @author gushizone
 * @since 2025/8/14
 */
@RequiredArgsConstructor
public class TenantDatasourceEventConfiguration {

    private final DynamicDataSourceManager dynamicDataSourceManager;
    ;

    /**
     * 事件发布
     */
    @Bean
    public TenantDatasourceEventPub tenantDatasourceEventPub() {
        return new TenantDatasourceEventPub();
    }

    @Bean
    public TenantDatasourceEventSub tenantDatasourceEventSub() {
        TenantDatasourceEventSub tenantDatasourceEventSub = new TenantDatasourceEventSub(dynamicDataSourceManager);
        // 监听刷新事件
        tenantDatasourceEventSub.subscribeRefresh();
        return tenantDatasourceEventSub;
    }
}
