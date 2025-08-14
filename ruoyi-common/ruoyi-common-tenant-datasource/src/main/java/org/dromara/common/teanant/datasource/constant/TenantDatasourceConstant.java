package org.dromara.common.teanant.datasource.constant;

import cn.hutool.core.text.StrPool;
import cn.hutool.extra.spring.SpringUtil;

/**
 * @author gushizone
 * @since 2025/8/13
 */
public interface TenantDatasourceConstant {

    String CACHE = "global:tenant_datasource";

    String MODULE_PREFIX = SpringUtil.getApplicationName() + StrPool.DASHED;

}
