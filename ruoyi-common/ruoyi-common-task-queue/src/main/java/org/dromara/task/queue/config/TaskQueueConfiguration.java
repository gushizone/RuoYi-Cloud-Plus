package org.dromara.task.queue.config;

import org.dromara.common.redis.config.RedisConfiguration;
import org.dromara.task.queue.config.properties.TaskQueueProperties;
import org.dromara.task.queue.core.TaskHandleRunner;
import org.dromara.task.queue.core.TaskQueue;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author gushizone
 * @since 2025/9/16
 */
@AutoConfigureAfter(RedisConfiguration.class)
@EnableConfigurationProperties(TaskQueueProperties.class)
public class TaskQueueConfiguration {

    @Bean
    public TaskQueue taskQueue(RedissonClient redissonClient) {
        return new TaskQueue(redissonClient);
    }

    @Bean
    public ThreadPoolTaskExecutor taskQueueExecutor(TaskQueueProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setBeanName("taskQueueExecutor");
        executor.setCorePoolSize(properties.getConsumerThreads());
        executor.setMaxPoolSize(properties.getConsumerThreads());
        executor.setMaxPoolSize(properties.getConsumerThreads());
        executor.setQueueCapacity(0);
        executor.initialize();
        return executor;
    }

    @Bean
    public TaskHandleRunner taskHandleRunner(RedissonClient redissonClient,
                                             ThreadPoolTaskExecutor taskQueueExecutor) {
        return new TaskHandleRunner(redissonClient, taskQueueExecutor);
    }

}
