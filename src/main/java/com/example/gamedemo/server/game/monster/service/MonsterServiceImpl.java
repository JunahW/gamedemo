package com.example.gamedemo.server.game.monster.service;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wengj
 * @description:怪物业务层
 * @date 2019/6/14
 */
@Service
public class MonsterServiceImpl implements MonsterService {
  @Autowired private MonsterManager monsterManager;

  @Override
  public Map<Long, Monster> getMonsters(int sceneId) {
    Scene scene = SpringContext.getSceneService().getSceneById(sceneId);
    return scene.getMonsterMap();
  }

  @Override
  public void createMonster(int sceneId, int monsterResourceId) {}

  @Override
  public void removeMonster(int sceneId, int guid) {}

  @Override
  public MonsterResource getMonsterResourceById(int monsterResourceId) {
    return monsterManager.getMonsterResource(monsterResourceId);
  }
}
