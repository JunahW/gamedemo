package com.example.gamedemo.server.game.rank.gm;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.rank.event.ChangeBattleScoreEvent;
import com.example.gamedemo.server.game.rank.gm.packet.CM_ChangeBatteScore;
import com.example.gamedemo.server.game.rank.model.BattleScoreRankInfo;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/7/22
 */
@Component
@HandlerClass
public class GmController {

  /**
   * 修改战斗指数
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "changeBattle")
  public void changeBattleScore(TSession session, CM_ChangeBatteScore req) {
    Player playerById = SpringContext.getPlayerService().getPlayerById(req.getPlayerId());
    playerById.setCombatIndex(req.getBattleScore());
    BattleScoreRankInfo rankInfo =
        BattleScoreRankInfo.valueOf(req.getPlayerId(), req.getBattleScore());
    EventBusManager.submitEvent(ChangeBattleScoreEvent.valueOf(rankInfo));
    SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("修改玩家战力完成"));
  }
}
