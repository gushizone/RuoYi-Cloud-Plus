package org.dromara.common.sequence.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.sequence.enums.SeqDateFormats;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 序列单号生成
 *
 * @author gushizone
 * @since 2025/9/4
 */
public class SeqNoGen {

    private final static String PARAMS_BIZ_CODE = "bizCode";
    private final static String PARAMS_DATE = "date";
    private final static String PARAMS_INCR_NO = "incrNo";

    private final static String DEFAULT_TEMPLATE = "{bizCode}{date}{incrNo}";


    /**
     * 获取序列号
     * @param dateFormat 日期格式
     * @param incrLength 递增数字长度
     * @return 结果
     */
    public static String next(SeqDateFormats dateFormat, Integer incrLength) {
        return next(StringUtils.EMPTY, dateFormat, incrLength);
    }

    /**
     * 获取序列号
     * @param bizCode 业务编码
     * @param dateFormat 日期格式
     * @param incrLength 递增数字长度
     * @return 结果
     */
    public static String next(String bizCode, SeqDateFormats dateFormat, Integer incrLength) {
        Map<String, Object> params = new HashMap<>();

        params.put(PARAMS_BIZ_CODE, StringUtils.defaultString(bizCode));

        String date = DateUtil.format(new Date(), dateFormat.getDateFormat());
        params.put(PARAMS_DATE, date);

        String key = bizCode + date;
        String incrNo = IncrNoGen.next(key, dateFormat.getTtl(), incrLength);
        params.put(PARAMS_INCR_NO, incrNo);
        return StrUtil.format(DEFAULT_TEMPLATE, params);
    }





}
