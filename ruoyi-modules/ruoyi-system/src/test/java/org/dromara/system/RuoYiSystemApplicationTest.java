package org.dromara.system;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import org.dromara.common.teanant.datasource.utils.TenantDataSourceHelper;
import org.dromara.system.domain.SysNotice;
import org.dromara.system.mapper.SysNoticeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RuoYiSystemApplicationTest {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @BeforeEach
    public void before() {

        Map<String, DataSource> dataSources = dynamicRoutingDataSource.getDataSources();
        System.out.println("dataSources:  " + dataSources);
    }

    @Test
    public void test() {

        // 默认，000000
        List<SysNotice> list1 = sysNoticeMapper.selectList();
        System.out.println("list1: " + list1);

        // 切换租户数据源
        TenantDataSourceHelper.exec("205949", () -> {
            List<SysNotice> list2 = sysNoticeMapper.selectList();
            System.out.println("list2: " + list2);
        });
    }
}
