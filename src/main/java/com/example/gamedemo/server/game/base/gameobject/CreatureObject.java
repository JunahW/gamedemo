package com.example.gamedemo.server.game.base.gameobject;

import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.buff.model.BuffContainer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author wengj
 * @description：有生命的对象
 * @date 2019/6/21
 */
public abstract class CreatureObject<T extends CreatureObject> extends SceneObject {
  /** 视野 */
  @JsonIgnore private SceneObjectView sceneObjectView = new SceneObjectView();

  /** 血量 */
  private long hp;

  /** 魔法值 */
  private long mp;

  /** Buff容器 */
  private BuffContainer<T> buffContainer = new BuffContainer<>();

  public SceneObjectView getSceneObjectView() {
    return sceneObjectView;
  }

  public void setSceneObjectView(SceneObjectView sceneObjectView) {
    this.sceneObjectView = sceneObjectView;
  }

  public long getHp() {
    return hp;
  }

  public void setHp(long hp) {
    this.hp = hp;
  }

  public long getMp() {
    return mp;
  }

  public void setMp(long mp) {
    this.mp = mp;
  }

  public BuffContainer<T> getBuffContainer() {
    return buffContainer;
  }

  public void setBuffContainer(BuffContainer<T> buffContainer) {
    this.buffContainer = buffContainer;
  }
}
