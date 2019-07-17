package com.example.gamedemo.server.game.scene.handler.impl;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.example.gamedemo.server.game.scene.handler.AbstractMapHandler;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：副本地图处理器
 * @date 2019/7/15
 */
@Component
public class DungeonMapHandler extends AbstractMapHandler {
  @Override
  public SceneTypeEnum getSceneTypeEnum() {
    return SceneTypeEnum.DUNGEON_SCENE;
  }

  @Override
  public void enterMap(Player player, int sceneId) {
    SpringContext.getDungeonService().enterDungeon(player, sceneId);
  }

  @Override
  public void leaveMap(Player player) {
    SpringContext.getDungeonService().leaveDungeon(player);
  }

  @Override
  public void handleMonsterDead(Player player, Scene scene, Monster monster) {
    SpringContext.getDungeonService().handleMonsterDead(player, scene, monster);
  }
}
