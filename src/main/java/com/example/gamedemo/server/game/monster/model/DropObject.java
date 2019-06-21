package com.example.gamedemo.server.game.monster.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;

/**
 * @author wengj
 * @description：掉落物
 * @date 2019/6/21
 */
public class DropObject extends SceneObject {
  /** 掉落道具的模板Id */
  private int itemId;

  /**
   * @param itemId
   * @return
   */
  public static DropObject valueOf(int itemId) {
    DropObject dropObject = new DropObject();
    dropObject.setId(UniqueIdUtils.nextId());
    dropObject.setItemId(itemId);
    return dropObject;
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.DROP_OBJECT;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }
}
