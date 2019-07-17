package com.example.gamedemo.server.game.scene.handler.impl;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.example.gamedemo.server.game.scene.handler.AbstractMapHandler;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：普通地图处理器
 * @date 2019/7/15
 */
@Component
public class CommonMapHandler extends AbstractMapHandler {
  private static final Logger logger = LoggerFactory.getLogger(CommonMapHandler.class);

  @Override
  public SceneTypeEnum getSceneTypeEnum() {
    return SceneTypeEnum.COMMON_SCENE;
  }

  @Override
  public void enterMap(Player player, int sceneId) {
    Scene targetScene = SpringContext.getSceneService().getSceneById(player, sceneId);
    MapResource mapResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    player.setSceneId(sceneId);
    player.setX(mapResource.getX());
    player.setY(mapResource.getY());
    targetScene.enterScene(player);
    logger.info("[{}][{}]进入[{}]", player.getSceneObjectType(), player.getId(), sceneId);
  }

  @Override
  public void leaveMap(Player player) {
    // 当前的场景
    Scene currentScene = SpringContext.getSceneService().getSceneById(player, player.getSceneId());
    // 退出当前场景
    currentScene.leaveScene(player.getId());
  }

  @Override
  public void handleMonsterDead(Player player, Scene scene, Monster monster) {}
}
