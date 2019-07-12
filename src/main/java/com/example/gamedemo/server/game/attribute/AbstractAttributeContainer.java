package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.attribute.utils.AttributeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wengj
 * @description：属性容器
 * @date 2019/6/3
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
public abstract class AbstractAttributeContainer<T> {

  /** 属性容器所属者 */
  @JsonIgnore protected T owner;

  /** 最终属性容器 */
  private Map<AttributeTypeEnum, Attribute> finalAttributeMap = new HashMap<>();

  /** 各个属性容器 */
  @JsonIgnore private Map<AttributeTypeEnum, Attribute> attributeMap = new HashMap<>();

  /** 不同模块的属性容器 */
  @JsonIgnore private Map<AttributeModelId, AttributeSet> modelAttributeListMap = new HashMap<>();

  public AbstractAttributeContainer(T owner) {
    this.owner = owner;
  }

  public AbstractAttributeContainer() {}

  /** 计算玩家战力 */
  public abstract void computeCombatIndex();

  /** 计算属性值 */
  public void compute() {
    attributeMap.clear();
    AttributeUtils.accumulateModeAttribute2AttributeMap(modelAttributeListMap, attributeMap);
    // 计算最终的值，包括加成
    for (Map.Entry<AttributeTypeEnum, Attribute> attributeEntry : attributeMap.entrySet()) {
      AttributeTypeEnum attributeType = attributeEntry.getKey();
      // 最终属性值
      long finalValue = attributeType.getAttributeComputer().compute(attributeType, attributeMap);

      Attribute finalAttribute = finalAttributeMap.get(attributeType);
      if (finalAttribute == null) {
        finalAttributeMap.put(attributeType, Attribute.valueof(attributeType, finalValue));
      } else {
        finalAttribute.setValue(finalValue);
      }
    }
    // 计算战力
    computeCombatIndex();
  }

  public void putAndComputeAttributes(
      AttributeModelId attributeModelId, List<Attribute> attributeList) {
    putAttributeSet(attributeModelId, attributeList);
    compute();
  }

  /**
   * 新增模块属性
   *
   * @param attributeModelId
   * @param attributeList
   */
  public void putAttributeSet(AttributeModelId attributeModelId, List<Attribute> attributeList) {
    if (attributeList == null) {
      throw new NullPointerException();
    }
    AttributeSet attributeSet = modelAttributeListMap.get(attributeModelId);
    // 不存在
    if (attributeSet == null) {
      attributeSet = new AttributeSet();
      for (Attribute attribute : attributeList) {
        attributeSet
            .getAttributeMap()
            .put(attribute.getType(), Attribute.valueof(attribute.getType(), attribute.getValue()));
      }
      modelAttributeListMap.put(attributeModelId, attributeSet);
      return;
    }

    modelAttributeListMap.get(attributeModelId).getAttributeMap().clear();

    for (Attribute attribute : attributeList) {
      modelAttributeListMap
          .get(attributeModelId)
          .getAttributeMap()
          .put(attribute.getType(), Attribute.valueof(attribute.getType(), attribute.getValue()));
    }
  }

  /**
   * 移除某模块属性
   *
   * @param attributeModelId
   */
  public void removeAttributeSet(AttributeModelId attributeModelId) {
    modelAttributeListMap.remove(attributeModelId);
  }

  /**
   * 移除某一模块的属性并重新计算
   *
   * @param attributeModelId
   */
  public void removeAndComputeAttributeSet(AttributeModelId attributeModelId) {
    modelAttributeListMap.remove(attributeModelId);
    compute();
  }

  /**
   * 获取属性的值
   *
   * @param attributeType
   * @return
   */
  public Long getAttributeValue(AttributeTypeEnum attributeType) {
    Attribute attribute = finalAttributeMap.get(attributeType);
    if (attribute == null) {
      return 0L;
    }
    return attribute.getValue();
  }

  @JsonIgnore
  public T getOwner() {
    return owner;
  }

  @JsonIgnore
  public void setOwner(T owner) {
    this.owner = owner;
  }

  public Map<AttributeTypeEnum, Attribute> getFinalAttributeMap() {
    return finalAttributeMap;
  }

  public void setFinalAttributeMap(Map<AttributeTypeEnum, Attribute> finalAttributeMap) {
    this.finalAttributeMap = finalAttributeMap;
  }

  public Map<AttributeModelId, AttributeSet> getModelAttributeListMap() {
    return modelAttributeListMap;
  }

  public void setModelAttributeListMap(Map<AttributeModelId, AttributeSet> modelAttributeListMap) {
    this.modelAttributeListMap = modelAttributeListMap;
  }

  public Map<AttributeTypeEnum, Attribute> getAttributeMap() {
    return attributeMap;
  }

  public void setAttributeMap(Map<AttributeTypeEnum, Attribute> attributeMap) {
    this.attributeMap = attributeMap;
  }
}
