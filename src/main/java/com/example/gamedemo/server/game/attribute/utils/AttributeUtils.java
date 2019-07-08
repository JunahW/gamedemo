package com.example.gamedemo.server.game.attribute.utils;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.AttributeSet;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.Map;

/**
 * @author wengj
 * @description：属性工具类
 * @date 2019/7/8
 */
public class AttributeUtils {
  /**
   * 将各个模块的属性累加到attributeMap
   *
   * @param modelAttributeListMap
   * @param attributeMap
   */
  public static void accumulateModeAttribute2AttributeMap(
      Map<AttributeModelId, AttributeSet> modelAttributeListMap,
      Map<AttributeTypeEnum, Attribute> attributeMap) {
    for (Map.Entry<AttributeModelId, AttributeSet> entry : modelAttributeListMap.entrySet()) {
      AttributeSet attributeSet = entry.getValue();
      // 累加各个属性的值
      for (Map.Entry<AttributeTypeEnum, Attribute> attributeEntry :
          attributeSet.getAttributeMap().entrySet()) {
        Attribute attribute = attributeEntry.getValue();
        if (attributeMap.containsKey(attribute.getType())) {
          // 计算同个属性计算后的值
          Attribute resultAttribute = attributeMap.get(attribute.getType());
          resultAttribute.setValue(resultAttribute.getValue() + attribute.getValue());
        } else {
          attributeMap.put(
              attribute.getType(), Attribute.valueof(attribute.getType(), attribute.getValue()));
        }
      }
    }
  }

  /**
   * 计算战力
   *
   * @param finalAttributeMap
   * @return
   */
  public static long computeCombatIndex(Map<AttributeTypeEnum, Attribute> finalAttributeMap) {
    long combatIndex = 0;
    Attribute hpAttribute = finalAttributeMap.get(AttributeTypeEnum.HP);
    Attribute mpAttribute = finalAttributeMap.get(AttributeTypeEnum.MP);
    Attribute attackAttribute = finalAttributeMap.get(AttributeTypeEnum.ATTACK);
    Attribute defenseAttribute = finalAttributeMap.get(AttributeTypeEnum.DEFENSE);
    /** 计算公式：战力 = 血量*50 + 魔法值*100 + 攻击力*300 + 防御力*200 */
    combatIndex =
        hpAttribute.getValue() * 50
            + mpAttribute.getValue() * 100
            + attackAttribute.getValue() * 300
            + defenseAttribute.getValue() * 200;
    return combatIndex;
  }
}
