package com.example.gamedemo.server.game.attribute;

import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;

/**
 * @author wengj
 * @description：属性
 * @date 2019/6/3
 */
public class Attribute {

    /**
     * 属性类型
     */
    private AttributeTypeEnum type;

    /**
     * 属性值
     */
    private long value;

    public AttributeTypeEnum getType() {
        return type;
    }

    public long getValue() {
        return value;
    }

    public void setType(AttributeTypeEnum type) {
        this.type = type;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Attribute() {
    }

    public Attribute(String type, long value) {
        this.type = AttributeTypeEnum.getAttributeTypeEnumByType(type);
        this.value = value;
    }

    public Attribute(AttributeTypeEnum type, long value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

    public static Attribute valueof(AttributeTypeEnum type, long value) {
        Attribute attribute = new Attribute(type, value);
        return attribute;
    }
}
