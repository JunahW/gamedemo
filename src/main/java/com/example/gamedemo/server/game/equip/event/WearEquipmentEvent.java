package com.example.gamedemo.server.game.equip.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author wengj
 * @description：穿装备事件
 * @date 2019/7/23
 */
public class WearEquipmentEvent implements Event {
  /** 玩家 */
  private Player player;

  /** 装备 */
  private EquipItem equipItem;

  /**
   * @param player
   * @param equipItem
   * @return
   */
  public static WearEquipmentEvent valueOf(Player player, EquipItem equipItem) {
    WearEquipmentEvent wearEquipmentEvent = new WearEquipmentEvent();
    wearEquipmentEvent.setPlayer(player);
    wearEquipmentEvent.setEquipItem(equipItem);
    return wearEquipmentEvent;
  }

  @Override
  public Object getOwnerId() {
    return player.getId();
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public EquipItem getEquipItem() {
    return equipItem;
  }

  public void setEquipItem(EquipItem equipItem) {
    this.equipItem = equipItem;
  }
}
