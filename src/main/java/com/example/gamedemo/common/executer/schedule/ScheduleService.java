package com.example.gamedemo.common.executer.schedule;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author wengj
 * @description:负责定时事件调度任务
 * @date 2019/7/8
 */
@Component
public class ScheduleService {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

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
    logger.info("开始一个周期任务");
    return scheduledExecutorService.scheduleAtFixedRate(
        task, initialDelay, period, TimeUnit.MILLISECONDS);
  }

  /**
   * 延时任务
   *
   * @param task
   * @param delay
   * @return
   */
  public ScheduledFuture scheduleDelay(Runnable task, long delay) {
    logger.info("开始一个延时期任务");
    return scheduledExecutorService.schedule(task, delay, TimeUnit.MILLISECONDS);
  }
}
