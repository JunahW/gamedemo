package com.example.gamedemo.server.game.base.constant;

/**
 * @author: wengj
 * @date: 2019/6/14
 * @description: 场景对象类型
 */
public enum SceneObjectTypeEnum {
  /** 玩家 */
  PLAYER(1),

  /** 怪物 */
  MONSTER(2),

  /** 掉落物 */
  DROP_OBJECT(3);

  private int id;

  SceneObjectTypeEnum(int id) {
    this.id = id;
  }

  public static SceneObjectTypeEnum getEnumById(int id) {
    for (SceneObjectTypeEnum typeEnum : SceneObjectTypeEnum.values()) {
      if (typeEnum.getId() == id) {
        return typeEnum;
      }
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
