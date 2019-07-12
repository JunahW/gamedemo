package com.example.gamedemo.server.game.player.packet;

import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author wengj
 * @description
 * @date 2019/7/12
 */
public class SM_Player {
  private Player player;

  public static SM_Player valueOf(Player player) {
    SM_Player sm_player = new SM_Player();
    sm_player.setPlayer(player);
    return sm_player;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
