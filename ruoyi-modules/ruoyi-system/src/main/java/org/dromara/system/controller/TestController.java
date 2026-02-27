package org.dromara.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.UserQueryBo;
import org.dromara.task.queue.file.domain.bo.FileTaskAddBo;
import org.dromara.task.queue.file.service.FileTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试
 *
 * @author gushizone
 * @since 2025/9/16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Resource
    private FileTaskService fileTaskService;

    /**
     * 新增异步任务
     */
    @SaIgnore
    @GetMapping("/task-queue")
    public R<Long> testTaskQueue(UserQueryBo userQueryBo, PageQuery pageQuery) {

        userQueryBo.setPageQuery(pageQuery);
        userQueryBo.setUsername("123");

        FileTaskAddBo<UserQueryBo> fileTaskAddBo = new FileTaskAddBo<>();
        fileTaskAddBo.setTaskType("download-task-test");
        fileTaskAddBo.setTaskMessage(userQueryBo);
        fileTaskAddBo.setTaskName("下载文件" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMATTER));
        fileTaskAddBo.setBizSource("1");

        Long taskId = fileTaskService.addDownloadTask(fileTaskAddBo);
        return R.ok(taskId);
    }

}
