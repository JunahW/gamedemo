package com.example.gamedemo.common.executer.account;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description：用户线程执行器
 * @date 2019/6/12
 */
public class AccountExecutor {
  private static final Logger logger = LoggerFactory.getLogger(AccountExecutor.class);
  /** 默认线程数量 */
  private static final int DEFAULT_THREAD_SIZE = Runtime.getRuntime().availableProcessors();

  /** 线程池数量，可以控制每个账户在同一个线程执行 */
  public static final ThreadPoolExecutor[] ACCOUNT_SERVICE =
      new ThreadPoolExecutor[DEFAULT_THREAD_SIZE];

  static {
    logger.info("开始用户线程。。。");
    ThreadFactory singleThreadFactory =
        new ThreadFactoryBuilder()
            .setNameFormat("Account-Thread-%d")
            .setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
            .build();
    for (int i = 0; i < DEFAULT_THREAD_SIZE; i++) {
      ACCOUNT_SERVICE[i] =
          new ThreadPoolExecutor(
              1,
              1,
              0L,
              TimeUnit.MILLISECONDS,
              new LinkedBlockingDeque<Runnable>(),
              singleThreadFactory,
              new ThreadPoolExecutor.DiscardPolicy());
    }
    logger.info("初始化用户线程完成");
  }

  /**
   * 获取下标
   *
   * @param accountId
   * @return
   */
  public static int modeIndex(String accountId) {
    return accountId.hashCode() % DEFAULT_THREAD_SIZE;
  }
}
