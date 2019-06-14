package com.example.gamedemo.server.game.monster.model;

import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;

/**
 * @author wengj
 * @description：怪物模型
 * @date 2019/6/13
 */
public class Monster extends SceneObject {
  /** 怪物资源id */
  private int monsterResourceId;

  public int getMonsterResourceId() {
    return monsterResourceId;
  }

  public void setMonsterResourceId(int monsterResourceId) {
    this.monsterResourceId = monsterResourceId;
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.MONSTER;
  }
}
