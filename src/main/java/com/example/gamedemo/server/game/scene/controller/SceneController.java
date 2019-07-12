package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.anno.ReceiverHandler;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.packet.SM_ErrorCode;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.packet.CM_MovePosition;
import com.example.gamedemo.server.game.scene.event.PlayerEnterSceneEvent;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.packet.CM_AoiScene;
import com.example.gamedemo.server.game.scene.packet.CM_GotoScene;
import com.example.gamedemo.server.game.scene.packet.CM_ListScene;
import com.example.gamedemo.server.game.scene.packet.CM_MoveScene;
import com.example.gamedemo.server.game.scene.service.SceneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
@HandlerClass
public class SceneController {

  @Autowired private SceneManager sceneManager;

  /**
   * 获取场景列表
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "listScene")
  public void getSceneList(TSession session, CM_ListScene req) {
    List<Scene> sceneList = SpringContext.getSceneService().getSceneList();
    SessionManager.sendMessage(session, sceneList);
  }

  /**
   * 传送金进入场景
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "gotoScene")
  public void gotoScene(TSession session, CM_GotoScene req) {
    // 获取当前的账户信息
    Player player = session.getPlayer();

    boolean isSuccess = false;
    try {
      isSuccess = SpringContext.getSceneService().gotoScene(player, req.getSceneId());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (isSuccess) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("进入场景成功"));
    }
  }

  /**
   * 进入相邻的场景
   *
   * @param session
   * @param req
   * @return
   */
  @HandlerMethod(cmd = "moveScene")
  public void move2Scene(TSession session, CM_MoveScene req) {
    // 获取当前的账户信息
    Player player = session.getPlayer();
    boolean isSuccess = false;
    try {
      isSuccess = SpringContext.getSceneService().changeScene(player, req.getSceneId());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (isSuccess) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("进入场景成功"));
    }
  }

  /**
   * 玩家移动到指定坐标
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "movePosition")
  public void move2Coordinate(TSession session, CM_MovePosition req) {
    Player player = session.getPlayer();
    boolean isSuccess = false;
    try {
      isSuccess = SpringContext.getSceneService().move2Coordinate(player, req.getX(), req.getY());
    } catch (RequestException e) {
      SessionManager.sendMessage(session, SM_ErrorCode.valueOf(e.getErrorCode()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (isSuccess) {
      SessionManager.sendMessage(session, SM_NoticeMessge.valueOf("移动成功"));
    }
  }

  /**
   * 获取当前场景下的实体
   *
   * @param session
   * @param req
   * @return
   */
  @HandlerMethod(cmd = "aoi")
  public void getSceneObject(TSession session, CM_AoiScene req) {
    // 获取当前的账户信息
    Player player = session.getPlayer();
    SpringContext.getSceneService().aoi(player);
  }

  @ReceiverHandler
  public void handlePlayerEnterScene(PlayerEnterSceneEvent event) {
    // SpringContext.getSceneService().createMonsters4Scene(event.getSceneId());
  }

  @ReceiverHandler
  public void handleMonsterDeadEvent(MonsterDeadEvent event) {
    SpringContext.getSceneService()
        .handMonsterDeadEvent(
            event.getAttacker(),
            event.getScene(),
            event.getMonsterId(),
            event.getMonsterResourceId());
  }
}
