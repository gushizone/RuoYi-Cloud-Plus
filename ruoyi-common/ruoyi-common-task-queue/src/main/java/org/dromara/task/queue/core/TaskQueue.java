package org.dromara.task.queue.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.task.queue.constant.TaskQueueConstant;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Slf4j
@RequiredArgsConstructor
public class TaskQueue {

    private final RedissonClient redissonClient;

    /**
     * 添加任务
     */
    public <T> void put(TaskMessage<T> taskMessage) {
        try {
            // todo
            RBlockingQueue<TaskMessage<T>> queue = redissonClient.getBlockingQueue(TaskQueueConstant.QUEUE);
            queue.put(taskMessage);
        } catch (Exception e) {
            log.error("添加异步任务失败: {}", e.getMessage(), e);
            throw new ServiceException("添加异步任务失败");
        }
    }

}
