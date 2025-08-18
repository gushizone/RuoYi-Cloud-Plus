package org.dromara.common.teanant.datasource.core;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;

import java.util.Map;
import java.util.function.Consumer;

/**
 * redis 数据源属性提供者
 *
 * @author gushizone
 * @since 2025/8/14
 */
@Slf4j
public class RedisDataSourcePropertyProvider {

    private final String cacheName;
    private final String keyPrefix;

    /**
     * 数据源属性上下文，对应已实例化的数据源
     */
    private final Map<String, DataSourceProperty> context;

    public RedisDataSourcePropertyProvider(String cacheName, String keyPrefix) {
        this.cacheName = cacheName;
        this.keyPrefix = keyPrefix;
        this.context = getRedisModulePropertyMap();
    }

    /**
     * 从 redis 获取当前模块的数据源
     */
    private Map<String, DataSourceProperty> getRedisModulePropertyMap() {
        Map<String, DataSourceProperty> map = RedisUtils.getCacheMap(cacheName);
        log.info("获取数据源属性，总计={}", map.size());
        if (StrUtil.isNotBlank(keyPrefix)) {
            map.keySet().removeIf(e -> !e.startsWith(keyPrefix));
        }
        log.info("获取数据源属性，当前模块可用数={}, keys={}", map.size(), map.keySet());
        return map;
    }

    public Map<String, DataSourceProperty> getPropertyMap() {
        return context;
    }

    /**
     * 刷新本地数据源属性
     *
     * @param addConsumer    消费增加的数据源
     * @param removeConsumer 消费移除的数据源
     */
    public synchronized void refresh(Consumer<DataSourceProperty> addConsumer,
                                     Consumer<String> removeConsumer) {
        Map<String, DataSourceProperty> dataSourcePropertyMap = getRedisModulePropertyMap();
        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertyMap.entrySet()) {
            DataSourceProperty dataSourceProperty = context.get(entry.getKey());
            if (dataSourceProperty == null) {
                log.info("新增数据源, ds={}", entry.getKey());
                addConsumer.accept(entry.getValue());
                context.put(entry.getKey(), entry.getValue());
            } else if (isModify(dataSourceProperty, entry.getValue())) {
                log.info("修改数据源, ds={}", entry.getKey());
                addConsumer.accept(entry.getValue());
            } else {
                // ignore
                log.info("忽略数据源，属性未变更, ds={}", entry.getKey());
            }
        }

        for (String ds : context.keySet()) {
            if (!dataSourcePropertyMap.containsKey(ds)) {
                log.info("移除数据源, ds={}", ds);
                removeConsumer.accept(ds);
            }
        }
    }

    /**
     * 判断数据源是否变更
     */
    private boolean isModify(DataSourceProperty oldProperty, DataSourceProperty newProperty) {
        if (!StrUtil.equals(oldProperty.getDriverClassName(), newProperty.getDriverClassName())
            || !StrUtil.equals(oldProperty.getUrl(), newProperty.getUrl())
            || !StrUtil.equals(oldProperty.getUsername(), newProperty.getUsername())
            || !StrUtil.equals(oldProperty.getPassword(), newProperty.getPassword())) {
            return true;
        }
        return false;
    }

}
