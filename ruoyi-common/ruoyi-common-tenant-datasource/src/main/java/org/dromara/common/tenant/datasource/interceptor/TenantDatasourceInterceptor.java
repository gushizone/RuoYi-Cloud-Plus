package org.dromara.common.tenant.datasource.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.dromara.common.tenant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.tenant.datasource.core.RedisDataSourcePropertyProvider;

import java.sql.Connection;

/**
 * 租户数据源信息打印
 * - 当使用非默认数据源源时，打印数据源信息
 *
 * @author gushizone
 * @since 2025/8/19
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDatasourceInterceptor implements InnerInterceptor {

    private final DynamicDataSourceManager dynamicDataSourceManager;
    private final RedisDataSourcePropertyProvider dataSourcePropertyProvider;

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        String ds = DynamicDataSourceContextHolder.peek();
        if (StrUtil.isBlank(ds)) {
            // 默认数据源
            return;
        }
        String primaryDs = dynamicDataSourceManager.getPrimaryDataSourceName();
        if (StrUtil.equals(ds, primaryDs)) {
            // 默认数据源
            return;
        }
        log.info("当前使用的数据源 = {}", ds);

        DataSourceProperty dataSourceProperty = dataSourcePropertyProvider.getProperty(ds);
        log.debug("数据源配置 = {}", dataSourceProperty);
    }
}
