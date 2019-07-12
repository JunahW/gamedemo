package com.example.gamedemo.server.game.monster.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description:怪物死亡事件
 * @date 2019/6/21
 */
public class MonsterDeadEvent implements Event {
  /** 归属者 */
  private CreatureObject attacker;

  /** 场景 */
  private Scene scene;

  /** 怪物id */
  private long monsterId;

  /** 怪物资源id */
  private int monsterResourceId;

  /**
   * @param scene
   * @param monsterId
   * @return
   */
  public static MonsterDeadEvent valueOf(
      CreatureObject attacker, Scene scene, long monsterId, int monsterResourceId) {
    MonsterDeadEvent monsterDeadEvent = new MonsterDeadEvent();
    monsterDeadEvent.setAttacker(attacker);
    monsterDeadEvent.setScene(scene);
    monsterDeadEvent.setMonsterId(monsterId);
    monsterDeadEvent.setMonsterResourceId(monsterResourceId);
    return monsterDeadEvent;
  }

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
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
    return attacker.getId();
  }
}
