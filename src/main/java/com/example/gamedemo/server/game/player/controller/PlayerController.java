package com.example.gamedemo.server.game.player.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.packet.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
@HandlerClass
public class PlayerController {

  /**
   * 通过id获取账户信息
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "getPlayer")
  public void getPlayerById(TSession session, CM_GetPlayer req) {
    // 获取当前的账户信息
    Player player = session.getPlayer();
    String returnMsg = null;
    if (player == null) {
      returnMsg = "未选择角色\r\n";
    } else {
      returnMsg = player.toString() + "\r\n";
    }

    SessionManager.sendMessage(session, returnMsg);
  }

  /**
   * 创建用户
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "createPlayer")
  public void createPlayer(TSession session, CM_CreatePlayer req) {
    Account account = session.getAccount();
    Player player = new Player();
    player.setId(req.getPlayerId());
    player.setPlayerType(req.getPlayerType());
    player.setAccountId(account.getAccountId());
    boolean isSuccess = false;
    try {
      isSuccess = SpringContext.getPlayerService().createPlayer(player);
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (isSuccess) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("创建角色成功"));
    } else {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("创建角色失败"));
    }
  }

  /**
   * 选择角色
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "selectPlayer")
  public void selectPlayer(TSession session, CM_LoginAccount req) {
    Account account = session.getAccount();
    Player player = null;
    try {
      player =
          SpringContext.getPlayerService().selectPlayer(account.getAccountId(), req.getPlayerId());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (player != null) {
      SessionManager.registerPlayer(session, player);
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("选择玩家完成"));
    }
  }

  /**
   * 获取当前位置
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "where")
  public void getWhere(TSession session, CM_Location req) {
    Player player = session.getPlayer();
    SessionManager.sendMessage(
        session, SM_PlayerWhere.valueOf(player.getSceneId(), player.getX(), player.getY()));
  }

  /**
   * 查看玩家属性
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "playerAttr")
  public void getPlayerAttributeByPlayerId(TSession session, CM_PlayerAttr req) {
    Player player = session.getPlayer();
    ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap = null;
    try {
      attributeMap =
          (SpringContext.getPlayerService().getPlayerAttrByPlayerId(player, req.getPlayerId()));
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    SessionManager.sendMessage(session, SM_PlayerAttrs.valueOf(attributeMap));
  }

  /**
   * 处理用户加载事件
   *
   * @param event
   */
  @ReceiverHandler
  public void handlerPlayerLoad(PlayerLoadEvent event) {
    SpringContext.getPlayerService().computePlayerBaseAttributes(event);
  }

  /**
   * 测试触发事件
   *
   * @param session
   * @param rep
   */
  @HandlerMethod(cmd = "event")
  public void triggerEvent(TSession session, CM_TestEvent rep) {
    EventBusManager.submitEvent(new PlayerLoadEvent(session.getPlayer()));
    System.out.println("触发了事件");
  }
}
