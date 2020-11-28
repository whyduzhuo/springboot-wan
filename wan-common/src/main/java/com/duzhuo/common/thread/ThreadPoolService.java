package com.duzhuo.common.thread;

import com.duzhuo.common.utils.SpringUtils;
import org.springframework.stereotype.Component;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 异步任务管理器
 *
 * @author 万宏远
 */
@Component
public class ThreadPoolService {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 异步操作任务调度线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = SpringUtils.getBean("threadPoolExecutor");

    /**
     * 执行任务
     * 延时执行
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        Threads.shutdownAndAwaitTermination(executor);
    }

    /**
     * 执行任务/立即执行
     * @param runnable
     */
    public void execute(Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 执行任务，可以获得返回值
     * @param callable
     * @param <T>
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public <T> T execute(Callable<T> callable) throws ExecutionException, InterruptedException {
        Future<T> future = threadPoolExecutor.submit(callable);
        return future.get();
    }


}
