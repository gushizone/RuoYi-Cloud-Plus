package org.dromara.system;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.dromara.common.sequence.core.SeqNoGen;
import org.dromara.common.sequence.enums.SeqDateFormats;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SequenceTest {


    @Test
    public void test() {

        for (int i = 0; i < 39; i++) {
            String no = SeqNoGen.next("TEST", SeqDateFormats.DAY, 4);
            System.out.println("no= " + no);

            String no1 = SeqNoGen.next("TEST", SeqDateFormats.YEAR, 4);
            System.out.println("no1= " + no1);
            String no2 = SeqNoGen.next(SeqDateFormats.MINUTE, 4);
            System.out.println("no2= " + no2);
        }
    }


    @Test
    public void test2MultiThread() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int threadCount = 10; // 线程数
        int total = 1000;       // 总生成次数

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                String no = SeqNoGen.next(SeqDateFormats.MINUTE, 4);
                System.out.println(StrUtil.format("{}, no= {}", Thread.currentThread().getName(), no));
            }, executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        stopWatch.stop();
        System.out.println(StrUtil.format("总计耗时 {} ms", stopWatch.getTime(TimeUnit.MILLISECONDS)));

        executor.shutdown();
    }
}
