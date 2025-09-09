package org.dromara.common.sequence.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.dromara.common.sequence.constant.SeqConstant;
import org.dromara.common.tenant.helper.TenantHelper;

/**
 * 递增号生成
 *
 * @author gushizone
 * @since 2025/9/4
 */
public class IncrNoGen {

    private static final SeqNoRepository REPOSITORY = SpringUtil.getBean(SeqNoRepository.class);


    /**
     * 获取递增号
     *
     * @param key    唯一标识
     * @param length 号长
     * @return 结果
     */
    public static String next(String key, long ttl, Integer length) {
        return next(key, ttl, length, 1);
    }

    /**
     * 获取递增号
     *
     * @param key    唯一标识
     * @param length 号长
     * @param incr   步长
     * @return 结果
     */
    public static String next(String key, long ttl, int length, int incr) {
        Long incrNum = incr(key, ttl, incr);
        String incrFormat = StrUtil.format("%0{}d", length);
        return String.format(incrFormat, incrNum);
    }

    /**
     * 获取递增数字
     *
     * @param key 唯一标识
     * @return 结果
     */
    public static Long incr(String key, long ttl) {
        return incr(key, ttl, 1);
    }

    /**
     * 获取递增数字
     *
     * @param key  唯一标识
     * @param incr 步长
     * @return 结果
     */
    public static Long incr(String key, long ttl, int incr) {
        if (TenantHelper.isEnable()) {
            key = SeqConstant.GLOBAL_REDIS_KEY_SEQ + key;
        }
        return REPOSITORY.incr(key, ttl, incr);
    }

}
