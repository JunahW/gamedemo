package com.example.gamedemo.server.game.attribute.computer.impl;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.computer.IAttributeComputer;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.Map;

/**
 * @author wengj
 * @description：普通计算器
 * @date 2019/7/8
 */
public class GeneralAttributeComputer implements IAttributeComputer {
  @Override
  public long compute(
      AttributeTypeEnum attributeType, Map<AttributeTypeEnum, Attribute> attributeMap) {
    Attribute attribute = attributeMap.get(attributeType);
    if (attribute != null) {
      // 获取所受到影响的百分比值
      AttributeTypeEnum[] percentageAttributes = attribute.getType().getPercentageAttributes();
      long percentage = 0;
      for (AttributeTypeEnum attributeTypeEnum : percentageAttributes) {
        if (attributeMap.get(attributeTypeEnum) != null) {
          percentage += attributeMap.get(attributeTypeEnum).getValue();
        }
      }
      // 属性加成
      return attribute.getValue() * (1 + percentage / SystemConstant.TEN_THOUSAND);
    }
    return 0;
  }
}
