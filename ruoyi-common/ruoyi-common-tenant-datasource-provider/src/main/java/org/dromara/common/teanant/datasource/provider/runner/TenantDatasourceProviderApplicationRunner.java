package org.dromara.common.teanant.datasource.provider.runner;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.common.core.utils.StreamUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.teanant.datasource.constant.TenantDatasourceConstant;
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

    @Override
    public void run(ApplicationArguments args) {
        log.info("加载租户数据源开始...");
        // todo 根据服务名加载
        List<DataSourceProperty> dataSourceProperties = tenantDatasourceRepository.getList();
        if (CollectionUtils.isNotEmpty(dataSourceProperties)) {
            Map<String, DataSourceProperty> dataSourcePropertyMap = StreamUtils.toIdentityMap(dataSourceProperties, DataSourceProperty::getPoolName);
            RedisUtils.setCacheMap(TenantDatasourceConstant.CACHE, dataSourcePropertyMap);
        }
        log.info("加载租户数据源完成.");
    }
}
