package com.example.gamedemo.common.executer.account.impl;

/**
 * @author wengj
 * @description
 * @date 2019/7/8
 */
public abstract class AbstractAccountRateCommand extends AbstractAccountCommand {

  /** 延时 */
  private long delay = 0L;
  /** 周期 */
  private long period;

  public AbstractAccountRateCommand(String accountId, long period) {
    super(accountId);
    this.period = period;
  }

  public AbstractAccountRateCommand(String accountId, long delay, long period) {
    super(accountId);
    this.delay = delay;
    this.period = period;
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }

  public long getDelay() {
    return delay;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }
}
