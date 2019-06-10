package com.example.gamedemo.server.game.equip.storage;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.EquipItem;
import com.example.gamedemo.server.game.equip.constant.EquipmentType;
import com.example.gamedemo.server.game.equip.model.Slot;

import java.util.Arrays;


/**
 * @author: wengj
 * @date: 2019/5/30
 * @description: 装备栏
 */
public class EquipStorage {

    /**
     * 卡槽属性
     */
    private Slot[] slots = new Slot[EquipmentType.values().length];


    @Override
    public String toString() {
        return "EquipStorage{" +
                "slots=" + Arrays.toString(slots) +
                '}';
    }

    public EquipStorage() {
        for (int i = 0; i < EquipmentType.values().length; i++) {
            slots[i] = new Slot();
        }
    }

    /**
     * 穿装备
     *
     * @param equipItem
     * @return 返回卸下的装备
     */
    public AbstractItem equip(EquipItem equipItem) {
        int position = SpringContext.getItemService().getItemResourceByItemResourceId(equipItem.getItemResourceId()).getPosition();
        //AbstractItem unEquipItem = equipItems[position];

        if (slots[position] == null) {
            slots[position] = new Slot();
        }
        EquipItem unEquipItem = slots[position].getEquipItem();
        slots[position].setEquipItem(equipItem);
        return unEquipItem;
    }

    /**
     * 卸载对应位置的装备
     *
     * @param position
     * @return
     */
    public AbstractItem unEquip(int position) {
        Slot slot = slots[position];
        EquipItem equipItem = slot.getEquipItem();
        slot.setEquipItem(null);
        return equipItem;
    }

    /**
     * 获取装备
     *
     * @param guid
     * @return
     */
    public AbstractItem getEquipmentByGuid(long guid) {
        for (Slot slot : slots) {
            if (slot != null) {
                EquipItem equipItem = slot.getEquipItem();
                if (null != equipItem) {
                    if (equipItem.getObjectId() == guid) {
                        return equipItem;
                    }
                }
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
        Slot slot = slots[position];
        if (null == slot) {
            return null;
        }
        return slots[position].getEquipItem();
    }

    public Slot getSlotByPosision(int position) {
        if (0 > position || position >= EquipmentType.values().length) {
            return null;
        }
        return slots[position];
    }

    public Slot[] getSlots() {
        return slots;
    }

    public void setSlots(Slot[] slots) {
        this.slots = slots;
    }
}
