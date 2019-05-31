package com.example.gamedemo.server.game.equip.storage;

import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.equip.constant.EquipmentType;

import java.util.Arrays;


/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备栏
 */
public class EquipStorage {

    /**
     * 容量
     */
    private final Integer size = EquipmentType.values().length;

    /**
     * 物品
     */
    private AbstractItem[] equipItems = new AbstractItem[size];


    public Integer getSize() {
        return size;
    }

    public AbstractItem[] getEquipItems() {
        return equipItems;
    }

    public void setEquipItems(AbstractItem[] equipItems) {
        this.equipItems = equipItems;
    }

    @Override
    public String toString() {
        return "EquipStorage{" +
                "size=" + size +
                ", equipItems=" + Arrays.toString(equipItems) +
                '}';
    }


    /**
     * 穿装备
     *
     * @param equipItem
     * @param position
     * @return 返回卸下的装备
     */
    public AbstractItem equip(AbstractItem equipItem, int position) {
        AbstractItem unEquipItem = equipItems[position];
        equipItems[position] = equipItem;
        return unEquipItem;
    }

    /**
     * 卸载对应位置的装备
     *
     * @param position
     * @return
     */
    public AbstractItem unEquip(int position) {
        AbstractItem equipItem = equipItems[position];
        return equipItem;
    }

    /**
     * 获取装备
     *
     * @param guid
     * @return
     */
    public AbstractItem getEquipmentByGuid(long guid) {
        for (AbstractItem item : equipItems) {
            if (item != null && item.getObjectId() == guid) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取部位装备信息
     *
     * @param position
     * @return
     */
    public AbstractItem getEquipmentByPosition(int position) {
        if (position >= size) {
            return null;
        }
        return equipItems[position];
    }
}
