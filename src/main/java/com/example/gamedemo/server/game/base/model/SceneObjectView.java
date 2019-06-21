package com.example.gamedemo.server.game.base.model;

import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.base.vo.SceneObjectVo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：场景对象视野
 * @date 2019/6/20
 */
public class SceneObjectView {

  /** 场景对象的视野 */
  private Map<Long, SceneObjectVo> sceneObjectMap = new HashMap<>();

  public Map<Long, SceneObjectVo> getSceneObjectMap() {
    return sceneObjectMap;
  }

  public void setSceneObjectMap(Map<Long, SceneObjectVo> sceneObjectMap) {
    this.sceneObjectMap = sceneObjectMap;
  }

  /**
   * 新增视野对象
   *
   * @param sceneObject
   */
  public void putSceneObject(SceneObject sceneObject) {
    sceneObjectMap.put(
        sceneObject.getId(),
        SceneObjectVo.valueOf(
            sceneObject.getX(), sceneObject.getY(), sceneObject.getSceneObjectType()));
  }

  /**
   * 移除视野对象
   *
   * @param id
   */
  public void removeSceneObject(Long id) {
    sceneObjectMap.remove(id);
  }

  /** 清除视野 */
  public void clearSceneObjectView() {
    sceneObjectMap.clear();
  }
}
