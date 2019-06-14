package com.example.gamedemo.server.game.base.gameobject;

import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wengj
 * @description：场景对象
 * @date 2019/6/14
 */
public abstract class SceneObject {

  /** TODO 场景对象视觉？ */
  ConcurrentHashMap<Long, SceneObject> sceneObjectMap = new ConcurrentHashMap<>();
  /** 场景对象id，唯一 */
  private Long id;
  /** 场景中的x坐标 */
  private int x;
  /** 场景中的y坐标 */
  private int y;

  public ConcurrentHashMap<Long, SceneObject> getSceneObjectMap() {
    return sceneObjectMap;
  }

  public void setSceneObjectMap(ConcurrentHashMap<Long, SceneObject> sceneObjectMap) {
    this.sceneObjectMap = sceneObjectMap;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
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
  public void removeSceneObject(long id) {
    sceneObjectMap.remove(id);
  }

  /**
   * 获取当前场景对象的类型
   *
   * @return
   */
  public abstract SceneObjectTypeEnum getSceneObjectType();

  @Override
  public String toString() {
    return "SceneObject{"
        + "sceneObjectMap="
        + sceneObjectMap
        + ", id="
        + id
        + ", x="
        + x
        + ", y="
        + y
        + '}';
  }
}
