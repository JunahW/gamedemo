package com.example.gamedemo.server.game.equip.constant;

/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备类型
 */
public enum EquipmentType {
    /**
     * 武器
     */
    WEAPON(0),

    /**
     * 衣服
     */
    CLOTH(1),

    /**
     * 头盔
     */
    HAT(2),

    /**
     * 战靴
     */
    SHOE(3);

    /**
     * 装备类型
     */
    private int equipmentType;

    EquipmentType(int equipmentType) {
        this.equipmentType = equipmentType;
    }

    public int getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(int equipmentType) {
        this.equipmentType = equipmentType;
    }

    /**
     * 获取装备类型
     *
     * @param id
     * @return
     */
    public static EquipmentType getEquipmentTypeId(int id) {
        for (EquipmentType equipmentType : EquipmentType.values()) {
            if (equipmentType.equipmentType == id) {
                return equipmentType;
            }
        }
        return null;
    }
}
