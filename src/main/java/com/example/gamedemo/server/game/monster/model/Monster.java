package com.example.gamedemo.server.game.monster.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.attribute.MonsterAttributeContainer;
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

  /** 血量 */
  private int hp;

  /** 魔法值 */
  private int mp;

  /** 怪物属性容器 */
  private MonsterAttributeContainer monsterAttributeContainer;

  /**
   * @param monsterResourceId
   * @return
   */
  public static Monster valueOf(Integer monsterResourceId) {
    Monster monster = new Monster();
    monster.setId(UniqueIdUtils.nextId());
    monster.setMonsterResourceId(monsterResourceId);
    /*MonsterResource resource =
            SpringContext.getMonsterService().getMonsterResourceById(monsterResourceId);
    */
    // TODO 设置坐标
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

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getMp() {
    return mp;
  }

  public void setMp(int mp) {
    this.mp = mp;
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.MONSTER;
  }
}
