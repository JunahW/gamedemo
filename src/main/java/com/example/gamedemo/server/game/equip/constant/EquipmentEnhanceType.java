package com.example.gamedemo.server.game.equip.constant;

import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;

/**
 * @author: wengj
 * @date: 2019/6/11
 * @description: 地图增强类型
 */
public enum EquipmentEnhanceType implements AttributeModelId {
    /**
     * 武器
     */
    WEAPON_ENHANCE(0) {
        @Override
        public String getModelName() {
            return "WEAPON_ENHANCE";
        }
    },

    /**
     * 衣服
     */
    CLOTH_ENHANCE(1) {
        @Override
        public String getModelName() {
            return "CLOTH_ENHANCE";
        }
    },

    /**
     * 头盔
     */
    HAT_ENHANCE(2) {
        @Override
        public String getModelName() {
            return "HAT_ENHANCE";
        }
    },

    /**
     * 战靴
     */
    SHOE_ENHANCE(3) {
        @Override
        public String getModelName() {
            return "SHOE_ENHANCE";
        }
    };

    /**
     * 装备类型
     */
    private int position;

    EquipmentEnhanceType(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 获取装备类型
     *
     * @param id
     * @return
     */
    public static EquipmentEnhanceType getEquipmentEnhanceTypeByPosition(int id) {
        for (EquipmentEnhanceType equipmentType : EquipmentEnhanceType.values()) {
            if (equipmentType.position == id) {
                return equipmentType;
            }
        }
        return null;
    }
}
