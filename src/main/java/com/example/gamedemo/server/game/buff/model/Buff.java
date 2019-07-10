package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description：buff模型
 * @date 2019/6/25
 */
public abstract class Buff {

  private static final Logger logger = LoggerFactory.getLogger(Buff.class);
  /** buffId; */
  private int buffId;

  /** 最近触发时间 */
  private long lastTriggerTime;

  /** 失效时间 */
  private long endTime;

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

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  /**
   * 执行buff效果
   *
   * @param owner
   */
  public abstract void active(CreatureObject owner);

  /**
   * 获得buff
   *
   * @param owner
   */
  public void gainBuff(CreatureObject owner) {
    logger.info("获取了buff");
  }

  /**
   * 失去buff
   *
   * @param owner
   */
  public void loseBuff(CreatureObject owner) {
    logger.info("失去了buff");
  }

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

  /**
   * buff是否到期
   *
   * @return
   */
  public boolean isEnd() {
    if (endTime <= System.currentTimeMillis()) {
      return true;
    }
    return false;
  }
}
