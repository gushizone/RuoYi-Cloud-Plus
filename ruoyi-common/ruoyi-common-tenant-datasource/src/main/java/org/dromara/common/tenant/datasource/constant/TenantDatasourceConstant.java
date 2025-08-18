package org.dromara.common.tenant.datasource.constant;

import cn.hutool.core.text.StrPool;
import cn.hutool.extra.spring.SpringUtil;
import org.dromara.common.core.constant.GlobalConstants;

/**
 * @author gushizone
 * @since 2025/8/13
 */
public interface TenantDatasourceConstant {

    String CACHE = GlobalConstants.GLOBAL_REDIS_KEY + "tenant_datasource";

    String TOPIC_REFRESH = GlobalConstants.GLOBAL_REDIS_KEY + "topic:tenant_datasource:refresh";

    String KEY_PREFIX = SpringUtil.getApplicationName() + StrPool.DASHED;

}
