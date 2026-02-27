package org.dromara.task.queue.file.service;

import org.dromara.task.queue.file.domain.bo.FileTaskAddBo;

public interface FileTaskService {

    Long addDownloadTask(FileTaskAddBo<?> fileTaskAddBo);

}
