package com.example.gamedemo.server.game.base.gameobject;

/**
 * @author wengj
 * @description:对象的唯一id，根据UniqueIdUtils生成
 * @date 2019/5/29
 */
public abstract class GameObject {
  private long objectId;

  public long getObjectId() {
    return objectId;
  }

  public void setObjectId(long objectId) {
    this.objectId = objectId;
  }
}
