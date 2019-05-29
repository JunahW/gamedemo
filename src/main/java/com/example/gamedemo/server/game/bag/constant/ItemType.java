package com.example.gamedemo.server.game.bag.constant;

import com.example.gamedemo.server.game.bag.model.StorageItem;

/**
 * @author: wengj
 * @date: 2019/5/29
 * @description: 道具类型
 */
public enum ItemType {

    /**
     * 背包物品
     */
    CommonItem(0, StorageItem.class),

    OneItem(1, StorageItem.class),

    TwoItem(2, StorageItem.class),

    ThreeItem(3, StorageItem.class),
    ;

    ItemType(int id, Class bagItemClazz) {
        this.id = id;
        this.itemClazz = bagItemClazz;
    }

    /**
     * 道具类型id
     */
    private int id;

    /**
     * 道具实例的class
     */
    private Class<? extends StorageItem> itemClazz;

    public int getId() {
        return id;
    }

    public Class<? extends StorageItem> getItemClazz() {
        return itemClazz;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemClazz(Class<? extends StorageItem> itemClazz) {
        this.itemClazz = itemClazz;
    }

    /**
     * 创建背包道具
     *
     * @return
     */
    public static StorageItem create(int id) {

        ItemType[] values = values();
        StorageItem storageItem = null;
        for (ItemType itemType : values) {
            if (itemType.getId() == id) {
                try {
                    storageItem = itemType.getItemClazz().newInstance();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("生成道具失败" + itemType.getItemClazz().getName());
                }
            }
        }
        return storageItem;
    }
}
