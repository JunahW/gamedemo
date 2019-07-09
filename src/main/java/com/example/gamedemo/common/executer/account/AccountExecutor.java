package com.example.gamedemo.common.executer.account;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.executer.account.impl.AbstractAccountDelayCommand;
import com.example.gamedemo.common.executer.account.impl.AbstractAccountRateCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author wengj
 * @description：用户线程执行器
 * @date 2019/6/12
 */
@Component
public class AccountExecutor {
  private static final Logger logger = LoggerFactory.getLogger(AccountExecutor.class);
  /** 默认线程数量 */
  private static final int DEFAULT_THREAD_SIZE = Runtime.getRuntime().availableProcessors();

  /** 线程池数量，可以控制每个账户在同一个线程执行 */
  private static final ThreadPoolExecutor[] ACCOUNT_SERVICE =
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
  private static int modeIndex(String accountId) {
    return accountId.hashCode() % DEFAULT_THREAD_SIZE;
  }

  /**
   * 执行任务
   *
   * @param accountId
   * @param task
   */
  public void addTask(String accountId, Runnable task) {
    int index = modeIndex(accountId);
    ACCOUNT_SERVICE[index].submit(task);
  }

  /**
   * 执行任务
   *
   * @param command
   */
  public void addTask(Command command) {
    int index = command.modIndex(DEFAULT_THREAD_SIZE);
    ACCOUNT_SERVICE[index].submit(
        new Runnable() {
          @Override
          public void run() {
            command.doAction();
          }
        });
  }

  /**
   * 延时任务
   *
   * @param command
   */
  public void addDelayTask(AbstractAccountDelayCommand command) {
    ScheduledFuture scheduledFuture =
        SpringContext.getScheduleService()
            .scheduleDelay(() -> addTask(command), command.getDelay());
    command.setFuture(scheduledFuture);
  }

  /**
   * 新增周期任务
   *
   * @param command
   */
  public void addScheduleTask(AbstractAccountRateCommand command) {
    ScheduledFuture scheduledFuture =
        SpringContext.getScheduleService()
            .scheduleAtFixedRate(() -> addTask(command), command.getDelay(), command.getPeriod());
    command.setFuture(scheduledFuture);
  }
}
