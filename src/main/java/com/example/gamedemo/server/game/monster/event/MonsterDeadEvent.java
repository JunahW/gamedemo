package com.example.gamedemo.server.game.monster.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;

/**
 * @author wengj
 * @description:怪物死亡事件
 * @date 2019/6/21
 */
public class MonsterDeadEvent implements Event {
  /** 归属者 */
  private CreatureObject attacker;

  /** 场景id */
  private int sceneId;

  /** 怪物id */
  private long monsterId;

  /** 怪物资源id */
  private int monsterResourceId;

  /**
   * @param sceneId
   * @param monsterId
   * @return
   */
  public static MonsterDeadEvent valueOf(
      CreatureObject attacker, int sceneId, long monsterId, int monsterResourceId) {
    MonsterDeadEvent monsterDeadEvent = new MonsterDeadEvent();
    monsterDeadEvent.setAttacker(attacker);
    monsterDeadEvent.setSceneId(sceneId);
    monsterDeadEvent.setMonsterId(monsterId);
    monsterDeadEvent.setMonsterResourceId(monsterResourceId);
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

  public CreatureObject getAttacker() {
    return attacker;
  }

  public void setAttacker(CreatureObject attacker) {
    this.attacker = attacker;
  }

  public int getMonsterResourceId() {
    return monsterResourceId;
  }

  public void setMonsterResourceId(int monsterResourceId) {
    this.monsterResourceId = monsterResourceId;
  }

  @Override
  public Object getOwnerId() {
    return sceneId;
  }
}
