package com.example.gamedemo.server.game.fight.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.fight.packet.CM_UseSkill;
import com.example.gamedemo.server.game.fight.packet.CM_UseSkillWithTarget;
import com.example.gamedemo.server.game.player.model.Player;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/7/3
 */
@HandlerClass
@Component
public class FightController {

  /**
   * 使用技能无目标参数
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "useSkill3")
  public void useSkill(TSession session, CM_UseSkillWithTarget req) {
    Player player = session.getPlayer();
    boolean flag = false;
    try {
      flag = SpringContext.getSkillService().useSkill(player, req.getIndex());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (flag) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("使用技能完成"));
    }
  }

  /**
   * 使用技能有目标参数
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "useSkillTarget")
  public void useSkillTarget(TSession session, CM_UseSkill req) {
    Player player = session.getPlayer();
  }
}
