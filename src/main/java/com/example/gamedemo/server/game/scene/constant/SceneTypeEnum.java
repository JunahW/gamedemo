package com.example.gamedemo.server.game.scene.constant;

import com.example.gamedemo.server.game.base.model.AbstractMapInfo;
import com.example.gamedemo.server.game.dungeon.model.DungeonMapInfo;

/**
 * @author: wengj
 * @date: 2019/7/10
 * @description: 场景类型
 */
public enum SceneTypeEnum {
  /** 普通 */
  COMMON_SCENE(0, null),
  /** 副本场景 */
  DUNGEON_SCENE(1, new DungeonMapInfo());

  /** 场景类型 */
  private int sceneType;

  private AbstractMapInfo mapInfo;

  SceneTypeEnum(int sceneType, AbstractMapInfo mapInfo) {
    this.sceneType = sceneType;
    this.mapInfo = mapInfo;
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

  public AbstractMapInfo initAndCreateMapInfo() {
    if (mapInfo != null) {
      return mapInfo.valueOf();
    }
    return null;
  }

  public int getSceneType() {
    return sceneType;
  }

  public void setSceneType(int sceneType) {
    this.sceneType = sceneType;
  }

  public AbstractMapInfo getMapInfo() {
    return mapInfo;
  }

  public void setMapInfo(AbstractMapInfo mapInfo) {
    this.mapInfo = mapInfo;
  }
}
