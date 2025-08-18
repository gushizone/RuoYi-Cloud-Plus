package org.dromara.common.tenant.datasource.config;

import lombok.RequiredArgsConstructor;
import org.dromara.common.tenant.datasource.auto.servlet.TenantDatasourceInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 租户数据源 servlet 自动配置
 *
 * @author gushizone
 * @since 2025/8/14
 */
@RequiredArgsConstructor
public class TenantDatasourceServletConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantDatasourceInterceptor()).addPathPatterns("/**");
    }
}
