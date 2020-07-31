package com.duzhuo.common.manager;

import com.duzhuo.common.utils.SpringUtils;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 异步任务管理器
 *
 * @author wanhy
 */
public class AsyncManager {
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
     * 单例模式
     */
    private AsyncManager(){}

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

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
     * @param timerTask
     */
    public void excute2(TimerTask timerTask){
        threadPoolExecutor.execute(timerTask);
    }

    /**
     * 执行任务，可以获得返回值
     * @param callable
     * @param <T>
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public <T> T excute3(Callable<T> callable) throws ExecutionException, InterruptedException {
        Future<T> future = threadPoolExecutor.submit(callable);
        return future.get();
    }


}
