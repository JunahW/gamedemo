package com.example.gamedemo.server.game.monster.model;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.bag.constant.ItemType;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;

/**
 * @author wengj
 * @description：掉落物
 * @date 2019/6/21
 */
public class DropObject extends SceneObject {
  private AbstractItem item;

  /**
   * @param itemId
   * @return
   */
  public static DropObject valueOf(int itemId, int quantity) {
    DropObject dropObject = new DropObject();
    ItemResource itemResource =
        SpringContext.getItemService().getItemResourceByItemResourceId(itemId);
    AbstractItem item = ItemType.create(itemResource.getItemType());
    item.setId(UniqueIdUtils.nextId());
    item.setQuantity(quantity);
    dropObject.setId(UniqueIdUtils.nextId());
    return dropObject;
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.DROP_OBJECT;
  }

  public AbstractItem getItem() {
    return item;
  }

  public void setItem(AbstractItem item) {
    this.item = item;
  }
}
