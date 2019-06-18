package com.example.gamedemo.server.game.player.packet;

/**
 * @author wengj
 * @description：创建玩家消息
 * @date 2019/5/27
 */
public class CM_CreatePlayer {

  /** 账户id */
  private Long playerId;

  /** 玩家类型 */
  private int jobId;

  public Long getPlayerId() {
    return playerId;
  }

  public int getJobId() {
    return jobId;
  }
}
