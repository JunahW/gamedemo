package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttrbuteType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description：属性容器
 * @date 2019/6/3
 */
public abstract class CreatureAttributeContainer<T> {

    /**
     * 属性容器所属者
     */
    private T owner;
    /**
     * 属性容器
     */
    private ConcurrentMap<AttrbuteType, Attribute> attributeMap = new ConcurrentHashMap<>();
}
