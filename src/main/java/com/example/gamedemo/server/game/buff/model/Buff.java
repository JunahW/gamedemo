package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

/**
 * @author wengj
 * @description：buff模型
 * @date 2019/6/25
 */
public abstract class Buff {
  /** buffId; */
  private int buffId;

  /** 最近触发时间 */
  private long lastTriggerTime;

  public int getBuffId() {
    return buffId;
  }

  public void setBuffId(int buffId) {
    this.buffId = buffId;
  }

  public long getLastTriggerTime() {
    return lastTriggerTime;
  }

  public void setLastTriggerTime(long lastTriggerTime) {
    this.lastTriggerTime = lastTriggerTime;
  }

  /**
   * 执行buff效果
   *
   * @param owner
   */
  public abstract void active(CreatureObject owner);

  /**
   * 是否达到触发时间
   *
   * @param period
   * @return
   */
  public boolean canTrigger(int period) {
    if (period == 0) {
      return false;
    }
    long nextTime = getLastTriggerTime() + period;
    if (System.currentTimeMillis() >= nextTime) {
      return true;
    }
    return false;
  }
}
