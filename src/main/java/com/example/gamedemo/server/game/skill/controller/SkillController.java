package com.example.gamedemo.server.game.skill.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.packet.CM_StudySkill;
import com.example.gamedemo.server.game.skill.packet.CM_UpgradeSkill;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/6/20
 */
@HandlerClass
@Component
public class SkillController {

  /**
   * 学习技能
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "studySkill")
  public void studySkill(TSession session, CM_StudySkill req) {
    Player player = session.getPlayer();
    SpringContext.getSkillService().studySkill(player, req.getSkillId());
  }

  /**
   * 升级技能
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "upgradeSkill")
  public void upgradeSkill(TSession session, CM_UpgradeSkill req) {
    Player player = session.getPlayer();
    SpringContext.getSkillService().upgradeSkill(player, req.getSkillId());
  }
}
