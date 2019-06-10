package com.example.gamedemo.server.game.bag.model;

import com.example.gamedemo.server.game.base.gameobject.GameObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author wengj
 * @description：
 * @date 2019/5/30
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
public class AbstractItem extends GameObject implements Cloneable {
    /**
     * 道具的资源id
     */
    private int itemResourceId;

    /**
     * 道具名称
     */
    private String itemName;

    /**
     * 道具数量
     */
    private int quantity;


    public int getItemResourceId() {
        return itemResourceId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setItemResourceId(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "AbstractItem{" +
                "itemResourceId='" + itemResourceId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", id=" + super.getObjectId() +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
