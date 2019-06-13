package com.example.gamedemo.common.executer;

import com.example.gamedemo.common.executer.account.AccountExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description：通用线程执行器
 * @date 2019/6/12
 */
public class CommonExecutor {
  private static final Logger logger = LoggerFactory.getLogger(AccountExecutor.class);
  /** 默认线程数量 */
  private static final int DEFAULT_THREAD_SIZE = Runtime.getRuntime().availableProcessors();

  /** 线程池数量，可以控制每个账户在同一个线程执行 */
  public static final ThreadPoolExecutor[] COMMON_SERVICE =
      new ThreadPoolExecutor[DEFAULT_THREAD_SIZE];

  static {
    logger.info("开始通用线程。。。");
    ThreadFactory singleThreadFactory =
        new ThreadFactoryBuilder()
            .setNameFormat("Common-Thread-%d")
            .setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
            .build();
    for (int i = 0; i < DEFAULT_THREAD_SIZE; i++) {
      COMMON_SERVICE[i] =
          new ThreadPoolExecutor(
              1,
              1,
              0L,
              TimeUnit.MILLISECONDS,
              new LinkedBlockingDeque<Runnable>(),
              singleThreadFactory,
              new ThreadPoolExecutor.DiscardPolicy());
    }
    logger.info("初始化通用线程完成");
  }

  /**
   * 获取下标
   *
   * @param id
   * @return
   */
  public static int modeIndex(String id) {
    return id.hashCode() % DEFAULT_THREAD_SIZE;
  }
}
