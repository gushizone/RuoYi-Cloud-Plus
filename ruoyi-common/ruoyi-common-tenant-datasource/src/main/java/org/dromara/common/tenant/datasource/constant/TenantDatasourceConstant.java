package org.dromara.common.tenant.datasource.constant;

import cn.hutool.core.text.StrPool;
import cn.hutool.extra.spring.SpringUtil;
import org.dromara.common.core.constant.GlobalConstants;

/**
 * @author gushizone
 * @since 2025/8/13
 */
public interface TenantDatasourceConstant {

    /**
     * 租户数据源缓存名称
     */
    String CACHE = GlobalConstants.GLOBAL_REDIS_KEY + "tenant_datasource";
    /**
     * 租户数据源前缀
     * - 名称前缀
     * - 缓存 key 前缀
     */
    String KEY_PREFIX = SpringUtil.getApplicationName() + StrPool.DASHED;

    /**
     * 事件主题 - 刷新
     */
    String TOPIC_REFRESH = GlobalConstants.GLOBAL_REDIS_KEY + "topic:tenant_datasource:refresh";


}
