package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.common.utils.FormulaUtils;
import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description：属性容器
 * @date 2019/6/3
 */
public abstract class AbstractAttributeContainer<T> {

  /** 属性容器所属者 */
  @JsonIgnore protected T owner;
  /** 各个属性容器 */
  private Map<AttributeTypeEnum, Attribute> attributeMap = new HashMap<>();

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
    for (Map.Entry<AttributeModelId, AttributeSet> entry : modelAttributeListMap.entrySet()) {
      AttributeSet attributeSet = entry.getValue();
      // 累加各个属性的值
      for (Map.Entry<AttributeTypeEnum, Attribute> attributeEntry :
          attributeSet.getAttributeMap().entrySet()) {
        Attribute attribute = attributeEntry.getValue();
        if (attributeMap.containsKey(attribute.getType())) {
          // 计算同个属性计算后的值
          FormulaUtils.computeAttribute(attributeMap, attribute);
        } else {
          attributeMap.put(
              attribute.getType(), Attribute.valueof(attribute.getType(), attribute.getValue()));
        }
      }
    }
    // 计算最终的值，包括加成
    FormulaUtils.computeAttributePercentage(attributeMap);
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
    Attribute attribute = attributeMap.get(attributeType);
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

  public Map<AttributeTypeEnum, Attribute> getAttributeMap() {
    return attributeMap;
  }

  public void setAttributeMap(ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap) {
    this.attributeMap = attributeMap;
  }

  public Map<AttributeModelId, AttributeSet> getModelAttributeListMap() {
    return modelAttributeListMap;
  }

  public void setModelAttributeListMap(
      ConcurrentMap<AttributeModelId, AttributeSet> modelAttributeListMap) {
    this.modelAttributeListMap = modelAttributeListMap;
  }
}
