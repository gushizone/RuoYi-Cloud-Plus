package org.dromara.task.queue.core;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.task.queue.annotation.TaskHandler;
import org.dromara.task.queue.constant.TaskQueueConstant;
import org.redisson.RedissonShutdownException;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Slf4j
@RequiredArgsConstructor
public class TaskHandleRunner implements ApplicationRunner {


    private final RedissonClient redissonClient;
    private final ThreadPoolTaskExecutor taskExecutor;

    private final Map<String, AbstractTaskHandler> taskHandlerMap = new HashMap<>();


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 处理器发现
        this.handlerDiscover();

        // 初始化队列消费者
        this.initQueueConsumer();
    }

    @SuppressWarnings("unchecked")
    private void handlerDiscover() {
        Map<String, Object> handlerNameMap = SpringUtil.getApplicationContext().getBeansWithAnnotation(TaskHandler.class);
        for (Object bean : handlerNameMap.values()) {
            if (!(bean instanceof AbstractTaskHandler)) {
                log.warn("任务处理器 {} 必须继承 {}", bean.getClass().getName(), AbstractTaskHandler.class.getName());
                continue;
            }
            Class<?> clazz = bean.getClass();
            TaskHandler handler = clazz.getAnnotation(TaskHandler.class);

            this.taskHandlerMap.put(handler.type(), (AbstractTaskHandler) bean);
        }
        log.info("已注册异步下载处理器: {}", this.taskHandlerMap.keySet());
    }

    /**
     * 启动队列消费者
     */
    @SuppressWarnings("unchecked")
    private void initQueueConsumer() {
        if (MapUtil.isEmpty(taskHandlerMap)) {
            return;
        }
        RBlockingQueue<TaskMessage<Object>> queue = redissonClient.getBlockingQueue(TaskQueueConstant.QUEUE);

        for (int i = 0; i < taskExecutor.getMaxPoolSize(); i++) {
            taskExecutor.execute(() -> {
                while (true) {
                    try {
                        TaskMessage<Object> taskMessage = queue.take();
                        log.debug("任务队列, 开始消费, taskId: {}", taskMessage.getId());
                        AbstractTaskHandler handler = taskHandlerMap.get(taskMessage.getType());
                        if (handler != null) {
                            handler.handle(taskMessage);
                        } else {
                            log.info("未找到对应处理器, 忽略任务, taskId: {}", taskMessage.getId());
                        }
                    } catch (InterruptedException ex) {
                        log.info("任务队列, 消费线程结束, interrupted...");
                        break;
                    } catch (RedissonShutdownException ex) {
                        log.info("任务队列, 消费线程结束, redisson is shutdown...");
                        break;
                    } catch (Exception ex) {
                        // 不可中断消费
                        log.error("任务队列, 消费异常: ", ex);
                    }
                }
            });
        }
        log.info("已启用异步任务消费者: {}", taskExecutor.getMaxPoolSize());
    }
}
