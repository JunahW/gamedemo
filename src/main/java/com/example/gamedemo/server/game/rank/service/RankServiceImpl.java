package com.example.gamedemo.server.game.rank.service;

import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.rank.constant.RankTypeEnum;
import com.example.gamedemo.server.game.rank.event.ChangeBattleScoreEvent;
import com.example.gamedemo.server.game.rank.handler.AbstractRankHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wengj
 * @description
 * @date 2019/7/22
 */
@Service
public class RankServiceImpl implements RankService {

  @Override
  public void showRank(Player player, int index) {
    RankTypeEnum rankType = RankTypeEnum.getRankTypeEnumByIndex(index);
    AbstractRankHandler rankHandler = AbstractRankHandler.getRankHandler(rankType);
    CopyOnWriteArrayList rankInfos = rankHandler.getRankInfos();
    SessionManager.sendMessage(player, rankInfos);
  }

  @Override
  public void handleChangeBattleScoreEvent(ChangeBattleScoreEvent event) {
    AbstractRankHandler rankHandler = AbstractRankHandler.getRankHandler(RankTypeEnum.BattleScore);
    rankHandler.updateRank(event.getRankInfo());
  }

  @Override
  public void initRanks() {
    RankTypeEnum[] rankTypes = RankTypeEnum.values();
    for (RankTypeEnum rankType : rankTypes) {
      AbstractRankHandler rankHandler = AbstractRankHandler.getRankHandler(rankType);
      rankHandler.initRank();
    }
  }
}
