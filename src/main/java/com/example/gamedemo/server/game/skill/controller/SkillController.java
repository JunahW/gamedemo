package com.example.gamedemo.server.game.skill.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.packet.*;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
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
    boolean flag = false;
    try {
      flag = SpringContext.getSkillService().studySkill(player, req.getSkillId());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (flag) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("学习技能完成"));
    }
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

  /**
   * 选择技能
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "selectSkill")
  public void selectSkill(TSession session, CM_SelectSkill req) {
    Player player = session.getPlayer();
    boolean flag = false;
    try {
      flag = SpringContext.getSkillService().selectSkill(player, req.getSkillId(), req.getIndex());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (flag) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("选择技能成功"));
    }
  }

  /**
   * 移除技能
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "removeSkill")
  public void removeSkill(TSession session, CM_RemoveSkill req) {
    Player player = session.getPlayer();
    boolean flag = false;
    try {
      flag = SpringContext.getSkillService().removeSkill(player, req.getIndex());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (flag) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("移除技能成功"));
    }
  }

  /**
   * 查看技能
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "showSkill")
  public void showSkill(TSession session, CM_ShowSkill req) {
    SkillStorage skillStorage = session.getPlayer().getSkillStorage();
    SessionManager.sendMessage(session, skillStorage);
  }

  /**
   * 使用技能
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "useSkill")
  public void useSkill(TSession session, CM_UseSkill req) {
    Player player = session.getPlayer();
    boolean flag = false;
    try {
      flag = SpringContext.getSkillService().useSkill(player, req.getIndex(), req.getTargetId());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (flag) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("使用技能完成"));
    }
  }
}
