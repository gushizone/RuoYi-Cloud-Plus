package org.dromara.common.tenant.datasource.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.tenant.datasource.constant.TenantDatasourceConstant;
import org.dromara.common.tenant.datasource.core.DynamicDataSourceManager;

/**
 * 事件订阅
 *
 * @author gushizone
 * @since 2025/8/15
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDatasourceEventSub {

    private final DynamicDataSourceManager dynamicDataSourceManager;

    public void subscribeRefresh() {
        log.info("动态数据源刷新, 开启监听");
        RedisUtils.subscribe(TenantDatasourceConstant.TOPIC_REFRESH, TenantDatasourceEvent.class,
            event -> {
                log.info("动态数据源刷新, event={}", event);
                log.info("动态数据源刷新, 开始刷新...");
                dynamicDataSourceManager.refresh();
                log.info("动态数据源刷新, 结束.");
            });
    }

}
