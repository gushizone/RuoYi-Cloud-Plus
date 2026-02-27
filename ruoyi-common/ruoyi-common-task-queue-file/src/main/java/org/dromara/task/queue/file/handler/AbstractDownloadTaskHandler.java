package org.dromara.task.queue.file.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.resource.api.RemoteFileService;
import org.dromara.resource.api.domain.RemoteFile;
import org.dromara.task.queue.core.AbstractTaskHandler;

/**
 * @author gushizone
 * @since 2025/9/16
 */
@Slf4j
public abstract class AbstractDownloadTaskHandler<T, R extends RemoteFile> extends AbstractTaskHandler<T, R> {

    @DubboReference
    private RemoteFileService remoteFileService;

    @Override
    public void postHandle(R result) {

        System.out.println("result: " + result);
    }
}
