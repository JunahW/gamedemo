package com.example.gamedemo.common.executer.schedule;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author wengj
 * @description:负责定时事件调度任务
 * @date 2019/7/8
 */
@Component
public class ScheduleService {
  private static ThreadFactory threadFactory =
      new ThreadFactoryBuilder()
          .setNameFormat("Schedule-Thread-%d")
          .setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
          .build();

  /** 调度任务池 */
  private ScheduledExecutorService scheduledExecutorService =
      new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), threadFactory);

  /**
   * 周期任务
   *
   * @param task
   * @param initialDelay
   * @param period
   * @return
   */
  public ScheduledFuture scheduleAtFixedRate(Runnable task, long initialDelay, long period) {
    return scheduledExecutorService.scheduleAtFixedRate(
        task, initialDelay, period, TimeUnit.MILLISECONDS);
  }

  /**
   * 延时任务
   *
   * @param task
   * @param initialDelay
   * @param delay
   * @return
   */
  public ScheduledFuture scheduleWithFixedDelay(Runnable task, long initialDelay, long delay) {
    return scheduledExecutorService.scheduleWithFixedDelay(
        task, initialDelay, delay, TimeUnit.MILLISECONDS);
  }
}
