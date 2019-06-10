package com.example.gamedemo.server.game.attribute.constant;

/**
 * @author: wengj
 * @date: 2019/6/3
 * @description:属性类型
 */
public enum AttributeTypeEnum {
    /**
     * 生命值
     */
    HP("HP", "生命值"),

    /**
     * 魔法值
     */
    MP("MP", "魔法值"),

    /**
     * 攻击力
     */
    ATTACK("ATTACK", "攻击力"),

    /**
     * 防御力
     */
    DEFENSE("DEFENSE", "防御力"),

    /**
     * 攻击力下限
     */
    ATTACK_LOWER("ATTACK_LOWER", "攻击力下限"),

    /**
     * 攻击力上限
     */
    ATTACK_UPPER("ATTACK_UPPER", "攻击力上限"),

    /**
     * 防御力下限
     */
    DEFENSE_LOWER("DEFENSE_LOWER", "防御力下限"),

    /**
     * 防御力上限
     */
    DEFENSE_UPPER("DEFENSE_UPPER", "防御力上限"),

    /**
     * 攻击加成
     */
    ATTACK_PERCENTAGE("", "攻击加成"),


    /**
     * 防御加成
     */
    DEFENSE_PERCENTAGE("", "防御加成");


    /**
     * 属性id
     */
    private String name;

    /**
     * 属性描述
     */
    private String description;


    AttributeTypeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;

    }

    /**
     * 通过name获取对应的枚举
     *
     * @param type
     * @return
     */
    public static AttributeTypeEnum getAttributeTypeEnumByType(String type) {
        for (AttributeTypeEnum attributeTypeEnum : AttributeTypeEnum.values()) {
            if (attributeTypeEnum.getName().equals(type)) {
                return attributeTypeEnum;
            }
        }
        return null;
    }
}
