package com.example.gamedemo.server.common.utils;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.Map;

/**
 * @author wengj
 * @description:公式工具类
 * @date 2019/6/13
 */
public class FormulaUtils {

  /**
   * 计算战力
   *
   * @param attributeMap
   * @return
   */
  public static long computeCombatIndex(Map<AttributeTypeEnum, Attribute> attributeMap) {
    long combatIndex = 0;
    Attribute hpAttribute = attributeMap.get(AttributeTypeEnum.HP);
    Attribute mpAttribute = attributeMap.get(AttributeTypeEnum.MP);
    Attribute attackAttribute = attributeMap.get(AttributeTypeEnum.ATTACK);
    Attribute defenseAttribute = attributeMap.get(AttributeTypeEnum.DEFENSE);
    /** 计算公式：战力 = 血量*50 + 魔法值*100 + 攻击力*300 + 防御力*200 */
    combatIndex =
        hpAttribute.getValue() * 50
            + mpAttribute.getValue() * 100
            + attackAttribute.getValue() * 300
            + defenseAttribute.getValue() * 200;
    return combatIndex;
  }

  /**
   * 计算属性
   *
   * @param attributeMap
   * @param attribute
   */
  public static void computeAttribute(
      Map<AttributeTypeEnum, Attribute> attributeMap, Attribute attribute) {
    Attribute resultAttribute = attributeMap.get(attribute.getType());
    resultAttribute.setValue(resultAttribute.getValue() + attribute.getValue());
  }

  /**
   * 计算属性加成
   *
   * @param attributeMap
   */
  public static void computeAttributePercentage(Map<AttributeTypeEnum, Attribute> attributeMap) {
    for (Map.Entry<AttributeTypeEnum, Attribute> attributeEntry : attributeMap.entrySet()) {
      Attribute attribute = attributeEntry.getValue();
      if (attribute == null) {
        continue;
      }
      // 获取所受到影响的百分比值
      AttributeTypeEnum[] percentageAttributes = attribute.getType().getPercentageAttributes();
      long percentage = 0;
      if (percentageAttributes == null) {
        continue;
      }
      for (AttributeTypeEnum attributeTypeEnum : percentageAttributes) {
        if (attributeMap.get(attributeTypeEnum) != null) {
          percentage += attributeMap.get(attributeTypeEnum).getValue();
        }
      }
      // 属性加成
      attribute.setValue(attribute.getValue() * (1 + percentage / SystemConstant.TEN_THOUSAND));
    }
  }
}
