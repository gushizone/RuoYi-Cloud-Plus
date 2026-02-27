package org.dromara.task.queue.file.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.resource.api.RemoteFileTaskService;
import org.dromara.resource.api.domain.bo.RemoteFileTaskAddBo;
import org.dromara.task.queue.core.TaskMessage;
import org.dromara.task.queue.core.TaskQueue;
import org.dromara.task.queue.file.domain.bo.FileTaskAddBo;
import org.dromara.task.queue.file.service.FileTaskService;
import org.springframework.stereotype.Service;

/**
 * @author gushizone
 * @since 2025/9/16
 */
@Service
@RequiredArgsConstructor
public class LocalFileTaskService implements FileTaskService {

    @DubboReference
    private RemoteFileTaskService remoteFileTaskService;

    private final TaskQueue taskQueue;

    @Override
    public Long addDownloadTask(FileTaskAddBo<?> fileTaskAddBo) {

        // 新增任务记录
        RemoteFileTaskAddBo remoteFileTaskAddBo = new RemoteFileTaskAddBo();
        remoteFileTaskAddBo.setTaskName(fileTaskAddBo.getTaskName());
        remoteFileTaskAddBo.setBizSource(fileTaskAddBo.getBizSource());
        Long taskId = remoteFileTaskService.addDownloadTask(remoteFileTaskAddBo);

        // 加入任务队列
        taskQueue.put(TaskMessage.builder()
            .id(taskId)
            .type(fileTaskAddBo.getTaskType())
            .body(fileTaskAddBo.getTaskMessage())
            .build());
        return 0L;
    }
}
