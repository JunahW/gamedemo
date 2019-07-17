package com.example.gamedemo.server.game.monster.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;

/**
 * @author wengj
 * @description:怪物死亡事件
 * @date 2019/6/21
 */
public class MonsterDeadEvent implements Event {
  /** 归属者 */
  private Player attacker;

  /** 场景 */
  private Scene scene;

  /** 怪物 */
  private Monster monster;

  /**
   * @param attacker
   * @param scene
   * @param monster
   * @return
   */
  public static MonsterDeadEvent valueOf(Player attacker, Scene scene, Monster monster) {
    MonsterDeadEvent monsterDeadEvent = new MonsterDeadEvent();
    monsterDeadEvent.setAttacker(attacker);
    monsterDeadEvent.setScene(scene);
    monsterDeadEvent.setMonster(monster);
    return monsterDeadEvent;
  }

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  public Player getAttacker() {
    return attacker;
  }

  public void setAttacker(Player attacker) {
    this.attacker = attacker;
  }

  public Monster getMonster() {
    return monster;
  }

  public void setMonster(Monster monster) {
    this.monster = monster;
  }

  @Override
  public Object getOwnerId() {
    return attacker.getId();
  }
}
