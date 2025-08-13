package org.dromara.common.teanant.datasource.runner;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.teanant.datasource.core.DynamicDataSourceManager;
import org.dromara.common.teanant.datasource.repository.TenantDatasourceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

/**
 * 租户数据源初始化
 *
 * @author gushizone
 * @since 2025/8/13
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDatasourceApplicationRunner implements ApplicationRunner {

    private final DynamicDataSourceManager dynamicDataSourceManager;

    private final TenantDatasourceRepository tenantDatasourceRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("加载租户数据源开始...");
        List<DataSourceProperty> dataSourceProperties = tenantDatasourceRepository.getList();
        for (DataSourceProperty dataSourceProperty : dataSourceProperties) {
            dynamicDataSourceManager.add(dataSourceProperty);
        }
        log.info("加载租户数据源完成.");
    }
}
