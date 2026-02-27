package org.dromara.resource.api.domain.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Data
public class RemoteFileTaskAddBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 业务来源(file_biz_source)
     */
    private String bizSource;

}
