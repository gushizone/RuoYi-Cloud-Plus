package org.dromara.common.teanant.datasource.provider.repository;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.common.teanant.datasource.core.TenantDataSourceHelper;
import org.dromara.common.teanant.datasource.provider.entity.SysTenantDatasource;
import org.dromara.common.teanant.datasource.provider.mapper.SysTenantDatasourceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gushizone
 * @since 2025/8/12
 */
@RequiredArgsConstructor
public class DbTenantDatasourceRepository implements TenantDatasourceRepository {

    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    private final SysTenantDatasourceMapper sysTenantDatasourceMapper;


    @Override
    public List<DataSourceProperty> getList() {
        List<SysTenantDatasource> tenantDatasourceList = sysTenantDatasourceMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(tenantDatasourceList)) {
            return new ArrayList<>();
        }
        List<DataSourceProperty> results = new ArrayList<>();
        for (SysTenantDatasource item : tenantDatasourceList) {
            results.add(convertToDataSourceProperty(item));
        }
        return results;
    }


    /**
     * 转变为数据源属性
     */
    private DataSourceProperty convertToDataSourceProperty(SysTenantDatasource item) {
        DataSourceProperty result = newDataSourceProperty();
        result.setPoolName(TenantDataSourceHelper.buildKey(item.getModule(), item.getTenantId()));
        result.setDriverClassName(item.getDriverClassName());
        result.setUrl(item.getUrl());
        result.setUsername(item.getUsername());
        result.setPassword(item.getPassword());
        return result;
    }

    /**
     * 根据默认数据源, 新建数据源属性
     */
    private DataSourceProperty newDataSourceProperty() {
        Map<String, DataSourceProperty> datasource = dynamicDataSourceProperties.getDatasource();
        DataSourceProperty dataSourceProperty = datasource.get(dynamicDataSourceProperties.getPrimary());

        DataSourceProperty result = new DataSourceProperty();
        result.setType(dataSourceProperty.getType());
        result.setJndiName(dataSourceProperty.getJndiName());
        result.setSeata(dataSourceProperty.getSeata());
        result.setP6spy(dataSourceProperty.getP6spy());
        result.setLazy(dataSourceProperty.getLazy());
        result.setInit(dataSourceProperty.getInit());
        result.setDruid(dataSourceProperty.getDruid());
        result.setHikari(dataSourceProperty.getHikari());
        result.setBeecp(dataSourceProperty.getBeecp());
        result.setDbcp2(dataSourceProperty.getDbcp2());
        result.setAtomikos(dataSourceProperty.getAtomikos());
        result.setPublicKey(dataSourceProperty.getPublicKey());
        return result;
    }
}
