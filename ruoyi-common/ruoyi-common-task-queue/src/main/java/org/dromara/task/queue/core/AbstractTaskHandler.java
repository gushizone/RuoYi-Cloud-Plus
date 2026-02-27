package org.dromara.task.queue.core;

/**
 * 抽象的任务处理器
 *
 * @author gushizone
 * @since 2025/9/15
 */
public abstract class AbstractTaskHandler<T, R> {

    protected void handle(TaskMessage<T> taskMessage) {
        this.preHandle(taskMessage);
        R r = this.doHandle(taskMessage);
        this.postHandle(r);
    }

    public void preHandle(TaskMessage<T> taskMessage) {
    }

    public abstract R doHandle(TaskMessage<T> taskMessage);

    public void postHandle(R taskMessage) {
    }
}
