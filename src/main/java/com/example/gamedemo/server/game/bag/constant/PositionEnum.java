package com.example.gamedemo.server.game.bag.constant;

import com.example.gamedemo.server.game.attribute.constant.AttributeModelId;

/**
 * @author: wengj
 * @date: 2019/6/5
 * @description: 部位枚举
 */
public enum PositionEnum implements AttributeModelId {
    HEAD() {
        @Override
        public String getModelName() {
            return "HEAD";
        }
    };

}
