package com.example.gamedemo.server.game.monster.service;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.MonsterAttributeContainer;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelIdEnum;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.player.model.Player;
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
  public Map<Long, SceneObject> getMonsters(Player player, int sceneId) {
    Scene scene = SpringContext.getSceneService().getSceneById(player, sceneId);
    return scene.getSceneObjectByType(SceneObjectTypeEnum.MONSTER);
  }

  @Override
  public void createMonster(Scene scene, int monsterResourceId) {
    MonsterResource monsterResource = monsterManager.getMonsterResource(monsterResourceId);
    List<Attribute> attributeList = monsterResource.getAttributeList();

    Monster monster = Monster.valueOf(monsterResourceId);
    MonsterAttributeContainer attributeContainer = monster.getAttributeContainer();
    attributeContainer.putAndComputeAttributes(AttributeModelIdEnum.BASE, attributeList);

    monster.setHp(attributeContainer.getAttributeValue(AttributeTypeEnum.HP));
    monster.setMp(attributeContainer.getAttributeValue(AttributeTypeEnum.MP));
    monster.setX(monsterResource.getX());
    monster.setY(monsterResource.getY());
    monster.setSceneId(scene.getSceneResourceId());
    scene.enterScene(monster);
  }

  @Override
  public void removeMonster(int sceneId, long guid) {
    /* Scene scene = SpringContext.getSceneService().getSceneById(sceneId);
    scene.leaveScene(guid);*/
  }

  @Override
  public MonsterResource getMonsterResourceById(int monsterResourceId) {
    return monsterManager.getMonsterResource(monsterResourceId);
  }

  @Override
  public Monster getMonsterById(Player player, Long monsterId) {
    int sceneId = player.getSceneId();
    Scene scene = SpringContext.getSceneService().getSceneById(player, sceneId);
    Map<Long, SceneObject> sceneObjectMap = scene.getSceneObjectMap();
    return (Monster) sceneObjectMap.get(monsterId);
  }

  @Override
  public void handleMonsterDead(Player player, Scene scene, Monster monster) {
    EventBusManager.submitEvent(MonsterDeadEvent.valueOf(player, scene, monster));
  }
}
