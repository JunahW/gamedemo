package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.base.resource.bean.CheckPointDef;
import com.example.gamedemo.server.game.base.resource.bean.RewardDef;
import com.example.gamedemo.server.game.base.utils.RewardUtils;
import com.example.gamedemo.server.game.dungeon.command.LeaveDungeonSceneNotifyDelayCommand;
import com.example.gamedemo.server.game.dungeon.model.DungeonMapInfo;
import com.example.gamedemo.server.game.dungeon.packet.SM_FinshRount;
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
    DungeonMapInfo mapInfo = mapHandler.getMapInfo(player);
    mapInfo.clear();

    Scene scene = SpringContext.getSceneService().createDungeonSceneBySceneId(sceneId);
    MapResource mapResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    // 刷怪
    List<CheckPointDef> checkPointDefList = mapResource.getCheckPointDefList();
    CheckPointDef checkPointDef = checkPointDefList.get(mapInfo.getRound() - 1);
    for (int i = 0; i < checkPointDef.getQuantity(); i++) {
      SpringContext.getMonsterService().createMonster(scene, checkPointDef.getMonsterId());
    }

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
    if (scene.isAllMonsterDead()) {
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
    // 地图信息
    AbstractMapHandler mapHandler = getMapHandler();
    DungeonMapInfo mapInfo = mapHandler.getMapInfo(player);
    // 增加杀怪数
    mapInfo.setKillMonsterQuantity(mapInfo.getKillMonsterQuantity() + 1);
    if (!scene.isAllMonsterDead()) {
      // 没杀完怪直接返回
      return;
    }
    MapResource mapResource =
        SpringContext.getSceneService().getSceneResourceById(scene.getSceneResourceId());
    List<CheckPointDef> checkPointDefList = mapResource.getCheckPointDefList();

    // 判断是否还有回合
    if (mapInfo.getRound() == checkPointDefList.size()) {
      logger.info("挑战副本成功");
      SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("挑战副本成功"));
      // 副本挑战成功 发放奖励
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
    } else {
      // 发回合奖励
      List<RewardDef> rewardDefs = checkPointDefList.get(mapInfo.getRound() - 1).getRewardDefs();
      boolean enoughPackSize = RewardUtils.isEnoughPackSize(player, rewardDefs);
      SessionManager.sendMessage(player, SM_FinshRount.valueOf(mapInfo.getRound()));
      if (enoughPackSize) {
        RewardUtils.reward(player, rewardDefs);
        logger.info("完成第[{}]回合获得奖励[{}]", mapInfo.getRound(), rewardDefs);
      } else {
        logger.info("{玩家[{}]背包无法装完奖励道具", player.getId());
      }
      // 进行下一回合
      mapInfo.setRound(mapInfo.getRound() + 1);
      SpringContext.getPlayerMapInfoService().savePlayerMapInfoEnt(player);
      CheckPointDef checkPointDef = checkPointDefList.get(mapInfo.getRound() - 1);
      for (int i = 0; i < checkPointDef.getQuantity(); i++) {
        SpringContext.getMonsterService().createMonster(scene, checkPointDef.getMonsterId());
      }
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
