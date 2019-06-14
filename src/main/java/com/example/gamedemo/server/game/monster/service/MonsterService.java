package com.example.gamedemo.server.game.monster.service;

/**
 * @author: wengj
 * @date: 2019/6/14
 * @description: 怪物业务接口
 */
public interface MonsterService {
  /**
   * 生成怪物
   *
   * @param sceneId
   * @param monsterResourceId
   */
  void createMonster(int sceneId, int monsterResourceId);

  /**
   * 移除场景中的怪物
   *
   * @param sceneId
   * @param guid
   */
  void removeMonster(int sceneId, int guid);
}
