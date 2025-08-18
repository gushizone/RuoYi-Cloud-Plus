package org.dromara.common.teanant.datasource.provider.runner;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.StreamUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.teanant.datasource.constant.TenantDatasourceConstant;
import org.dromara.common.teanant.datasource.event.TenantDatasourceEventPub;
import org.dromara.common.teanant.datasource.provider.repository.TenantDatasourceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;
import java.util.Map;

/**
 * 租户数据源初始化
 *
 * @author gushizone
 * @since 2025/8/13
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDatasourceProviderApplicationRunner implements ApplicationRunner {

    private final TenantDatasourceRepository tenantDatasourceRepository;

    private final TenantDatasourceEventPub tenantDatasourceEventPub;

    @Override
    public void run(ApplicationArguments args) {
        log.info("初始化租户数据源缓存，开始...");

        // 初始化缓存
        initCache();

        // 发布刷新事件
        tenantDatasourceEventPub.publishRefresh("数据源提供者启动");

        log.info("初始化租户数据源缓存，结束.");
    }

    /**
     * 初始化缓存(覆盖和移除)
     */
    private void initCache() {
        // 获取数据库配置
        List<DataSourceProperty> dataSourceProperties = tenantDatasourceRepository.getList();
        Map<String, DataSourceProperty> dsMap = StreamUtils.toIdentityMap(dataSourceProperties, DataSourceProperty::getPoolName);
        // 获取缓存配置
        Map<String, DataSourceProperty> cacheDsMap = RedisUtils.getCacheMap(TenantDatasourceConstant.CACHE);

        for (String cacheDs : cacheDsMap.keySet()) {
            DataSourceProperty dataSourceProperty = dsMap.get(cacheDs);
            if (dataSourceProperty != null) {
                RedisUtils.setCacheMapValue(TenantDatasourceConstant.CACHE, cacheDs, dataSourceProperty);
            } else {
                RedisUtils.delCacheMapValue(TenantDatasourceConstant.CACHE, cacheDs);
            }
        }
    }
}
