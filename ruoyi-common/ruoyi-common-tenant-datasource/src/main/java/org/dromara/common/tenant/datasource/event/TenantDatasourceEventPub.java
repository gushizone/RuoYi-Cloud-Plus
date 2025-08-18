package org.dromara.common.tenant.datasource.event;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.tenant.datasource.constant.TenantDatasourceConstant;

/**
 * 事件发布
 *
 * @author gushizone
 * @since 2025/8/15
 */
@Slf4j
public class TenantDatasourceEventPub {

    public void publishRefresh(String source) {
        log.info("发布事件，数据源刷新, source={}", source);
        TenantDatasourceEvent event = new TenantDatasourceEvent(source);
        RedisUtils.publish(TenantDatasourceConstant.TOPIC_REFRESH, event);
    }

}
