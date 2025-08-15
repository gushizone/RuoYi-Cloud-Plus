package org.dromara.common.teanant.datasource.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gushizone
 * @since 2025/8/15
 */
@Data
public class TenantDatasourceEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime = new Date();

}
