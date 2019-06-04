package com.example.gamedemo.server.game.bag.packet;

/**
 * @author wengj
 * @description:使用道具信息
 * @date 2019/5/29
 */
public class CM_UseItemByResourceId {
    private String resourceId;

    private int quantity;

    public String getResourceId() {
        return resourceId;
    }

    public int getQuantity() {
        return quantity;
    }
}
