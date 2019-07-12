package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.LandformResource;
import com.example.gamedemo.server.game.scene.resource.MapResource;

import java.util.List;
import java.util.Map;

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
   * 获取场景
   *
   * @param creature
   * @param sceneId
   * @return
   */
  Scene getSceneById(CreatureObject creature, int sceneId);

  /**
   * 客户端请求切图
   *
   * @param player
   * @param sceneId
   * @return
   */
  boolean changeScene(Player player, int sceneId);

  /**
   * 服务端切图
   *
   * @param player
   * @param sceneId
   * @return
   */
  boolean serverChangeScene(Player player, int sceneId);

  /**
   * 进入场景
   *
   * @param player
   * @param sceneId
   * @return
   */
  boolean enterScene(Player player, int sceneId);

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
   * @param scene
   * @param monsterResourceId
   */
  void createDropObject(Scene scene, int monsterResourceId);

  /**
   * @param sceneId
   * @param playerId
   */
  // void handlePlayerEnterScene(int sceneId, long playerId);

  /**
   * 处理怪物死亡事件
   *
   * @param attacker
   * @param scene
   * @param monsterId
   * @param monsterResourceId
   */
  void handMonsterDeadEvent(
      CreatureObject attacker, Scene scene, long monsterId, int monsterResourceId);

  /** 开启场景线程 */
  void sceneStart();

  /**
   * 获取某场景下的怪物资源集合
   *
   * @param sceneId
   * @return
   */
  Map<Integer, MonsterResource> getMonsterResourceMapBySceneId(int sceneId);

  /**
   * aoi
   *
   * @param player
   */
  void aoi(Player player);

  /**
   * 获取地形
   *
   * @param id
   * @return
   */
  LandformResource getLandformResourceById(int id);
}
