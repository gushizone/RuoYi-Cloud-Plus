package org.dromara.task.queue.constant;

import cn.hutool.extra.spring.SpringUtil;
import org.dromara.common.core.constant.GlobalConstants;

/**
 * @author gushizone
 * @since 2025/9/15
 */
public class TaskQueueConstant {

    /**
     * 任务队列
     */
    public static final String QUEUE = GlobalConstants.GLOBAL_REDIS_KEY + "task-queue:" + SpringUtil.getApplicationName();



}
