package xyz.zzzxb.tofucat.common.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 异步批量执行任务, 并返回批量结果
 * zzzxb
 * 2023/3/2
 */
public class ConcurrentTask<T> {
    private final ExecutorService executor;

    public ConcurrentTask(ExecutorService executor) {
        this.executor = executor;
    }

    public List<T> submit(List<? extends Callable<T>> callableList, TimeUnit timeUnit, long timeout) throws InterruptedException, ExecutionException {
        final CompletionService<T> completion = new ExecutorCompletionService<T>(executor);
        callableList.forEach(completion::submit);
        return collectResult(callableList.size(), completion, timeUnit, timeout);
    }

    /**
     * 提交任务列表
     * @param callableList 自定义一个带返回值的线程列表
     * @return result set
     */
    public List<T> submit(List<? extends Callable<T>> callableList) throws InterruptedException, ExecutionException {
        return submit(callableList, TimeUnit.SECONDS, 3);
    }

    /**
     * 收集结果集(任务执行完毕先后顺序)
     * @param dataNum 需要收集数据的总量
     * @param completion 任务集合
     * @param timeUnit 超时时间单位
     * @param timeout 超时时间
     * @return result set
     */
    private List<T> collectResult(int dataNum, final CompletionService<T> completion, TimeUnit timeUnit, long timeout) throws InterruptedException, ExecutionException {
        final List<T> result = new LinkedList<>();
        for (int i = 0; i < dataNum; i++) {
            Future<T> take = completion.take();
            try {
                result.add(take.get(timeout, timeUnit));
            }catch (TimeoutException ignored) {
            }finally {
                take.cancel(true);
            }
        }
        return result;
    }
}
