package org.dromara.common.tenant.datasource.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gushizone
 * @since 2025/8/15
 */
@Data
@NoArgsConstructor
public class TenantDatasourceEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事件源
     */
    private String source;
    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public TenantDatasourceEvent(String source) {
        this.source = source;
    }
}
