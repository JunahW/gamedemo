package com.example.gamedemo.server.game.scene.constant;

/**
 * @author: wengj
 * @date: 2019/7/10
 * @description: 场景类型
 */
public enum SceneTypeEnum {
  /** 普通 */
  COMMON_SCENE(0),
  /** 副本场景 */
  DUNGEON_SCENE(1);

  /** 场景类型 */
  private int sceneType;

  SceneTypeEnum(int sceneType) {
    this.sceneType = sceneType;
  }

  /**
   * @param sceneType
   * @return
   */
  public static SceneTypeEnum getSceneTypeEnumBySceneType(int sceneType) {
    for (SceneTypeEnum sceneTypeEnum : SceneTypeEnum.values()) {
      if (sceneTypeEnum.getSceneType() == sceneType) {
        return sceneTypeEnum;
      }
    }
    return null;
  }

  public int getSceneType() {
    return sceneType;
  }

  public void setSceneType(int sceneType) {
    this.sceneType = sceneType;
  }
}
