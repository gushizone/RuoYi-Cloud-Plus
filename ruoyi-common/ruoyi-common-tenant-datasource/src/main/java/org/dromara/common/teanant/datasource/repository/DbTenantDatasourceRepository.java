package org.dromara.common.teanant.datasource.repository;

import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.common.teanant.datasource.core.TenantDataSourceRoutePlanner;
import org.dromara.common.teanant.datasource.domain.entity.SysTenantDatasource;
import org.dromara.common.teanant.datasource.mapper.SysTenantDatasourceMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gushizone
 * @since 2025/8/12
 */
@RequiredArgsConstructor
public class DbTenantDatasourceRepository implements TenantDatasourceRepository {

    private final TenantDataSourceRoutePlanner tenantDataSourceRoutePlanner;

    private final SysTenantDatasourceMapper sysTenantDatasourceMapper;


    @Override
    public List<DataSourceProperty> getList() {
        List<SysTenantDatasource> tenantDatasourceList = sysTenantDatasourceMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(tenantDatasourceList)) {
            return new ArrayList<>();
        }
        List<DataSourceProperty> results = new ArrayList<>();
        for (SysTenantDatasource item : tenantDatasourceList) {
            results.add(build(item));
        }
        return results;
    }


    private DataSourceProperty build(SysTenantDatasource item) {
        DataSourceProperty result = new DataSourceProperty();
        result.setPoolName(tenantDataSourceRoutePlanner.buildKey(item.getTenantId()));
//        result.setType();
        result.setDriverClassName(item.getDriverClassName());
        result.setUrl(item.getUrl());
        result.setUsername(item.getUsername());
        result.setPassword(item.getPassword());
//        result.setJndiName();
//        result.setSeata();
//        result.setP6spy();
//        result.setLazy();
//        result.setInit();
//        result.setDruid();
//        result.setHikari();
//        result.setBeecp();
//        result.setDbcp2();
//        result.setAtomikos();
//        result.setPublicKey();
        return result;
    }
}
