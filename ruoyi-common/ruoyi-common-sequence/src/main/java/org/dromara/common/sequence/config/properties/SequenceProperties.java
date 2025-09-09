package org.dromara.common.sequence.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author gushizone
 * @since 2025/9/9
 */
@Data
@ConfigurationProperties("sequence")
public class SequenceProperties {

    /**
     * 同步周期(默认 20)
     * - redis 每自增数差为 n 时, 同步更新 mysql
     * - n 越小, 数据一致性越强, 性能越差
     */
    public Integer syncPeriod = 20;


}
