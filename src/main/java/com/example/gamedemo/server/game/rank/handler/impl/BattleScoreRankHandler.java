package com.example.gamedemo.server.game.rank.handler.impl;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.rank.constant.RankTypeEnum;
import com.example.gamedemo.server.game.rank.handler.AbstractRankHandler;
import com.example.gamedemo.server.game.rank.model.BattleScoreRankInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wengj
 * @description
 * @date 2019/7/19
 */
@Component
public class BattleScoreRankHandler extends AbstractRankHandler<BattleScoreRankInfo> {
  @Override
  public RankTypeEnum getRankType() {
    return RankTypeEnum.BattleScore;
  }

  @Override
  public void initRank() {
    List<Player> playerList = SpringContext.getPlayerService().getPlayerList();
    CopyOnWriteArrayList<BattleScoreRankInfo> rankInfos = new CopyOnWriteArrayList<>();
    long currentTime = System.currentTimeMillis();
    for (Player player : playerList) {
      BattleScoreRankInfo rankInfo = new BattleScoreRankInfo();
      rankInfo.setPlayerId(player.getId());
      rankInfo.setBattleScore(player.getCombatIndex());
      rankInfo.setRefreshTime(currentTime);
      rankInfos.add(rankInfo);
    }
    Collections.sort(rankInfos);
    rankInfos =
        (CopyOnWriteArrayList<BattleScoreRankInfo>)
            rankInfos.subList(0, GameConstant.BATTLE_SCORE_LENGTH);
    super.setRankInfos(rankInfos);
  }

  @Override
  public void updateRank(BattleScoreRankInfo rankInfo) {
    CopyOnWriteArrayList<BattleScoreRankInfo> rankInfos = super.getRankInfos();

    if (rankInfos.contains(rankInfo)) {
      rankInfos.remove(rankInfo);
      rankInfos.add(rankInfo);
    } else {
      if (rankInfos.size() < GameConstant.BATTLE_SCORE_LENGTH) {
        rankInfos.add(rankInfo);
      } else {
        BattleScoreRankInfo lastRankInfo = rankInfos.get(GameConstant.BATTLE_SCORE_LENGTH);
        if (lastRankInfo.getBattleScore() < rankInfo.getBattleScore()) {
          rankInfos.remove(lastRankInfo);
          rankInfos.add(rankInfo);
        } else {
          return;
        }
      }
    }
    Collections.sort(rankInfos);
  }
}
