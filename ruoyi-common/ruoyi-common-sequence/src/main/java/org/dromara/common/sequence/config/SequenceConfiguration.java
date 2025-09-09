package org.dromara.common.sequence.config;

import org.dromara.common.redis.config.RedisConfiguration;
import org.dromara.common.sequence.config.properties.SequenceProperties;
import org.dromara.common.sequence.core.SeqNoRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author gushizone
 * @since 2025/9/9
 */
@EnableConfigurationProperties(SequenceProperties.class)
@AutoConfiguration(after = RedisConfiguration.class)
public class SequenceConfiguration {


    @Bean
    public SeqNoRepository seqNoRepository(SequenceProperties sequenceProperties,
                                           JdbcTemplate jdbcTemplate) {
        return new SeqNoRepository(sequenceProperties, jdbcTemplate);
    }

}
