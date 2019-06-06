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

    /**
     * 属性容器所属者
     */
    private T owner;
    /**
     * 各个属性容器
     */
    private ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap = new ConcurrentHashMap<>();

    /**
     * 不同模块的属性容器
     */
    private ConcurrentMap<AttributeModelId, AttributeSet> modelAttributeListMap = new ConcurrentHashMap<>();

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

    /**
     * 计算属性值
     */
    public void compute() {
        attributeMap.clear();
        for (Map.Entry<AttributeModelId, AttributeSet> entry : modelAttributeListMap.entrySet()) {
            AttributeSet attributeSet = entry.getValue();

            for (Map.Entry<AttributeTypeEnum, Attribute> attributeEntry : attributeSet.getAttributeMap().entrySet()) {
                Attribute attribute = attributeEntry.getValue();
                if (attributeMap.containsKey(attribute.getType())) {
                    long value = attributeMap.get(attribute.getType()).getValue();
                    //新建有一个对象，不能修改list的内容
                    Attribute newAttribute = new Attribute();
                    newAttribute.setValue(value + attribute.getValue());
                    newAttribute.setType(attribute.getType());

                    //计算属性
                    attributeMap.put(newAttribute.getType(), newAttribute);
                } else {
                    attributeMap.put(attribute.getType(), attribute);
                }

            }
        }


    }

    public void putAndComputeAttributes(AttributeModelId attributeModelId, List<Attribute> attributeList) {
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
        //不存在
        if (attributeSet == null) {
            attributeSet = new AttributeSet();
            for (Attribute attribute : attributeList) {
                attributeSet.getAttributeMap().put(attribute.getType(), attribute);
            }
            modelAttributeListMap.put(attributeModelId, attributeSet);
            return;
        }

        modelAttributeListMap.get(attributeModelId).getAttributeMap().clear();

        for (Attribute attribute : attributeList) {
            modelAttributeListMap.get(attributeModelId).getAttributeMap().put(attribute.getType(), attribute);
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

    public ConcurrentMap<AttributeTypeEnum, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public ConcurrentMap<AttributeModelId, AttributeSet> getModelAttributeListMap() {
        return modelAttributeListMap;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public void setAttributeMap(ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public void setModelAttributeListMap(ConcurrentMap<AttributeModelId, AttributeSet> modelAttributeListMap) {
        this.modelAttributeListMap = modelAttributeListMap;
    }
}
