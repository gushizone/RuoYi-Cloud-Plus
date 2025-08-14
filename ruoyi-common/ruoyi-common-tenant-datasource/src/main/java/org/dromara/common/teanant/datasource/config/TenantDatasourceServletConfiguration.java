package org.dromara.common.teanant.datasource.config;

import org.dromara.common.teanant.datasource.auto.servlet.TenantDatasourceInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gushizone
 * @since 2025/8/14
 */
@AutoConfiguration
@ConditionalOnProperty(value = "tenant-datasource.auto.enabled", havingValue = "true", matchIfMissing = true)
public class TenantDatasourceServletConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantDatasourceInterceptor()).addPathPatterns("/**");
    }
}
