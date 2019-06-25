package com.example.gamedemo.server.game.monster.service;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.MonsterAttributeContainer;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelIdEnum;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
  public Map<Long, SceneObject> getMonsters(int sceneId) {
    Scene scene = SpringContext.getSceneService().getSceneById(sceneId);
    return scene.getSceneObjectByType(SceneObjectTypeEnum.MONSTER);
  }

  @Override
  public void createMonster(int sceneId, int monsterResourceId) {
    MonsterResource monsterResource = monsterManager.getMonsterResource(monsterResourceId);
    Scene scene = SpringContext.getSceneService().getSceneById(sceneId);
    List<Attribute> attributeList = monsterResource.getAttributeList();

    Monster monster = Monster.valueOf(monsterResourceId);
    MonsterAttributeContainer attributeContainer = monster.getMonsterAttributeContainer();
    attributeContainer.putAndComputeAttributes(AttributeModelIdEnum.BASE, attributeList);

    monster.setHp(attributeContainer.getAttributeValue(AttributeTypeEnum.HP));
    monster.setMp(attributeContainer.getAttributeValue(AttributeTypeEnum.MP));
    monster.setX(monsterResource.getX());
    monster.setY(monsterResource.getY());
    scene.enterScene(monster);
  }

  @Override
  public void removeMonster(int sceneId, long guid) {
    Scene scene = SpringContext.getSceneService().getSceneById(sceneId);
    scene.removeSceneObject(guid);
  }

  @Override
  public MonsterResource getMonsterResourceById(int monsterResourceId) {
    return monsterManager.getMonsterResource(monsterResourceId);
  }
}
