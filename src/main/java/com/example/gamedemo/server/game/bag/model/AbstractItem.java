package com.example.gamedemo.server.game.bag.model;

import com.example.gamedemo.server.game.base.gameobject.GameObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author wengj
 * @description：
 * @date 2019/5/30
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
/*@JsonSubTypes({@JsonSubTypes.Type(value = CommonItem.class, name = "CommonItem")
        , @JsonSubTypes.Type(value = EquipItem.class, name = "EquipItem")
        , @JsonSubTypes.Type(value = GemstoneItem.class, name = "GemstoneItem")
        , @JsonSubTypes.Type(value = MedicineItem.class, name = "MedicineItem")})*/
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
    private int quanlity;

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

    public int getQuanlity() {
        return quanlity;
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

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "AbstractItem{" +
                "itemResourceId='" + itemResourceId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quanlity=" + quanlity +
                ", itemType=" + itemType +
                ", id=" + super.getObjectId() +
                '}';
    }
}
