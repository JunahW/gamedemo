package com.example.gamedemo.server.game.base.vo;

import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;

/**
 * @author wengj
 * @description：场景对象视野视图
 * @date 2019/6/20
 */
public class SceneObjectVo {

  /** 主键 */
  private long id;
  /** 场景中的x坐标 */
  private int x;
  /** 场景中的y坐标 */
  private int y;

  private SceneObjectTypeEnum sceneObjectTypeEnum;

  /**
   * @param x
   * @param y
   * @param type
   * @return
   */
  public static SceneObjectVo valueOf(long id, int x, int y, SceneObjectTypeEnum type) {
    SceneObjectVo sceneObjectVo = new SceneObjectVo();
    sceneObjectVo.setId(id);
    sceneObjectVo.setX(x);
    sceneObjectVo.setY(y);
    sceneObjectVo.setSceneObjectTypeEnum(type);
    return sceneObjectVo;
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public SceneObjectTypeEnum getSceneObjectTypeEnum() {
    return sceneObjectTypeEnum;
  }

  public void setSceneObjectTypeEnum(SceneObjectTypeEnum sceneObjectTypeEnum) {
    this.sceneObjectTypeEnum = sceneObjectTypeEnum;
  }
}
