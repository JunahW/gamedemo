package com.example.gamedemo.server.game.attribute.constant;

/**
 * @author: wengj
 * @date: 2019/6/5
 * @description: 模块id枚举
 */
public enum AttributeModelIdEnum implements AttributeModelId {
    /**
     * 基础属性
     */
    BASE {
        @Override
        public String getModelName() {
            return "BASE";
        }
    },

    /**
     * 装备
     */
    EQUIPMENT {
        @Override
        public String getModelName() {
            return "EQUIPMENT";
        }
    };


}
