package com.example.gamedemo.server.game.fight.constant;

import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.common.utils.FormulaUtils;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.player.model.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: wengj
 * @date: 2019/7/3
 * @description: 攻击后范围类型
 */
public enum AreaTypeEnum {

  /** 点 */
  POINT(1),

  /** 圆形 */
  CIRCULAR(2) {
    @Override
    public Set<CreatureObject> getAreaCreatureObjectList(
        Player player, CreatureObject creatureObject, String areaParam) {
      Set<CreatureObject> creatureObjectList = new HashSet<>();
      Map<String, Integer> map = JsonUtils.deSerializeEntity(areaParam, Map.class);
      Integer radius = map.get(RADIUS);
      Map<Long, SceneObject> sceneObjectMap =
          creatureObject.getSceneObjectView().getSceneObjectMap();
      for (Map.Entry<Long, SceneObject> sceneObjectEntry : sceneObjectMap.entrySet()) {
        SceneObject sceneObject = sceneObjectEntry.getValue();
        if (sceneObject instanceof CreatureObject && !player.getId().equals(sceneObject.getId())) {
          CreatureObject targetCreature = (CreatureObject) sceneObject;
          int distance =
              FormulaUtils.computeDistance(
                  creatureObject.getX(),
                  creatureObject.getY(),
                  targetCreature.getX(),
                  targetCreature.getY());
          // 判断距离
          if (distance <= radius) {
            creatureObjectList.add(targetCreature);
          }
        }
      }
      return creatureObjectList;
    }
  };
  /** 半径常量 */
  private static final String RADIUS = "radius";

  /** 范围类型 */
  private int areaType;

  AreaTypeEnum(int areaType) {
    this.areaType = areaType;
  }

  /**
   * 通过攻击类型获取枚举
   *
   * @param areaType
   * @return
   */
  public static AreaTypeEnum getAreaTypeEnumByAreaType(int areaType) {
    for (AreaTypeEnum areaTypeEnum : AreaTypeEnum.values()) {
      if (areaTypeEnum.getAreaType() == areaType) {
        return areaTypeEnum;
      }
    }
    return null;
  }

  public int getAreaType() {
    return areaType;
  }

  public void setAreaType(int areaType) {
    this.areaType = areaType;
  }

  /**
   * 获取目标集合
   *
   * @param creatureObject
   * @param areaParam
   * @return
   */
  public Set<CreatureObject> getAreaCreatureObjectList(
      Player player, CreatureObject creatureObject, String areaParam) {
    Set<CreatureObject> creatureObjects = new HashSet<>();
    return creatureObjects;
  }
}
