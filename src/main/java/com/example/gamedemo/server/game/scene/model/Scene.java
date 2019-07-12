package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.executer.Command;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.scene.command.SceneBuffRateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：场景模型
 * @date 2019/6/13
 */
public class Scene {
  private static final Logger logger = LoggerFactory.getLogger(Scene.class);
  /** 场景id */
  private int sceneResourceId;

  /** 场景中的对象 */
  private Map<Long, SceneObject> sceneObjectMap = new HashMap<>();

  /** 场景内的任务 */
  private Map<Class<? extends Command>, Command> commandMap = new HashMap<>();

  public Scene(int sceneResourceId) {
    this.sceneResourceId = sceneResourceId;
  }

  public static Scene valueOf(int sceneResourceId) {
    Scene scene = new Scene(sceneResourceId);
    return scene;
  }

  public int getSceneResourceId() {
    return sceneResourceId;
  }

  public void setSceneResourceId(int sceneResourceId) {
    this.sceneResourceId = sceneResourceId;
  }

  public Map<Long, SceneObject> getSceneObjectMap() {
    return sceneObjectMap;
  }

  public void setSceneObjectMap(Map<Long, SceneObject> sceneObjectMap) {
    this.sceneObjectMap = sceneObjectMap;
  }

  public Map<Class<? extends Command>, Command> getCommandMap() {
    return commandMap;
  }

  public void setCommandMap(Map<Class<? extends Command>, Command> commandMap) {
    this.commandMap = commandMap;
  }

  /**
   * 进入场景
   *
   * @param sceneObject
   */
  public void enterScene(SceneObject sceneObject) {
    putSceneObject(sceneObject);

    // 改变地图中对象的视野
    for (Map.Entry<Long, SceneObject> sceneObjectEntry : sceneObjectMap.entrySet()) {
      SceneObject value = sceneObjectEntry.getValue();
      if (value instanceof CreatureObject && !sceneObject.equals(value)) {
        // TODO 可以新增额外条件，判断是否加入视野范围内
        ((CreatureObject) value).getSceneObjectView().putSceneObject(sceneObject);
      }
    }

    // 改变当前对象的视野
    if (sceneObject instanceof CreatureObject) {
      CreatureObject creatureObject = (CreatureObject) sceneObject;
      for (Map.Entry<Long, SceneObject> sceneObjectEntry : sceneObjectMap.entrySet()) {
        SceneObject value = sceneObjectEntry.getValue();
        if (!creatureObject.equals(value)) {
          // TODO 可以新增额外条件，判断是否加入视野范围内
          creatureObject.getSceneObjectView().putSceneObject(value);
        }
      }
    }
  }

  /**
   * 离开场景
   *
   * @param id
   */
  public void leaveScene(Long id) {
    // 判断是否为有生命的对象
    SceneObject sceneObject = sceneObjectMap.get(id);

    // 改变地图中对象的视野
    for (Map.Entry<Long, SceneObject> sceneObjectEntry : sceneObjectMap.entrySet()) {
      SceneObject value = sceneObjectEntry.getValue();
      if (value instanceof CreatureObject && !sceneObject.equals(value)) {
        ((CreatureObject) value).getSceneObjectView().removeSceneObject(id);
      }
    }
    // 清除对象的视野
    if (sceneObject instanceof CreatureObject) {
      ((CreatureObject) sceneObject).getSceneObjectView().clearSceneObjectView();
    }
    // 移除场景该对象
    removeSceneObject(id);
  }

  /**
   * 玩家移动
   *
   * @param creatureObject
   */
  public void biologyObjectMove(CreatureObject creatureObject) {
    // 更新视野
    for (Map.Entry<Long, SceneObject> sceneObjectEntry : sceneObjectMap.entrySet()) {
      SceneObject value = sceneObjectEntry.getValue();
      if (value instanceof CreatureObject && !creatureObject.equals(value)) {
        // TODO 可以新增额外条件，判断是否加入视野范围内
        ((CreatureObject) value).getSceneObjectView().putSceneObject(creatureObject);
      }
    }
  }

  /**
   * 新增场景对象
   *
   * @param sceneObject
   */
  public void putSceneObject(SceneObject sceneObject) {
    sceneObjectMap.put(sceneObject.getId(), sceneObject);
  }

  /**
   * 移除场景对象
   *
   * @param id
   */
  public void removeSceneObject(Long id) {
    sceneObjectMap.remove(id);
  }

  /**
   * 获取指定类型的场景对象
   *
   * @param type
   * @return
   */
  public Map<Long, SceneObject> getSceneObjectByType(SceneObjectTypeEnum type) {
    Map<Long, SceneObject> map = new HashMap<>();
    for (Map.Entry<Long, SceneObject> entry : sceneObjectMap.entrySet()) {
      if (entry.getValue().getSceneObjectType().equals(type)) {
        map.put(entry.getKey(), entry.getValue());
      }
    }
    return map;
  }

  /**
   * 获取场景中的CreatureObject
   *
   * @param id
   * @return
   */
  public CreatureObject getCreatureObjectById(Long id) {
    SceneObject sceneObject = sceneObjectMap.get(id);

    if (sceneObject instanceof CreatureObject) {
      return (CreatureObject) sceneObject;
    }
    return null;
  }

  /** 场景开始 */
  public void start() {
    SpringContext.getSceneExecutorService()
        .submit(
            SceneBuffRateCommand.valueOf(
                this, GameConstant.SCENE_DELAY, GameConstant.SCENE_PERIOD));
  }

  /** 初始化怪物 */
  public void initMonster() {
    Map<Integer, MonsterResource> monsterResourceMap =
        SpringContext.getSceneService().getMonsterResourceMapBySceneId(sceneResourceId);
    // 初始化怪物
    if (monsterResourceMap != null) {
      for (Map.Entry<Integer, MonsterResource> entry : monsterResourceMap.entrySet()) {
        MonsterResource monsterResource = entry.getValue();
        // 生成多个怪物
        for (int i = 0; i < monsterResource.getQuantity(); i++) {
          SpringContext.getMonsterService().createMonster(this, monsterResource.getMonsterId());
        }
      }
    }
  }
}
