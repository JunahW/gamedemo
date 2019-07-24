package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.base.model.AbstractMapInfo;
import com.example.gamedemo.server.game.base.resource.bean.RewardDef;
import com.example.gamedemo.server.game.base.utils.RewardUtils;
import com.example.gamedemo.server.game.dungeon.command.LeaveDungeonSceneNotifyDelayCommand;
import com.example.gamedemo.server.game.dungeon.model.DungeonMapInfo;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.command.SceneBuffRateCommand;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.example.gamedemo.server.game.scene.handler.AbstractMapHandler;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    AbstractMapHandler mapHandler = getMapHandler();
    // 地图信息
    AbstractMapInfo mapInfo = mapHandler.getMapInfo(player);

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
    LeaveDungeonSceneNotifyDelayCommand delayCommand =
        LeaveDungeonSceneNotifyDelayCommand.valueOf(
            sceneId,
            mapResource.getDuration() - GameConstant.DUNGEON_COUNT_DOWN,
            GameConstant.DUNGEON_COUNT_DOWN,
            player);
    scene.putCommand(delayCommand);
    SpringContext.getSceneExecutorService().submit(delayCommand);
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
    Map<Long, SceneObject> sceneObjectByType =
        scene.getSceneObjectByType(SceneObjectTypeEnum.MONSTER);
    if (sceneObjectByType.size() == 0) {
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("挑战副本成功，离开副本"));
    } else {
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("挑战副本失败，离开副本"));
    }
    scene.leaveScene(player.getId());
    dungeonManager.removeScene(player.getId());
    logger.info(
        "[{}][{}]离开副本[{}]", player.getSceneObjectType(), player.getId(), player.getSceneId());
  }

  @Override
  public Scene getDungeonSceneByPlayerId(Long playerId) {
    return dungeonManager.getSceneByPlayerId(playerId);
  }

  @Override
  public void handleMonsterDead(Player player, Scene scene, Monster monster) {
    Map<Long, SceneObject> sceneObjectByType =
        scene.getSceneObjectByType(SceneObjectTypeEnum.MONSTER);
    if (sceneObjectByType.size() == 0) {
      logger.info("挑战副本成功");
      // 副本挑战成功 发放奖励
      MapResource mapResource =
          SpringContext.getSceneService().getSceneResourceById(scene.getSceneResourceId());
      List<RewardDef> rewardDefs = mapResource.getRewardDefs();
      logger.info("获得奖励[{}]", rewardDefs);
      // TODO
      boolean enoughPackSize = RewardUtils.isEnoughPackSize(player, rewardDefs);
      if (enoughPackSize) {
        RewardUtils.reward(player, rewardDefs);
        logger.info("获得奖励[{}]", rewardDefs);
      } else {
        logger.info("{玩家[{}]背包无法装完奖励道具", player.getId());
      }

      // 离开地图
      SpringContext.getSceneService().changeScene(player, player.getBeforeSceneId());
    }
  }

  @Override
  public boolean canEnterScene(Player player, int sceneId) {
    MapResource resource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    DungeonMapInfo mapInfo = getMapHandler().getMapInfo(player);
    long currentTimeMillis = System.currentTimeMillis();
    long nextEnterTime = mapInfo.getLastLeaveTime() + resource.getCd();
    if (currentTimeMillis >= nextEnterTime) {
      return true;
    }
    return false;
  }

  /**
   * 获取处理器
   *
   * @return
   */
  private AbstractMapHandler getMapHandler() {
    return AbstractMapHandler.getHandler(SceneTypeEnum.DUNGEON_SCENE);
  }
}
