package com.example.gamedemo.server.game.base.resource;

import com.example.gamedemo.server.game.bag.model.AbstractItem;
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
}
