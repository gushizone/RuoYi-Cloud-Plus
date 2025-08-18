package org.dromara.common.tenant.datasource.core;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 动态数据源管理
 *
 * @author gushizone
 * @since 2025/8/12
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicDataSourceManager {


    private final DynamicDataSourceProperties dynamicDataSourceProperties;
    private final DynamicRoutingDataSource dynamicRoutingDataSource;
    private final DefaultDataSourceCreator dataSourceCreator;

    private final RedisDataSourcePropertyProvider dataSourcePropertyProvider;

    /**
     * 获取默认数据源名称
     *
     * @return 数据源名称
     */
    public String getPrimaryDataSourceName() {
        return dynamicDataSourceProperties.getPrimary();
    }

    /**
     * 获取所有数据源名称
     */
    public Set<String> getDataSourceNames() {
        return dynamicRoutingDataSource.getDataSources().keySet();
    }

    /**
     * 判断数据源是否存在
     *
     * @param ds 数据源名称
     * @return 是否存在
     */
    public boolean exists(String ds) {
        return getDataSourceNames().contains(ds);
    }

    /**
     * 添加数据源
     * - 相同的 poolName 会被覆盖
     *
     * @param dataSourceProperty 数据源配置，poolName 对应数据源名称(ds)
     * @return 操作结果
     */
    public boolean add(DataSourceProperty dataSourceProperty) {
        try {
            DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
            dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
        } catch (Exception e) {
            log.error("添加数据源失败, dataSourceProperty={}, 原因: {}", dataSourceProperty, e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 移除数据源
     *
     * @param ds 数据源名称
     * @return 操作结果
     */
    public boolean remove(String ds) {
        try {
            dynamicRoutingDataSource.removeDataSource(ds);
        } catch (Exception e) {
            log.error("移除数据源失败, ds={}, 原因: {}", ds, e.getMessage(), e);
        }
        return true;
    }

    /**
     * 刷新数据源
     * - 不影响默认数据源
     */
    public void refresh() {
        try {
            dataSourcePropertyProvider.refresh(this::add, this::remove);
        } catch (Exception e) {
            log.error("重新加载数据源失败, 原因: {}", e.getMessage(), e);
        }
    }
}
