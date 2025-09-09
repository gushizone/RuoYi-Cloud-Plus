package org.dromara.common.sequence.enums;

import cn.hutool.core.date.DateUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gushizone
 * @since 2025/9/8
 */
@Getter
@AllArgsConstructor
public enum SeqDateFormats {
    /**
     * 年, 保留近 10 年记录
     */
    YEAR("yyyy", DateUnit.DAY.getMillis() * 365 * 10),
    /**
     * 月, 保留近 12 月记录
     */
    MONTH("yyyyMM", DateUnit.DAY.getMillis() * 365),
    /**
     * 日, 保留近 90 天记录
     */
    DAY("yyyyMMdd", DateUnit.DAY.getMillis() * 90),
    /**
     * 小时, 保留近 30 天记录
     */
    HOUR("yyyyMMddHH", DateUnit.DAY.getMillis() * 30),
    /**
     * 分钟, 保留近 15 天记录
     */
    MINUTE("yyyyMMddHHmm", DateUnit.DAY.getMillis() * 15),

    ;

    private final String dateFormat;
    private final Long ttl;

}
