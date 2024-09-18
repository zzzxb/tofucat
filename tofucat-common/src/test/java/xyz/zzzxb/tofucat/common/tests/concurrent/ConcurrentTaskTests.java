package xyz.zzzxb.tofucat.common.tests.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.concurrent.ConcurrentTask;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * zzzxb
 * 2024/9/13
 */
public class ConcurrentTaskTests {
    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, blockingQueue);

    @Test
    public void cTask() throws ExecutionException, InterruptedException {
        List<Callable<String>> callableList = new LinkedList<>();
        callableList.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "1a";
            }
        });

        callableList.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "2b";
            }
        });

        callableList.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return "3c";
            }
        });

        ConcurrentTask<String> ct = new ConcurrentTask<>(executorService);
        List<String> submit = ct.submit(callableList);
        Assertions.assertArrayEquals(submit.toArray(), new String[]{"1a", "2b", "3c"});
    }
}
