package org.dromara.common.teanant.datasource.core;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

import java.util.function.Supplier;

/**
 * @author gushizone
 * @since 2025/8/13
 */
public class TenantDataSourceHelper {

    private static final TenantDataSourceRoutePlanner TENANT_DATA_SOURCE_ROUTE_PLANNER = SpringUtil.getBean(TenantDataSourceRoutePlanner.class);

    public static void exec(String tenantId, Runnable handle) {
        try {
            String ds = TENANT_DATA_SOURCE_ROUTE_PLANNER.getDataSource(tenantId);
            DynamicDataSourceContextHolder.push(ds);
            handle.run();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    public static <T> T exec(String tenantId, Supplier<T> handle) {
        try {
            String ds = TENANT_DATA_SOURCE_ROUTE_PLANNER.getDataSource(tenantId);
            DynamicDataSourceContextHolder.push(ds);
            return handle.get();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }
}
