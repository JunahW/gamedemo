package com.example.gamedemo.common.executer.scene;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneCommand;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.common.executer.scene.impl.AbstractSceneRateCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

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
  public static void addTask(int sceneId, Runnable task) {
    int index = modeIndex(sceneId);
    SCENE_SERVICE[index].submit(task);
  }

  /**
   * 周期运行
   *
   * @param sceneId
   * @param delay
   * @param task
   */
  public static void addDelayTask(int sceneId, long delay, Runnable task) {
    int index = modeIndex(sceneId);
    SCENE_SERVICE[index].schedule(task, delay, TimeUnit.MILLISECONDS);
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

  /**
   * 执行延期指令
   *
   * @param command
   * @param delay
   */
  public static void addDelayTask(Command command, long delay) {
    int index = command.modIndex(DEFAULT_THREAD_SIZE);
    SCENE_SERVICE[index].schedule(
        new Runnable() {
          @Override
          public void run() {
            command.doAction();
          }
        },
        delay,
        TimeUnit.MILLISECONDS);
  }

  public static void addScheduleTask(
      int sceneId, long delay, long period, long endTime, AbstractSceneCommand command) {
    int index = modeIndex(sceneId);
    ScheduledFuture scheduledFuture =
        SCENE_SERVICE[index].scheduleAtFixedRate(
            new Runnable() {
              @Override
              public void run() {
                if (System.currentTimeMillis() >= endTime) {
                  command.cancel();
                } else {
                  command.doAction();
                }
              }
            },
            delay,
            period,
            TimeUnit.MILLISECONDS);
    command.setFuture(scheduledFuture);
  }
  /** =======================================新接口==================================== */

  /**
   * 执行场景线程
   *
   * @param command
   */
  public void addTask(Command command) {
    int index = command.modIndex(DEFAULT_THREAD_SIZE);
    SCENE_SERVICE[index].submit(
        new Runnable() {
          @Override
          public void run() {
            if (!command.isCanceled()) {
              command.doAction();
            }
          }
        });
  }

  /**
   * 延时任务
   *
   * @param command
   */
  public void addDelayTask(AbstractSceneDelayCommand command) {
    ScheduledFuture scheduledFuture =
        SpringContext.getScheduleService()
            .scheduleWithFixedDelay(() -> addTask(command), 0, command.getDelay());
    command.setFuture(scheduledFuture);
  }

  /**
   * 新增周期任务
   *
   * @param command
   */
  public void addScheduleTask(AbstractSceneRateCommand command) {
    ScheduledFuture scheduledFuture =
        SpringContext.getScheduleService()
            .scheduleAtFixedRate(() -> addTask(command), command.getDelay(), command.getPeriod());
    command.setFuture(scheduledFuture);
  }
}
