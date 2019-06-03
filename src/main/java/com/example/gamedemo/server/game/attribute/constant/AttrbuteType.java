package com.example.gamedemo.server.game.attribute.constant;

/**
 * @author: wengj
 * @date: 2019/6/3
 * @description:属性类型
 */
public enum AttrbuteType {
    /**
     * 生命值
     */
    HP(0, "生命值"),

    /**
     * 魔法值
     */
    MP(1, "魔法值"),

    /**
     * 攻击力
     */
    ATTACK(2, "攻击力"),

    /**
     * 防御力
     */
    DEFENSE(3, "防御力"),

    /**
     * 攻击力加成
     */
    ATTACKUPPER(4, "攻击力加成"),

    /**
     * 防御力加成
     */
    DEFENSEUPPER(5, "防御力加成");

    /**
     * 属性id
     */
    private int attrId;

    /**
     * 属性描述
     */
    private String description;


    AttrbuteType(int attrId, String description) {
        this.attrId = attrId;
        this.description = description;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttrId() {
        return attrId;
    }

    public String getDescription() {
        return description;
    }
}
