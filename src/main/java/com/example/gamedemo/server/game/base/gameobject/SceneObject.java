package com.example.gamedemo.server.game.base.gameobject;

import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;

/**
 * @author wengj
 * @description：场景对象
 * @date 2019/6/14
 */
public abstract class SceneObject extends GameObject {

  /** 场景中的x坐标 */
  private int x;
  /** 场景中的y坐标 */
  private int y;

  /** 场景id */
  private int sceneId;

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

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

  /**
   * 获取当前场景对象的类型
   *
   * @return
   */
  public abstract SceneObjectTypeEnum getSceneObjectType();

  @Override
  public String toString() {
    return "SceneObject{" + ", id=" + super.getId() + ", x=" + x + ", y=" + y + '}';
  }
}
