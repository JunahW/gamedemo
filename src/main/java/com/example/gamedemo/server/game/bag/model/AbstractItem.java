package com.example.gamedemo.server.game.bag.model;

import com.example.gamedemo.server.game.base.gameobject.GameObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author wengj
 * @description：
 * @date 2019/5/30
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
public class AbstractItem extends GameObject {
    /**
     * 道具的资源id
     */
    private String itemResourceId;

    /**
     * 道具名称
     */
    private String itemName;

    /**
     * 道具数量
     */
    private int quantity;

    /**
     * 道具类型
     */
    private int itemType;


    public String getItemResourceId() {
        return itemResourceId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getItemType() {
        return itemType;
    }


    public void setItemResourceId(String itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "AbstractItem{" +
                "itemResourceId='" + itemResourceId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", itemType=" + itemType +
                ", id=" + super.getObjectId() +
                '}';
    }
}
