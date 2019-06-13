package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description:属性集合
 * @date 2019/6/5
 */
public class AttributeSet {
    /**
     * 属性集合
     */
    private ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap = new ConcurrentHashMap<>();

    public ConcurrentMap<AttributeTypeEnum, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
