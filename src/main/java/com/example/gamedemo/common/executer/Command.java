package com.example.gamedemo.common.executer;

/**
 * @author: wengj
 * @date: 2019/7/1
 * @description：指令
 */
public interface Command {
  /** 执行指令 */
  void doAction();

  /**
   * 取余，指定线程执行
   *
   * @param poolSize
   * @return
   */
  int modIndex(int poolSize);

  /** 取消command */
  void cancel();

  /**
   * 指令是否已经取消
   *
   * @return
   */
  boolean isCanceled();
}
