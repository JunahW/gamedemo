package com.example.gamedemo.server.game.bag.model;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.resource.ItemResource;

import java.util.Arrays;

/**
 * @author wengj
 * @description 背包
 * @date 2019/5/21
 */
public class ItemStorage {

    /**
     * 背包容量
     */
    private Integer size = 50;

    /**
     * 背包物品
     */
    private StorageItem[] storageItems = new StorageItem[50];

    /**
     * 已有数量
     */
    private int num;


    public Integer getSize() {
        return size;
    }

    public StorageItem[] getStorageItems() {
        return storageItems;
    }

    public int getNum() {
        return num;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setStorageItems(StorageItem[] storageItems) {
        this.storageItems = storageItems;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 通过guid获取道具
     *
     * @param guid
     * @return
     */
    public StorageItem getStorageItemByObjectId(long guid) {
        for (int i = 0; i < storageItems.length; i++) {
            if (storageItems[i] != null) {
                if (storageItems[i].getObjectId() == guid) {
                    return storageItems[i];
                }
            }

        }
        return null;
    }

    /**
     * 通过guid减少道具数量
     *
     * @param guid
     * @param quanlity
     */
    public boolean reduceStorageItemByObjectId(long guid, int quanlity) {
        boolean isReduce = false;
        for (int i = 0; i < storageItems.length; i++) {
            if (storageItems[i] != null) {
                if (storageItems[i].getQuanlity() >= quanlity) {
                    int size = storageItems[i].getQuanlity() - quanlity;
                    if (size > 0) {
                        storageItems[i].setQuanlity(size);
                    } else {
                        storageItems[i] = null;
                    }
                    isReduce = true;
                    break;
                }
            }
        }
        return isReduce;
    }

    /**
     * 添加道具
     *
     * @param item
     * @return
     */
    public boolean addStorageItem(StorageItem item) {
        String itemResourceId = item.getItemResourceId();
        ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);
        //是否加入背包
        boolean isAdd = false;
        //道具可堆叠
        if (itemResource.getOverLimit() > 1) {
            for (StorageItem storageItem : storageItems) {
                if (storageItem != null) {
                    //可堆叠
                    //已经存在
                    if (item.getItemResourceId() == storageItem.getItemResourceId()) {
                        storageItem.setQuanlity(storageItem.getQuanlity() + 1);
                        isAdd = true;
                        break;
                    }
                }
            }
        }
        //不可堆叠 或者背包不存在
        if (!isAdd) {
            for (int i = 0; i < storageItems.length; i++) {
                if (storageItems[i] == null) {
                    storageItems[i] = item;
                    isAdd = true;
                    break;
                }
            }
        }
        return isAdd;

    }

    @Override
    public String toString() {
        return "ItemStorage{" +
                "size=" + size +
                ", storageItems=" + Arrays.toString(storageItems) +
                ", num=" + num +
                '}';
    }
}
