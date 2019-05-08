package com.example.gamedemo.server.game.base.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author: wengj
 * @date: 2019/4/30
 * @description: 程序线程组
 */
public final class WorkThreadPool {

    private static final Logger loger = LoggerFactory.getLogger(WorkThreadPool.class);

    /**
     * 单线程循环执行器
     * 用于异步储存数据
     */
    private static ThreadFactory singleThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("dao-singleThread-%d").setUncaughtExceptionHandler((t, e) -> e.printStackTrace()).build();
    public static ScheduledExecutorService singleThreadSchedule = Executors.newSingleThreadScheduledExecutor(singleThreadFactory);


    /**
     * 设置单线程定时任务
     *
     * @param delay    延迟的毫秒值
     * @param runnable 提交的任务
     * @return
     */
    public static Future singleThreadSchedule(long delay, Runnable runnable) {
        return singleThreadSchedule.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }
}
