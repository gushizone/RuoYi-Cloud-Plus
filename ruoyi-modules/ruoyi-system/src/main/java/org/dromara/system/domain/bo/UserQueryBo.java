package org.dromara.system.domain.bo;

import lombok.Data;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserQueryBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private PageQuery pageQuery;

    private String username;

}
