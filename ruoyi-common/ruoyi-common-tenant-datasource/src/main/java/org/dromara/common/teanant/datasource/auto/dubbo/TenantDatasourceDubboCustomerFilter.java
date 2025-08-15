package org.dromara.common.teanant.datasource.auto.dubbo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.dromara.common.teanant.datasource.properties.TenantDatasourceProperties;
import org.dromara.common.teanant.datasource.utils.TenantDataSourceHelper;
import org.dromara.common.tenant.helper.TenantHelper;

/**
 * dubbo 自动切换数据源
 *
 * @author gushizone
 * @since 2025/8/15
 */
@Slf4j
@Activate(group = {CommonConstants.CONSUMER}, order = Integer.MAX_VALUE)
public class TenantDatasourceDubboCustomerFilter implements Filter {

    private final TenantDatasourceProperties tenantDatasourceProperties = SpringUtil.getBean(TenantDatasourceProperties.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            if (!tenantDatasourceProperties.getAutoMode()) {
                return invoker.invoke(invocation);
            }
            // 1. 切换数据源
            if (StrUtil.isNotBlank(TenantHelper.getTenantId())) {
                String ds = TenantDataSourceHelper.getDataSource(TenantHelper.getTenantId());
                DynamicDataSourceContextHolder.push(ds);
            }
            // 2. 调用
            return invoker.invoke(invocation);
        } catch (Exception e) {
            log.warn("自动切换数据源失败", e);
        } finally {
            try {
                if (!tenantDatasourceProperties.getAutoMode()) {
                    // 3. 还原数据源
                    DynamicDataSourceContextHolder.poll();
                }
            } catch (Exception e) {
                log.warn("还原数据源失败", e);
            }
        }
        return invoker.invoke(invocation);
    }


}
