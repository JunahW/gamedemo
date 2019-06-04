package com.example.gamedemo.server.game.attribute;


import java.util.List;
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
    private ConcurrentMap<String, Attribute> attributeMap = new ConcurrentHashMap<>();

    /**
     * 不同级别属性容器
     */
    private ConcurrentMap<String, List<Attribute>> modelAttributeListMap = new ConcurrentHashMap<>();

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


    public T getOwner() {
        return owner;
    }

    public ConcurrentMap<String, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public ConcurrentMap<String, List<Attribute>> getModelAttributeListMap() {
        return modelAttributeListMap;
    }


    public void setOwner(T owner) {
        this.owner = owner;
    }

    public void setAttributeMap(ConcurrentMap<String, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public void setModelAttributeListMap(ConcurrentMap<String, List<Attribute>> modelAttributeListMap) {
        this.modelAttributeListMap = modelAttributeListMap;
    }


}
