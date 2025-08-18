package org.dromara.common.tenant.datasource.auto.servlet;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.tenant.datasource.properties.TenantDatasourceProperties;
import org.dromara.common.tenant.datasource.utils.TenantDataSourceHelper;
import org.dromara.common.tenant.helper.TenantHelper;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * servlet 自动切换数据源
 *
 * @author gushizone
 * @since 2025/8/14
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDatasourceInterceptor implements HandlerInterceptor {

    private final TenantDatasourceProperties tenantDatasourceProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (!tenantDatasourceProperties.getAutoMode()) {
                return true;
            }
            if (StrUtil.isNotBlank(TenantHelper.getTenantId())) {
                String ds = TenantDataSourceHelper.getDataSource(TenantHelper.getTenantId());
                DynamicDataSourceContextHolder.push(ds);
            }
        } catch (Exception e) {
            log.warn("自动切换数据源失败", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            if (!tenantDatasourceProperties.getAutoMode()) {
                return;
            }
            DynamicDataSourceContextHolder.poll();
        } catch (Exception e) {
            log.warn("还原数据源失败", e);
        }
    }
}
