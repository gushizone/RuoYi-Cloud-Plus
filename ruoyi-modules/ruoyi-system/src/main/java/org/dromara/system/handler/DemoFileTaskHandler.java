package org.dromara.system.handler;

import lombok.extern.slf4j.Slf4j;
import org.dromara.resource.api.domain.RemoteFile;
import org.dromara.system.domain.bo.UserQueryBo;
import org.dromara.task.queue.annotation.TaskHandler;
import org.dromara.task.queue.core.TaskMessage;
import org.dromara.task.queue.file.handler.AbstractDownloadTaskHandler;

@Slf4j
@TaskHandler(type = DemoFileTaskHandler.TYPE)
public class DemoFileTaskHandler extends AbstractDownloadTaskHandler<UserQueryBo, RemoteFile> {

    public static final String TYPE = "download-task-test";

    @Override
    public RemoteFile doHandle(TaskMessage<UserQueryBo> taskMessage) {
        log.info("msg: {}", taskMessage);


        RemoteFile remoteFile = new RemoteFile();
        remoteFile.setOssId(123L);
        return remoteFile;
    }
}
