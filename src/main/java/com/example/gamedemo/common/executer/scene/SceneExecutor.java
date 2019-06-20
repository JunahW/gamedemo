package com.example.gamedemo.common.executer.scene;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description：场景线程执行器
 * @date 2019/6/12
 */
@Component
public class SceneExecutor {

  private static final Logger logger = LoggerFactory.getLogger(SceneExecutor.class);
  /** 默认线程数量 */
  private static final int DEFAULT_THREAD_SIZE = Runtime.getRuntime().availableProcessors();

  /** 线程池数量，可以控制每个账户在同一个线程执行 */
  private static final ScheduledThreadPoolExecutor[] SCENE_SERVICE =
      new ScheduledThreadPoolExecutor[DEFAULT_THREAD_SIZE];

  static {
    logger.info("初始化场景线程。。。");
    ThreadFactory singleThreadFactory =
        new ThreadFactoryBuilder()
            .setNameFormat("Scene-Thread-%d")
            .setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
            .build();
    for (int i = 0; i < DEFAULT_THREAD_SIZE; i++) {
      SCENE_SERVICE[i] =
          new ScheduledThreadPoolExecutor(
              1, singleThreadFactory, new ThreadPoolExecutor.DiscardPolicy());
    }
    logger.info("初始化场景线程完成");
  }

  /**
   * 获取下标
   *
   * @param id
   * @return
   */
  private static int modeIndex(int id) {
    return id % DEFAULT_THREAD_SIZE;
  }

  /**
   * 执行场景线程
   *
   * @param sceneId
   * @param task
   */
  public static void addScheduleTask(int sceneId, Runnable task) {
    int index = modeIndex(sceneId);
    SCENE_SERVICE[index].submit(task);
  }

  /**
   * 周期运行
   *
   * @param sceneId
   * @param delay
   * @param timeUnit
   * @param task
   */
  public static void addDelayTask(int sceneId, long delay, TimeUnit timeUnit, Runnable task) {
    int index = modeIndex(sceneId);
    SCENE_SERVICE[index].schedule(task, delay, timeUnit);
  }

  /**
   * 周期运行
   *
   * @param sceneId
   * @param delay
   * @param period
   * @param task
   */
  public static void addScheduleTask(int sceneId, long delay, long period, Runnable task) {
    int index = modeIndex(sceneId);
    SCENE_SERVICE[index].scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
  }
}
