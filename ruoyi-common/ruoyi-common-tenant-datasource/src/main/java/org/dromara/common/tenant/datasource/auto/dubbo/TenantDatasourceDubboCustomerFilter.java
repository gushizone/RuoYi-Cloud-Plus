package org.dromara.common.tenant.datasource.auto.dubbo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.dromara.common.tenant.datasource.properties.TenantDatasourceProperties;
import org.dromara.common.tenant.datasource.utils.TenantDataSourceHelper;
import org.dromara.common.tenant.helper.TenantHelper;

/**
 * dubbo 自动切换数据源
 *
 * @author gushizone
 * @since 2025/8/15
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER}, order = Integer.MAX_VALUE)
public class TenantDatasourceDubboCustomerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!TenantDataSourceHelper.isAuto()) {
            return invoker.invoke(invocation);
        }

        // 1. 切换数据源
        String ds = TenantDataSourceHelper.getDataSource(TenantHelper.getTenantId());
        DynamicDataSourceContextHolder.push(ds);
        // 2. 调用
        Result invoke = invoker.invoke(invocation);
        // 3. 还原数据源
        DynamicDataSourceContextHolder.poll();

        return invoke;
    }


}
