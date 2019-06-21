package com.example.gamedemo.server.game.monster.event;

import com.example.gamedemo.common.event.Event;

/**
 * @author wengj
 * @description:怪物死亡事件
 * @date 2019/6/21
 */
public class MonsterDeadEvent implements Event {

  /** 场景id */
  private int sceneId;

  /** 怪物id */
  private long monsterId;

  /**
   * @param sceneId
   * @param monsterId
   * @return
   */
  public static MonsterDeadEvent valueOf(int sceneId, long monsterId) {
    MonsterDeadEvent monsterDeadEvent = new MonsterDeadEvent();
    monsterDeadEvent.setSceneId(sceneId);
    monsterDeadEvent.setMonsterId(monsterId);
    return monsterDeadEvent;
  }

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

  public long getMonsterId() {
    return monsterId;
  }

  public void setMonsterId(long monsterId) {
    this.monsterId = monsterId;
  }

  @Override
  public Object getOwnerId() {
    return sceneId;
  }
}
