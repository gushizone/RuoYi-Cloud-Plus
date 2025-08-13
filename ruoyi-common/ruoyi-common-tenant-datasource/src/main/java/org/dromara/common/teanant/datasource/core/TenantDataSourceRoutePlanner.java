package org.dromara.common.teanant.datasource.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 租户数据源路由规划工具
 *
 * @author gushizone
 * @since 2025/8/13
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDataSourceRoutePlanner {


    private final DynamicDataSourceManager dynamicDataSourceManager;

    /**
     * 获取租户数据源名称
     *
     * @param tenantId 租户编号
     * @return 数据源名称
     */
    public String getDataSource(String tenantId) {
        String ds = buildKey(tenantId);
        if (!dynamicDataSourceManager.exists(ds)) {
            ds = dynamicDataSourceManager.getPrimaryDataSourceName();
        }
        return ds;
    }

    /**
     * 通过租户构建数据源 key, todo 待优化
     */
    public String buildKey(String tenantId) {
        return "tenant-" + tenantId;
    }
}
