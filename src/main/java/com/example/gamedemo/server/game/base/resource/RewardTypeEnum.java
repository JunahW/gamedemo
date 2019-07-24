package com.example.gamedemo.server.game.base.resource;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.base.resource.reward.ItemReward;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/7/17
 * @description: 奖励类型
 */
public enum RewardTypeEnum {
  /** 道具奖励 */
  ITEM_REWARD(ItemReward.class) {
    @Override
    public boolean reward(Player player, String value) {
      ItemReward itemReward = new ItemReward();
      itemReward.doParse(value);
      AbstractItem item = itemReward.getItem();
      player.getPack().addStorageItem(item);
      return true;
    }

    @Override
    public int needPackSize(String value) {
      ItemReward itemReward = new ItemReward();
      itemReward.doParse(value);
      AbstractItem item = itemReward.getItem();
      ItemResource resource =
          SpringContext.getItemService().getItemResourceByItemResourceId(item.getItemResourceId());
      int overLimit = resource.getOverLimit();
      int quantity = item.getQuantity();
      int size = 0;
      while (true) {
        size++;
        quantity = quantity - overLimit;
        if (quantity <= 0) {
          break;
        }
      }
      return size;
    }
  },

  /** 经验奖励 */
  EXP_REWARD(null);

  private Class rewardClazz;

  RewardTypeEnum(Class rewardClazz) {
    this.rewardClazz = rewardClazz;
  }

  /**
   * 奖励
   *
   * @param player
   * @param value
   * @return
   */
  public boolean reward(Player player, String value) {
    return true;
  }

  /**
   * 需要的背包格子数
   *
   * @param value
   * @return
   */
  public int needPackSize(String value) {
    return 0;
  }
}
