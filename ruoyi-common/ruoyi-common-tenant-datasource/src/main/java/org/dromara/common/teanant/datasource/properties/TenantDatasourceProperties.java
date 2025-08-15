package org.dromara.common.teanant.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tenant-datasource")
public class TenantDatasourceProperties {

    /**
     * 自动模式
     * - servlet
     * - dubbo
     */
    private Boolean autoMode = true;

}
