package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description:属性集合
 * @date 2019/6/5
 */
public class AttributeSet {
  /** 属性集合 */
  private Map<AttributeTypeEnum, Attribute> attributeMap = new HashMap<>();

  public Map<AttributeTypeEnum, Attribute> getAttributeMap() {
    return attributeMap;
  }

  public void setAttributeMap(Map<AttributeTypeEnum, Attribute> attributeMap) {
    this.attributeMap = attributeMap;
  }
}
