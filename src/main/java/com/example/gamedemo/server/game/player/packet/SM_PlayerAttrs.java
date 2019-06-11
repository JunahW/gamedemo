package com.example.gamedemo.server.game.player.packet;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wengj
 * @description
 * @date 2019/6/11
 */
public class SM_PlayerAttrs {
    List<Attribute> attributes;


    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public SM_PlayerAttrs(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static SM_PlayerAttrs valueOf(ConcurrentMap<AttributeTypeEnum, Attribute> attributeMap) {
        LinkedList<Attribute> attributes = new LinkedList<>();
        for (Map.Entry<AttributeTypeEnum, Attribute> entry : attributeMap.entrySet()) {
            Attribute attribute = entry.getValue();
            if (attribute != null) {
                attributes.add(attribute);
            }
        }
        return new SM_PlayerAttrs(attributes);
    }
}
