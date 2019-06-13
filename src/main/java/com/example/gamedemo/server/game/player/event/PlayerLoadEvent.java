package com.example.gamedemo.server.game.player.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author wengj
 * @description
 * @date 2019/6/11
 */
public class PlayerLoadEvent implements Event {
  private Player player;

  public PlayerLoadEvent() {}

  public PlayerLoadEvent(Player player) {
    this.player = player;
  }

  /**
   * 获取事件对象
   *
   * @param player
   * @return
   */
  public static PlayerLoadEvent valueof(Player player) {
    return new PlayerLoadEvent(player);
  }

  @Override
  public Object getOwnerId() {
    return player.getPlayerId();
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  @Override
  public String toString() {
    return "PlayerLoadEvent{" + "player=" + player + '}';
  }
}
