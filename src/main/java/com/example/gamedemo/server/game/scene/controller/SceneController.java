package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.packet.CM_AoiScene;
import com.example.gamedemo.server.game.scene.packet.CM_GotoScene;
import com.example.gamedemo.server.game.scene.packet.CM_ListScene;
import com.example.gamedemo.server.game.scene.packet.CM_MoveScene;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import com.example.gamedemo.server.game.scene.service.SceneService;
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
  @Autowired private SceneService sceneService;

  /**
   * 获取场景列表
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "list")
  public void getSceneList(TSession session, CM_ListScene req) {
    List<Scene> sceneList = sceneService.getSceneList();
    SessionManager.sendMessage(session, sceneList);
  }

  /**
   * 传送金进入场景
   *
   * @param session
   * @param req
   */
  @HandlerMethod(cmd = "goto")
  public void gotoScene(TSession session, CM_GotoScene req) {

    // 获取当前的账户信息
    Player player = session.getPlayer();

    sceneService.gotoScene(player, req.getSceneId());
    String returnMsg = player.getPlayerName() + "进入";
    SessionManager.sendMessage(session, returnMsg + "\r\n");
  }

  /**
   * 进入相邻的场景
   *
   * @param session
   * @param req
   * @return
   */
  @HandlerMethod(cmd = "move")
  public void move2Scene(TSession session, CM_MoveScene req) {
    // 获取当前的账户信息
    Player player = session.getPlayer();
    boolean isSuccess = sceneService.move2Scene(player, req.getSceneId());
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
    SceneResource sceneResource = sceneService.getSceneById(player.getSceneId());
    SessionManager.sendMessage(session, sceneResource.toString() + "\r\n");
  }
}
