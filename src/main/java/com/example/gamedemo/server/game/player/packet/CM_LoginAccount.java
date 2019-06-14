package com.example.gamedemo.server.game.player.packet;

/**
 * @author wengj
 * @description:登陆消息
 * @date 2019/5/27
 */
public class CM_LoginAccount {
  /** 角色id */
  private Long playerId;

  public Long getPlayerId() {
    return playerId;
  }
}
