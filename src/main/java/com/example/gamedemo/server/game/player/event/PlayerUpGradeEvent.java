package com.example.gamedemo.server.game.player.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author wengj
 * @description：玩家升级事件
 * @date 2019/7/22
 */
public class PlayerUpGradeEvent implements Event<Long> {

  private Player player;

  /** 等级 */
  private int level;

  /**
   * @param player
   * @param level
   * @return
   */
  public static PlayerUpGradeEvent valueOf(Player player, int level) {
    PlayerUpGradeEvent event = new PlayerUpGradeEvent();
    event.setPlayer(player);
    event.setLevel(level);
    return event;
  }

  @Override
  public Long getOwnerId() {
    return player.getId();
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
