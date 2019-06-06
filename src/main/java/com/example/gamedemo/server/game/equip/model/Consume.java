package com.example.gamedemo.server.game.equip.model;

/**
 * @author wengj
 * @description：消耗实体
 * @date 2019/6/6
 */
public class Consume {

    /**
     * 物品id
     */
    private String itemId;

    /**
     * 数量
     */
    private int quantity;

    public String getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
