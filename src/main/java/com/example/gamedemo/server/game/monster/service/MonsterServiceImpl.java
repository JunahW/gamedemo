package com.example.gamedemo.server.game.monster.service;

import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description:怪物业务层
 * @date 2019/6/14
 */
@Service
public class MonsterServiceImpl implements MonsterService {
  @Override
  public void createMonster(int sceneId, int monsterResourceId) {}

  @Override
  public void removeMonster(int sceneId, int guid) {}
}
