package com.example.gamedemo.common.executer.account;

import com.example.gamedemo.common.executer.Command;

/**
 * @author wengj
 * @description：账号任务执行服务接口
 * @date 2019/7/8
 */
public interface IAccountExecutorService {

  /**
   * 执行用户线程和场景线程
   *
   * @param accountId
   * @param task
   */
  void addTask(String accountId, Runnable task);
  /**
   * 执行command
   *
   * @param command
   */
  void submit(Command command);
}
