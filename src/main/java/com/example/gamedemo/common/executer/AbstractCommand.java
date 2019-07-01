package com.example.gamedemo.common.executer;

import java.util.concurrent.ScheduledFuture;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public abstract class AbstractCommand implements Command {

  /** 任务是否取消 */
  private boolean isCanceled = false;

  /** 定时任务句柄 */
  private ScheduledFuture future;

  public boolean isCanceled() {
    return isCanceled;
  }

  public void setCanceled(boolean canceled) {
    isCanceled = canceled;
  }

  public ScheduledFuture getFuture() {
    return future;
  }

  public void setFuture(ScheduledFuture future) {
    this.future = future;
  }

  @Override
  public void cancel() {
    if (future != null) {
      future.cancel(true);
    }
    isCanceled = true;
  }
}
