package com.example.gamedemo.server.game.monster.service;

import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;

import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/6/14
 * @description: 怪物业务接口
 */
public interface MonsterService {
  /**
   * 获取怪物集合
   *
   * @param player
   * @param sceneId
   * @return
   */
  Map<Long, SceneObject> getMonsters(Player player, int sceneId);

  /**
   * 生成怪物
   *
   * @param scene
   * @param monsterResourceId
   */
  void createMonster(Scene scene, int monsterResourceId);

  /**
   * 移除场景中的怪物
   *
   * @param sceneId
   * @param guid
   */
  void removeMonster(int sceneId, long guid);

  /**
   * 获取怪物的配置信息
   *
   * @param monsterResourceId
   * @return
   */
  MonsterResource getMonsterResourceById(int monsterResourceId);

  /**
   * 获取怪物信息
   *
   * @param player
   * @param monsterId
   * @return
   */
  Monster getMonsterById(Player player, Long monsterId);

  /**
   * 处理怪物死亡a
   *
   * @param player
   * @param scene
   * @param monster
   */
  void handleMonsterDead(Player player, Scene scene, Monster monster);
}
