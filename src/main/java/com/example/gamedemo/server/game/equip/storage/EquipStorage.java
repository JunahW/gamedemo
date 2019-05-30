package com.example.gamedemo.server.game.equip.storage;

import com.example.gamedemo.server.game.bag.model.EquipItem;
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
    private EquipItem[] equipItems = new EquipItem[size];


    public Integer getSize() {
        return size;
    }

    public EquipItem[] getEquipItems() {
        return equipItems;
    }


    @Override
    public String toString() {
        return "EquipStorage{" +
                "size=" + size +
                ", equipItems=" + Arrays.toString(equipItems) +
                '}';
    }
}
