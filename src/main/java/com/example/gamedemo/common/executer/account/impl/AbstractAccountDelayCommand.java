package com.example.gamedemo.common.executer.account.impl;

/**
 * @author wengj
 * @description：延时账户任务
 * @date 2019/7/8
 */
public abstract class AbstractAccountDelayCommand extends AbstractAccountCommand {
  /** 延时 */
  private long delay;

  public AbstractAccountDelayCommand(String accountId, long delay) {
    super(accountId);
    this.delay = delay;
  }

  public long getDelay() {
    return delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }
}
