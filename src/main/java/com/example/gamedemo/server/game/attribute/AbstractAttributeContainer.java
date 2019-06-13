package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description：属性容器
 * @date 2019/6/3
 */
public abstract class AbstractAttributeContainer<T> {

  /** 属性容器所属者 */
  protected T owner;
  /** 各个属性容器 */
  private ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap = new ConcurrentHashMap<>();

  /** 不同模块的属性容器 */
  private ConcurrentMap<AttributeModelId, AttributeSet> modelAttributeListMap =
      new ConcurrentHashMap<>();

  public AbstractAttributeContainer(T owner) {
    this.owner = owner;
  }

  public AbstractAttributeContainer() {}

  /** 计算玩家战力 */
  public abstract void computeCombatIndex();

  /**
   * 获取属性的值
   *
   * @param attributeType
   * @return
   */
  public double getAttributeValue(String attributeType) {
    Attribute attribute = attributeMap.get(attributeType);
    return attribute.getValue();
  }

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
          long value = attributeMap.get(attribute.getType()).getValue();
          // 新建有一个对象，不能修改list的内容
          attributeMap.put(
              attribute.getType(),
              Attribute.valueof(attribute.getType(), attribute.getValue() + value));
        } else {
          attributeMap.put(
              attribute.getType(), Attribute.valueof(attribute.getType(), attribute.getValue()));
        }
      }
    }
    // 计算最终的值，包括加成
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
      attribute.setValue(attribute.getValue() * (100 + percentage) / 100);
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

  public T getOwner() {
    return owner;
  }

  public void setOwner(T owner) {
    this.owner = owner;
  }

  public ConcurrentMap<AttributeTypeEnum, Attribute> getAttributeMap() {
    return attributeMap;
  }

  public void setAttributeMap(ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap) {
    this.attributeMap = attributeMap;
  }

  public ConcurrentMap<AttributeModelId, AttributeSet> getModelAttributeListMap() {
    return modelAttributeListMap;
  }

  public void setModelAttributeListMap(
      ConcurrentMap<AttributeModelId, AttributeSet> modelAttributeListMap) {
    this.modelAttributeListMap = modelAttributeListMap;
  }
}
