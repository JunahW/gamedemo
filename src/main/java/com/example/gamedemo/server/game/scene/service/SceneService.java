package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.MapResource;

import java.util.List;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 场景业务接口层
 */
public interface SceneService {
  /**
   * 获取场景列表
   *
   * @return
   */
  List<Scene> getSceneList();

  /**
   * 进入场景
   *
   * @param player
   * @param sceneId
   * @return
   */
  boolean gotoScene(Player player, int sceneId);

  /**
   * 通过ID获取场景
   *
   * @param sceneId
   * @return
   */
  Scene getSceneById(int sceneId);

  /**
   * 去其他的相邻场景
   *
   * @param player
   * @param sceneId
   * @return
   */
  boolean move2Scene(Player player, int sceneId);

  /**
   * 通过id获取配置信息
   *
   * @param sceneId
   * @return
   */
  MapResource getSceneResourceById(int sceneId);

  /**
   * 玩家移动
   *
   * @param player
   * @param x
   * @param y
   * @return
   */
  boolean move2Coordinate(Player player, int x, int y);

  /**
   * 为场景创建怪物
   *
   * @param sceneId
   */
  void createMonsters4Scene(int sceneId);

  /**
   * 怪物死亡，创建掉落物
   *
   * @param sceneId
   * @param monsterId
   */
  void createDropObject(int sceneId, long monsterId);

  /**
   * @param sceneId
   * @param playerId
   */
  // void handlePlayerEnterScene(int sceneId, long playerId);

  /**
   * 处理怪物死亡事件
   *
   * @param sceneId
   * @param monsterId
   */
  void handMonsterDeadEvent(int sceneId, long monsterId);
}
