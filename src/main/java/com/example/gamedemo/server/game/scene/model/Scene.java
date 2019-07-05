package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.scene.command.SceneBuffTriggerCommand;
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

  public void statSceneTimer() {
    SceneExecutor.addScheduleTask(
        sceneResourceId,
        1000,
        200,
        Long.MAX_VALUE,
        SceneBuffTriggerCommand.valueOf(sceneResourceId));
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
}
