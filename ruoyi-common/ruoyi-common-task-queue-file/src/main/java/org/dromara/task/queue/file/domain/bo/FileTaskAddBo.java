package org.dromara.task.queue.file.domain.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Data
public class FileTaskAddBo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务消息(查询)
     */
    private T taskMessage;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 业务来源(file_biz_source)
     */
    private String bizSource;

}
