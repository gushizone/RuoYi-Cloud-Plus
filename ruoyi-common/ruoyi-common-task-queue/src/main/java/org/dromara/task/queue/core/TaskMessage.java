package org.dromara.task.queue.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author gushizone
 * @since 2025/9/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskMessage<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;

    private T body;
}
