package com.example.gamedemo.server.game.base.utils;

import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.base.resource.RewardTypeEnum;
import com.example.gamedemo.server.game.base.resource.bean.RewardDef;
import com.example.gamedemo.server.game.player.model.Player;

import java.util.List;

/**
 * @author wengj
 * @description：发放奖励工具类
 * @date 2019/7/17
 */
public class RewardUtils {

  /**
   * 奖励
   *
   * @param player
   * @param rewardDefs
   * @return
   */
  public static boolean reward(Player player, List<RewardDef> rewardDefs) {
    for (RewardDef rewardDef : rewardDefs) {
      RewardTypeEnum type = rewardDef.getType();
      type.reward(player, rewardDef.getValue());
    }
    return true;
  }

  /**
   * 判断背包是否满足
   *
   * @param player
   * @param rewardDefs
   * @return
   */
  public static boolean isEnoughPackSize(Player player, List<RewardDef> rewardDefs) {
    ItemStorage pack = player.getPack();
    int needTotalSize = 0;
    for (RewardDef rewardDef : rewardDefs) {
      RewardTypeEnum type = rewardDef.getType();
      needTotalSize += type.needPackSize(rewardDef.getValue());
    }
    int packEmptySize = pack.getPackEmptySize();
    if (packEmptySize >= needTotalSize) {
      return true;
    }
    return false;
  }
}
