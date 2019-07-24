package com.example.gamedemo.server.game.mapinfo.model;

import com.example.gamedemo.server.game.base.model.AbstractMapInfo;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/23
 */
public class PlayerMapInfo {
  private Map<SceneTypeEnum, AbstractMapInfo> infoMap = new HashMap<>();

  public Map<SceneTypeEnum, AbstractMapInfo> getInfoMap() {
    return infoMap;
  }

  public void setInfoMap(Map<SceneTypeEnum, AbstractMapInfo> infoMap) {
    this.infoMap = infoMap;
  }

  /**
   * 新增
   *
   * @param sceneType
   * @param mapInfo
   */
  public void putInfoMap(SceneTypeEnum sceneType, AbstractMapInfo mapInfo) {
    infoMap.put(sceneType, mapInfo);
  }

  /**
   * 移除
   *
   * @param sceneTypeEnum
   */
  public void removeInfoMapEntryBySceneTypeEnum(SceneTypeEnum sceneTypeEnum) {
    infoMap.remove(sceneTypeEnum);
  }

  /**
   * 获取 空则创建
   *
   * @param sceneTypeEnum
   * @return
   */
  public AbstractMapInfo getOrCreateMapInfo(SceneTypeEnum sceneTypeEnum) {
    AbstractMapInfo mapInfo = infoMap.get(sceneTypeEnum);
    if (mapInfo == null) {
      infoMap.putIfAbsent(sceneTypeEnum, sceneTypeEnum.initAndCreateMapInfo());
    }
    return infoMap.get(sceneTypeEnum);
  }
}
