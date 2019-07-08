package com.example.gamedemo.server.game.attribute.computer;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.Map;

/**
 * @author wengj
 * @description：属性计算器接口
 * @date 2019/7/8
 */
public interface IAttributeComputer {
  /**
   * 计算属性
   *
   * @param attributeType
   * @param attributeMap
   * @return
   */
  long compute(AttributeTypeEnum attributeType, Map<AttributeTypeEnum, Attribute> attributeMap);
}
