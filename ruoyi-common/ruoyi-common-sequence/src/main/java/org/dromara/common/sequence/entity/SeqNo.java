package org.dromara.common.sequence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gushizone
 * @since 2025/9/4
 */
@Data
public class SeqNo implements Serializable {

    private Long id;

    private String seqKey;

    private Long no;

    private Date retentionDeadline;

    private Date createTime;

    private Date updateTime;
}
