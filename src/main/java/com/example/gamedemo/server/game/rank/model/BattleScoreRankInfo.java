package com.example.gamedemo.server.game.rank.model;

import java.util.Objects;

/**
 * @author wengj
 * @description
 * @date 2019/7/19
 */
public class BattleScoreRankInfo implements Comparable<BattleScoreRankInfo> {
  /** 玩家id */
  private Long playerId;

  /** 战力数值 */
  private long battleScore;

  /** 更新时间 */
  private long refreshTime;

  /**
   * @param playerId
   * @param battleScore
   * @return
   */
  public static BattleScoreRankInfo valueOf(Long playerId, long battleScore) {
    BattleScoreRankInfo rankInfo = new BattleScoreRankInfo();
    rankInfo.setPlayerId(playerId);
    rankInfo.setBattleScore(battleScore);
    rankInfo.setRefreshTime(System.currentTimeMillis());

    return rankInfo;
  }

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

  public long getRefreshTime() {
    return refreshTime;
  }

  public void setRefreshTime(long refreshTime) {
    this.refreshTime = refreshTime;
  }

  @Override
  public int compareTo(BattleScoreRankInfo battleScoreRankInfo) {
    if (this.getBattleScore() > battleScoreRankInfo.getBattleScore()) {
      return -1;
    } else if (this.getBattleScore() == battleScoreRankInfo.getBattleScore()) {
      if (this.getBattleScore() < battleScoreRankInfo.getBattleScore()) {
        return -1;
      } else {
        return 0;
      }
    }
    return 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BattleScoreRankInfo that = (BattleScoreRankInfo) o;
    return Objects.equals(playerId, that.playerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerId);
  }
}
