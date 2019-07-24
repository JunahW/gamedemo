package com.example.gamedemo.server.game.scene.handler;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.model.AbstractMapInfo;
import com.example.gamedemo.server.game.mapinfo.entity.PlayerMapInfoEnt;
import com.example.gamedemo.server.game.mapinfo.model.PlayerMapInfo;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.example.gamedemo.server.game.scene.model.Scene;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description:地图处理器
 * @date 2019/7/15
 */
public abstract class AbstractMapHandler {
  /** 处理器集合 */
  private static final Map<SceneTypeEnum, AbstractMapHandler> handlerMap = new HashMap<>();

  /**
   * 获取地图处理器
   *
   * @param sceneTypeEnum
   * @param <T>
   * @return
   */
  public static <T extends AbstractMapHandler> T getHandler(SceneTypeEnum sceneTypeEnum) {
    return (T) handlerMap.get(sceneTypeEnum);
  }

  @PostConstruct
  public void init() {
    handlerMap.put(getSceneTypeEnum(), this);
  }

  /**
   * 获取场景类型
   *
   * @return
   */
  public abstract SceneTypeEnum getSceneTypeEnum();

  /**
   * 进入地图
   *
   * @param player
   * @param sceneId
   */
  public abstract void enterMap(Player player, int sceneId);

  /**
   * 离开地图
   *
   * @param player
   */
  public abstract void leaveMap(Player player);

  /**
   * 副本结束进行操作
   *
   * @param player
   */
  public void doEnd(Player player) {}

  /**
   * 处理怪物死亡
   *
   * @param player
   * @param scene
   * @param monster
   */
  public abstract void handleMonsterDead(Player player, Scene scene, Monster monster);

  /**
   * 获取当前地图信息
   *
   * @param player
   * @param <T>
   * @return
   */
  public <T extends AbstractMapInfo> T getMapInfo(Player player) {
    PlayerMapInfoEnt playerMapInfoEnt =
        SpringContext.getPlayerMapInfoService().getPlayerMapInfoEnt(player.getId());
    PlayerMapInfo playerMapInfo = playerMapInfoEnt.getPlayerMapInfo();
    AbstractMapInfo mapInfo = playerMapInfo.getOrCreateMapInfo(getSceneTypeEnum());
    return (T) mapInfo;
  }

  /**
   * 获取场景
   *
   * @param creature
   * @param sceneId
   * @return
   */
  public abstract Scene getScene(CreatureObject creature, int sceneId);

  /**
   * 判断是否可以进入地图
   *
   * @param player
   * @param sceneId
   * @return
   */
  public boolean canEnterMap(Player player, int sceneId) {
    return true;
  }
}
