package com.example.gamedemo.server.game.rank.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.rank.event.ChangeBattleScoreEvent;
import com.example.gamedemo.server.game.rank.packet.CM_ShowRank;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/7/22
 */
@Component
@HandlerClass
public class RankController {

  /**
   * 展示排行板
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "showRank")
  public void showRank(TSession session, CM_ShowRank req) {
    SpringContext.getRankService().showRank(session.getPlayer(), req.getIndex());
  }

  /**
   * 处理改变战力事件
   *
   * @param event
   */
  @ReceiverHandler
  public void handleChangeBattleScoreEvent(ChangeBattleScoreEvent event) {
    SpringContext.getRankService().handleChangeBattleScoreEvent(event);
  }
}
