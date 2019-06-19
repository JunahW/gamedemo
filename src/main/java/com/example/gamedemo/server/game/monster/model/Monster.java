package com.example.gamedemo.server.game.monster.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;
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

  /**
   * @param monsterResourceId
   * @return
   */
  public static Monster valueOf(Integer monsterResourceId) {
    Monster monster = new Monster();
    monster.setId(UniqueIdUtils.nextId());
    monster.setMonsterResourceId(monsterResourceId);
    // TODO 设置坐标
    return monster;
  }

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
