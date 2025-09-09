package org.dromara.common.sequence.core;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.sequence.config.properties.SequenceProperties;
import org.dromara.common.sequence.entity.SeqNo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * 序列号仓储
 *
 * @author gushizone
 * @since 2025/9/4
 */
@Slf4j
@RequiredArgsConstructor
public class SeqNoRepository {

    private final SequenceProperties sequenceProperties;
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT = "SELECT `id`, `key`, `no`, `retention_deadline`, `create_time`, `update_time` from  `seq_no` WHERE `key` = ? ";
    private static final String SQL_INSERT = "INSERT INTO `seq_no` ( `key`, `no`, `retention_deadline`) VALUES ( ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `seq_no` set `no` = `no` + ?, `retention_deadline` = ? where `key` = ? ";


    public Long incr(String key, long ttl, int incr) {
        long next;
        long nowSeqNo = redisIncr(key, -1, 0);
        int period = getPeriod();
        if (nowSeqNo == 0L) {
            // 第一次获取 或 redis 数据丢失
            log.warn("redis 不存在序列号, 尝试从数据库获取, key={}, incr={}", key, incr);
            SeqNo seqNo = select(key);
            if (seqNo == null) {
                log.info("数据库新增序列号, key={}, incr={}", key, incr);
                insert(key, ttl, incr);
                next = redisIncr(key, ttl, incr);
            } else {
                log.warn("恢复 redis 中的序列号, key={}, incr={}, no={}", key, incr, seqNo.getNo());
                update(key, ttl, incr);
                next = redisIncr(key, ttl, seqNo.getNo() + incr);
            }
            return next;
        }

        next = redisIncr(key, ttl, incr);
        if (next % period == 0 || incr >= period) {
            log.info("更新数据库中的序列号, key={}, next={}, incr={}, period={}", key, next, incr, period);
            update(key, ttl, incr);
        }
        return next;
    }

    private long redisIncr(String key, long ttl, long incr) {
        long result = RedisUtils.incrAtomicValue(key, incr);
        if (ttl > 0 && incr > 0) {
            RedisUtils.expire(key, ttl);
        }
        return result;
    }

    private SeqNo select(String key) {
        List<SeqNo> list = jdbcTemplate.query(SQL_SELECT, new BeanPropertyRowMapper<>(SeqNo.class), key);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    private void insert(String key, long ttl, int incr) {
        Integer seqNo = getSeqNo(incr);
        Date retentionDeadline = getRetentionDeadline(ttl);
        int updated = jdbcTemplate.update(SQL_INSERT, key, seqNo, retentionDeadline);
        if (updated != 1) {
            // insert 可能并发
            throw new ServiceException("系统繁忙, 请稍后重试");
        }
    }

    private void update(String key, long ttl, int incr) {
        Integer seqNo = getSeqNo(incr);
        Date retentionDeadline = getRetentionDeadline(ttl);
        int updated = jdbcTemplate.update(SQL_UPDATE, seqNo, retentionDeadline, key);
        if (updated != 1) {
            // 并发生成单号时, update 可能早于 insert
            throw new ServiceException("系统繁忙, 请稍后重试");
        }
    }

    private int getSeqNo(int incr) {
        int period = getPeriod();
        return (incr / period + 1) * period;
    }

    private int getPeriod() {
        return sequenceProperties.getSyncPeriod();
    }

    private static Date getRetentionDeadline(long ttl) {
        if (ttl < 0) {
            return null;
        }
        return new Date(new Date().getTime() + ttl);
    }

}
