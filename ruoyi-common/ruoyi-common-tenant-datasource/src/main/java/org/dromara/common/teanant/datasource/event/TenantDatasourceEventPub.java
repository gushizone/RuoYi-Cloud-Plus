package org.dromara.common.teanant.datasource.event;

import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.teanant.datasource.constant.TenantDatasourceConstant;

/**
 * 事件发布
 *
 * @author gushizone
 * @since 2025/8/15
 */
public class TenantDatasourceEventPub {

    public void publishRefresh() {
        TenantDatasourceEvent event = new TenantDatasourceEvent();
        RedisUtils.publish(TenantDatasourceConstant.TOPIC_REFRESH, event);
    }

}
