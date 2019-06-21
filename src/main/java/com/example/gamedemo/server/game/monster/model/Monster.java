package com.example.gamedemo.server.game.monster.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.attribute.MonsterAttributeContainer;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.BiologyObject;

/**
 * @author wengj
 * @description：怪物模型
 * @date 2019/6/13
 */
public class Monster extends BiologyObject {
  /** 怪物资源id */
  private int monsterResourceId;

  /** 怪物属性容器 */
  private MonsterAttributeContainer monsterAttributeContainer = new MonsterAttributeContainer();

  /** 怪物视野 */

  /**
   * @param monsterResourceId
   * @return
   */
  public static Monster valueOf(Integer monsterResourceId) {
    Monster monster = new Monster();
    monster.setId(UniqueIdUtils.nextId());
    monster.setMonsterResourceId(monsterResourceId);
    return monster;
  }

  public int getMonsterResourceId() {
    return monsterResourceId;
  }

  public void setMonsterResourceId(int monsterResourceId) {
    this.monsterResourceId = monsterResourceId;
  }

  public MonsterAttributeContainer getMonsterAttributeContainer() {
    return monsterAttributeContainer;
  }

  public void setMonsterAttributeContainer(MonsterAttributeContainer monsterAttributeContainer) {
    this.monsterAttributeContainer = monsterAttributeContainer;
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.MONSTER;
  }
}
