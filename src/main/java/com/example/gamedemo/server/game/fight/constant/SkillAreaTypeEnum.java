package com.example.gamedemo.server.game.fight.constant;

import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.common.utils.FormulaUtils;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;

import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/7/3
 * @description: 技能范围类型
 */
public enum SkillAreaTypeEnum {

  /** 点 */
  POINT(1),

  /** 圆形 */
  CIRCULAR(2) {
    @Override
    public Long getTargetIdByAreaType(CreatureObject creatureObject, String skillAreaParam) {
      Map<String, Integer> map = JsonUtils.deSerializeEntity(skillAreaParam, Map.class);
      Integer radius = map.get(RADIUS);
      int minDistance = Integer.MAX_VALUE;
      int distance = 0;
      Long targetId = null;
      Map<Long, SceneObject> sceneObjectMap =
          creatureObject.getSceneObjectView().getSceneObjectMap();
      for (Map.Entry<Long, SceneObject> sceneObjectEntry : sceneObjectMap.entrySet()) {
        SceneObject sceneObject = sceneObjectEntry.getValue();
        if (sceneObject instanceof CreatureObject) {
          CreatureObject targetCreature = (CreatureObject) sceneObject;
          distance =
              FormulaUtils.computeDistance(
                  creatureObject.getX(),
                  creatureObject.getY(),
                  targetCreature.getX(),
                  targetCreature.getY());
          if (distance <= radius && distance < minDistance) {
            minDistance = distance;
            targetId = targetCreature.getId();
          }
        }
      }
      return targetId;
    }
  };
  /** 半径常量 */
  private static final String RADIUS = "radius";

  /** 范围类型 */
  private int skillAreaType;

  SkillAreaTypeEnum(int areaType) {
    this.skillAreaType = areaType;
  }

  /**
   * 通过攻击类型获取枚举
   *
   * @param areaType
   * @return
   */
  public static SkillAreaTypeEnum getAreaTypeEnumByAreaType(int areaType) {
    for (SkillAreaTypeEnum areaTypeEnum : SkillAreaTypeEnum.values()) {
      if (areaTypeEnum.getSkillAreaType() == areaType) {
        return areaTypeEnum;
      }
    }
    return null;
  }

  public int getSkillAreaType() {
    return skillAreaType;
  }

  public void setSkillAreaType(int skillAreaType) {
    this.skillAreaType = skillAreaType;
  }

  /**
   * 获取最近的目标物
   *
   * @param creatureObject
   * @param skillAreaParam
   * @return
   */
  public Long getTargetIdByAreaType(CreatureObject creatureObject, String skillAreaParam) {
    return null;
  }
}
