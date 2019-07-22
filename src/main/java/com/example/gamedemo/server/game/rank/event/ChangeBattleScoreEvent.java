package com.example.gamedemo.server.game.rank.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.rank.model.BattleScoreRankInfo;

/**
 * @author wengj
 * @description
 * @date 2019/7/22
 */
public class ChangeBattleScoreEvent implements Event<Long> {

  /** 排行信息 */
  private BattleScoreRankInfo rankInfo;

  /**
   * @param rankInfo
   * @return
   */
  public static ChangeBattleScoreEvent valueOf(BattleScoreRankInfo rankInfo) {
    ChangeBattleScoreEvent event = new ChangeBattleScoreEvent();
    event.setRankInfo(rankInfo);
    return event;
  }

  @Override
  public Long getOwnerId() {
    return rankInfo.getPlayerId();
  }

  public BattleScoreRankInfo getRankInfo() {
    return rankInfo;
  }

  public void setRankInfo(BattleScoreRankInfo rankInfo) {
    this.rankInfo = rankInfo;
  }
}
