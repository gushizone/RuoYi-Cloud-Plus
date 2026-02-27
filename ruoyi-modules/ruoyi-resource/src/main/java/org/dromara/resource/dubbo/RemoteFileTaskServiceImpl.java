package org.dromara.resource.dubbo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.resource.api.RemoteFileTaskService;
import org.dromara.resource.api.domain.bo.RemoteFileTaskAddBo;
import org.springframework.stereotype.Service;

/**
 * @author gushizone
 * @since 2025/9/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
@DubboService
public class RemoteFileTaskServiceImpl implements RemoteFileTaskService {

    @Override
    public Long addDownloadTask(RemoteFileTaskAddBo fileTaskSaveBo) {


        return 0L;
    }
}
