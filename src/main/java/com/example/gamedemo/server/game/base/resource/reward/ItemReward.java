package com.example.gamedemo.server.game.base.resource.reward;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.bag.constant.ItemType;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.base.resource.AbstractReward;

/**
 * @author wengj
 * @description
 * @date 2019/7/17
 */
public class ItemReward extends AbstractReward {
  private AbstractItem item;

  @Override
  public void doParse(String value) {
    String[] splits = value.split(GameConstant.SPLIT_TOKEN_COLON);
    int quantity = Integer.parseInt(splits[0]);
    int itemResourceId = Integer.parseInt(splits[1]);
    ItemResource itemResource =
        SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);
    AbstractItem item = ItemType.create(itemResource.getItemType());
    item.setQuantity(quantity);
    item.setItemResourceId(itemResourceId);
    this.item = item;
  }

  public AbstractItem getItem() {
    return item;
  }

  public void setItem(AbstractItem item) {
    this.item = item;
  }
}
