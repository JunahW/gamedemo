package com.example.gamedemo.server.game.bag.packet;

/**
 * @author wengj
 * @description：获取道具数量
 * @date 2019/6/11
 */
public class SM_GetItemQuantity {
    private int itemQuantity;

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public SM_GetItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public static SM_GetItemQuantity valueOf(int itemQuantity) {
        return new SM_GetItemQuantity(itemQuantity);
    }
}
