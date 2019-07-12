package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description：buff模型
 * @date 2019/6/25
 */
public abstract class AbstractBuff implements AttributeModelId {

  private static final Logger logger = LoggerFactory.getLogger(AbstractBuff.class);
  /** 已经合并的次数 */
  protected int haveMergeTime = 0;
  /** buff施加方 */
  private CreatureObject caster;
  /** buffId; */
  private int buffId;
  /** 最近触发时间 */
  private long lastTriggerTime;
  /** 失效时间 */
  private long endTime;

  /** 持续时间 */
  private long duration;

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

  public int getHaveMergeTime() {
    return haveMergeTime;
  }

  public void setHaveMergeTime(int haveMergeTime) {
    this.haveMergeTime = haveMergeTime;
  }

  public CreatureObject getCaster() {
    return caster;
  }

  public void setCaster(CreatureObject caster) {
    this.caster = caster;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
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

  /**
   * 合并buff可合并的buff类型需要重写该方法
   *
   * @param buff
   */
  public void merge(AbstractBuff buff) {
    throw new UnsupportedOperationException();
  }

  /**
   * 判断buff能否合并
   *
   * @return
   */
  public boolean canMerge() {
    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(buffId);
    if (buffResource.getMergeTime() == 0) {
      return false;
    }
    return true;
  }

  /**
   * 合并是否达到上限
   *
   * @return
   */
  public boolean isMergeFull() {
    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(buffId);
    return canMerge() && haveMergeTime + 1 >= buffResource.getMergeTime();
  }

  @Override
  public String getModelName() {
    return Integer.toString(buffId);
  }
}
