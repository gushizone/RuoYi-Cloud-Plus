package org.dromara.common.tenant.datasource.utils;

import cn.hutool.core.text.StrPool;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.dromara.common.tenant.datasource.constant.TenantDatasourceConstant;
import org.dromara.common.tenant.datasource.core.DynamicDataSourceManager;

import java.util.function.Supplier;

/**
 * @author gushizone
 * @since 2025/8/13
 */
public class TenantDataSourceHelper {

    private static final DynamicDataSourceManager DYNAMIC_DATA_SOURCE_MANAGER = SpringUtil.getBean(DynamicDataSourceManager.class);

    /**
     * 通过租户构建数据源 key (module-tenantId)
     */
    public static String buildKey(String tenantId) {
        return TenantDatasourceConstant.KEY_PREFIX + tenantId;
    }

    /**
     * 通过租户构建数据源 key (module-tenantId)
     */
    public static String buildKey(String module, String tenantId) {
        return module + StrPool.DASHED + tenantId;
    }

    /**
     * 获取租户数据源名称
     *
     * @param tenantId 租户编号
     * @return 数据源名称
     */
    public static String getDataSource(String tenantId) {
        String ds = TenantDataSourceHelper.buildKey(tenantId);
        if (!DYNAMIC_DATA_SOURCE_MANAGER.exists(ds)) {
            ds = DYNAMIC_DATA_SOURCE_MANAGER.getPrimaryDataSourceName();
        }
        return ds;
    }


    public static void exec(String tenantId, Runnable handle) {
        try {
            String ds = getDataSource(tenantId);
            DynamicDataSourceContextHolder.push(ds);
            handle.run();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    public static <T> T exec(String tenantId, Supplier<T> handle) {
        try {
            String ds = getDataSource(tenantId);
            DynamicDataSourceContextHolder.push(ds);
            return handle.get();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }
}
