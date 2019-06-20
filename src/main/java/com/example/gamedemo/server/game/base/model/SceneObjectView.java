package com.example.gamedemo.server.game.base.model;

import com.example.gamedemo.server.game.base.gameobject.SceneObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：场景对象视野
 * @date 2019/6/20
 */
public class SceneObjectView {

  /** 场景对象的视野 */
  private Map<Long, SceneObject> sceneObjectMap = new HashMap<>();

  public Map<Long, SceneObject> getSceneObjectMap() {
    return sceneObjectMap;
  }

  public void setSceneObjectMap(Map<Long, SceneObject> sceneObjectMap) {
    this.sceneObjectMap = sceneObjectMap;
  }

  /**
   * 新增视野对象
   *
   * @param sceneObject
   */
  public void putSceneObject(SceneObject sceneObject) {
    sceneObjectMap.put(sceneObject.getId(), sceneObject);
  }

  /**
   * 移除视野对象
   *
   * @param id
   */
  public void removeSceneObject(Long id) {
    sceneObjectMap.remove(id);
  }
}
