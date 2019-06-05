package com.example.gamedemo.server.game.equip.storage;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.equip.constant.EquipmentType;
import com.example.gamedemo.server.game.equip.model.SlotAttribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备栏
 */
public class EquipStorage {

    /**
     * 物品
     */
    private AbstractItem[] equipItems = new AbstractItem[EquipmentType.values().length];

    /**
     * 卡槽属性
     */
    private Map<Integer, SlotAttribute> slotAttributeMap = new HashMap<>(EquipmentType.values().length);


    public AbstractItem[] getEquipItems() {
        return equipItems;
    }

    public void setEquipItems(AbstractItem[] equipItems) {
        this.equipItems = equipItems;
    }

    @Override
    public String toString() {
        return "EquipStorage{" +
                "equipItems=" + Arrays.toString(equipItems) +
                ", slotAttributeMap=" + slotAttributeMap +
                '}';
    }

    /**
     * 穿装备
     *
     * @param equipItem
     * @return 返回卸下的装备
     */
    public AbstractItem equip(EquipItem equipItem) {
        int position = SpringContext.getItemService().getItemResourceByItemResourceId(equipItem.getItemResourceId()).getPosition();
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
        if (position >= EquipmentType.values().length) {
            return null;
        }
        return equipItems[position];
    }
}
