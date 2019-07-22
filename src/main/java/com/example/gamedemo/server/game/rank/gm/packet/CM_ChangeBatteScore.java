package com.example.gamedemo.server.game.rank.gm.packet;

/**
 * @author wengj
 * @description
 * @date 2019/7/22
 */
public class CM_ChangeBatteScore {
  /** 玩家id */
  private Long playerId;

  /** 战力指数 */
  private long battleScore;

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public long getBattleScore() {
    return battleScore;
  }

  public void setBattleScore(long battleScore) {
    this.battleScore = battleScore;
  }
}
