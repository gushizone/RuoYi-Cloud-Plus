package org.dromara.common.teanant.datasource.core;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.teanant.datasource.constant.TenantDatasourceConstant;

import java.util.Map;
import java.util.function.Consumer;

/**
 * todo 优化
 *
 * @author gushizone
 * @since 2025/8/14
 */
@Slf4j
public class RedisDataSourcePropertyProvider {

    private final Map<String, DataSourceProperty> context;

    public RedisDataSourcePropertyProvider(String key) {
        this.context = getModulePropertyMap(key);
    }

    private static Map<String, DataSourceProperty> getModulePropertyMap(String key) {
        Map<String, DataSourceProperty> map = RedisUtils.getCacheMap(key);
        log.info("获得数据源属性，总计={}", map.size());
        map.keySet().removeIf(e -> !e.startsWith(TenantDatasourceConstant.MODULE_PREFIX));
        log.info("获得数据源属性，当前模块可用数={}, keys={}", map.size(), map.keySet());
        return map;
    }

    /**
     * todo 根据 module 获取
     */
    public Map<String, DataSourceProperty> getPropertyMap() {
        return context;
    }

    /**
     * 重载数据源属性 todo 待优化
     *
     * @param addConsumer    添加
     * @param removeConsumer 移除
     */
    public synchronized void reload(Consumer<DataSourceProperty> addConsumer,
                                    Consumer<String> removeConsumer) {
        Map<String, DataSourceProperty> dataSourcePropertyMap = getModulePropertyMap(TenantDatasourceConstant.CACHE);
        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertyMap.entrySet()) {
            DataSourceProperty dataSourceProperty = context.get(entry.getKey());
            if (dataSourceProperty == null) {
                log.info("新增数据源, ds={}", entry.getKey());
                addConsumer.accept(entry.getValue());
                context.put(entry.getKey(), entry.getValue());
            } else if (isModify(dataSourceProperty, entry.getValue())) {
                log.info("变更数据源, ds={}", entry.getKey());
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
