package com.example.gamedemo.server.game.attribute.computer.impl;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.computer.IAttributeComputer;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.Map;

/**
 * @author wengj
 * @description：原始计算器
 * @date 2019/7/8
 */
public class DefaultAttributeComputer implements IAttributeComputer {
  @Override
  public long compute(
      AttributeTypeEnum attributeType, Map<AttributeTypeEnum, Attribute> attributeMap) {
    return attributeMap.get(attributeType).getValue();
  }
}
