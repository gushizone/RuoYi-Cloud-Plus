package org.dromara.resource.api;

import org.dromara.resource.api.domain.bo.RemoteFileTaskAddBo;

/**
 * @author gushizone
 * @since 2025/9/15
 */
public interface RemoteFileTaskService {


    Long addDownloadTask(RemoteFileTaskAddBo fileTaskSaveBo);


}
