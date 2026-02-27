package org.dromara.system;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.domain.bo.UserQueryBo;
import org.dromara.task.queue.adapter.file.FileTaskService;
import org.dromara.task.queue.adapter.file.domain.bo.FileTaskAddBo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class FileTaskTest {

    @Resource
    private FileTaskService fileTaskService;

    @Test
    public void test() {

        UserQueryBo userQueryBo = new UserQueryBo();
        userQueryBo.setPageQuery(new PageQuery());
        userQueryBo.setUsername("123");

        FileTaskAddBo<UserQueryBo> fileTaskAddBo = new FileTaskAddBo<>();
        fileTaskAddBo.setTaskType("download-task-test");
        fileTaskAddBo.setTaskMessage(userQueryBo);
        fileTaskAddBo.setTaskName("下载文件" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMATTER));
        fileTaskAddBo.setBizSource("1");

        Long task = fileTaskService.addTask(fileTaskAddBo);
    }


}
