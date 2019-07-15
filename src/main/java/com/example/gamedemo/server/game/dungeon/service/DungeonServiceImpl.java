package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.dungeon.command.LeaveDungeonSceneNotifyDelayCommand;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.command.SceneBuffRateCommand;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/7/10
 * @description: 副本业务层
 */
@Service
public class DungeonServiceImpl implements DungeonService {
  private static final Logger logger = LoggerFactory.getLogger(DungeonServiceImpl.class);

  @Autowired private DungeonManager dungeonManager;

  @Override
  public void enterDungeon(Player player, int sceneId) {
    logger.info("[{}][{}]进入副本地图[{}]", player.getSceneObjectType(), player.getId(), sceneId);
    Scene scene = SpringContext.getSceneService().createDungeonSceneBySceneId(sceneId);
    MapResource mapResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    // 进入场景
    player.setBeforeSceneId(player.getSceneId());
    player.setSceneId(sceneId);
    player.setX(mapResource.getX());
    player.setY(mapResource.getY());
    scene.enterScene(player);
    dungeonManager.putScene(player.getId(), scene);
    // 启动场景刷新任务
    SceneBuffRateCommand command =
        SceneBuffRateCommand.valueOf(scene, GameConstant.SCENE_DELAY, GameConstant.SCENE_PERIOD);
    SpringContext.getSceneExecutorService().submit(command);
    scene.putCommand(command);

    SpringContext.getSceneExecutorService()
        .submit(
            LeaveDungeonSceneNotifyDelayCommand.valueOf(
                sceneId,
                mapResource.getDuration() - GameConstant.DUNGEON_COUNT_DOWN,
                GameConstant.DUNGEON_COUNT_DOWN,
                player));
  }

  @Override
  public void leaveDungeon(Player player) {
    Scene scene = SpringContext.getSceneService().getSceneById(player, player.getSceneId());

    Map<Class<? extends Command>, Command> commandMap = scene.getCommandMap();
    for (Command command : commandMap.values()) {
      command.cancel();
      logger.info("取消副本场景[{}]中的任务[{}]", player.getSceneId(), command.getClass());
    }
    // 回到原来的地图
    scene.leaveScene(player.getId());
    dungeonManager.removeScene(player.getId());
    logger.info(
        "[{}][{}]离开副本[{}]", player.getSceneObjectType(), player.getId(), player.getSceneId());
  }

  @Override
  public Scene getDungeonSceneByPlayerId(Long playerId) {
    return dungeonManager.getSceneByPlayerId(playerId);
  }
}
