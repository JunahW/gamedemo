package com.example.gamedemo.server.common.utils;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.concurrent.ConcurrentMap;

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
  public static long computeCombatIndex(ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap) {
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
}
