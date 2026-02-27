package org.dromara.task.queue.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Data
@ConfigurationProperties(prefix = "task-queue")
public class TaskQueueProperties {

    /**
     * 单节点消费线程数
     */
    private Integer consumerThreads = 1;

}
