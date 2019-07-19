package com.example.gamedemo.server.game.guild.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.game.guild.packet.*;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：公会控制层
 * @date 2019/7/18
 */
@HandlerClass
@Component
public class GuildController {

  /**
   * 创建公会
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "createGuild")
  public void createGuild(TSession session, CM_CreateGuild req) {
    try {
      SpringContext.getGuildService()
          .createGuild(session.getPlayer(), req.getGuildId(), req.getGuildName());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 加入公会
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "joinGuild")
  public void joinGuild(TSession session, CM_JoinGuild req) {
    try {
      SpringContext.getGuildService().joinGuild(session.getPlayer(), req.getGuildId());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 退出公会
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "quitGuild")
  public void quitGuild(TSession session, CM_QuitGuild req) {
    try {
      SpringContext.getGuildService().quitGuild(session.getPlayer());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取公会列表
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "showGuild")
  public void showGuild(TSession session, CM_GetGuildList req) {
    try {
      SpringContext.getGuildService().getGuildList(session.getPlayer());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 处理请求
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "handleApply")
  public void handApply(TSession session, CM_HandleApply req) {
    try {
      SpringContext.getGuildService().handleApply(session.getPlayer(), req.getId(), req.isAgree());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @HandlerMethod(cmd = "showApply")
  public void showApply(TSession session, CM_GetApplyList req) {
    try {
      SpringContext.getGuildService().showApply(session.getPlayer());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取成员列表
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "showMembers")
  public void showMembers(TSession session, CM_GetMemberList req) {
    try {
      SpringContext.getGuildService().showMembers(session.getPlayer());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
