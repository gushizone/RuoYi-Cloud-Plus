package org.dromara.task.queue.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TaskHandler {

    /**
     * 类型
     */
    String type();

}
