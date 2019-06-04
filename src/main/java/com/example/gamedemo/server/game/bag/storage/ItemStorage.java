package com.example.gamedemo.server.game.bag.storage;

import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
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
    private AbstractItem[] abstractItems = new AbstractItem[50];



    public Integer getSize() {
        return size;
    }

    public void setAbstractItems(AbstractItem[] abstractItems) {
        this.abstractItems = abstractItems;
    }


    public void setSize(Integer size) {
        this.size = size;
    }

    public AbstractItem[] getAbstractItems() {
        return abstractItems;
    }


    /**
     * 通过guid获取道具
     *
     * @param guid
     * @return
     */
    public AbstractItem getStorageItemByObjectId(long guid) {
        for (int i = 0; i < abstractItems.length; i++) {
            if (abstractItems[i] != null) {
                if (abstractItems[i].getObjectId() == guid) {
                    return abstractItems[i];
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
        for (int i = 0; i < abstractItems.length; i++) {
            if (abstractItems[i] != null) {
                if (abstractItems[i].getQuantity() >= quanlity) {
                    int size = abstractItems[i].getQuantity() - quanlity;
                    if (size > 0) {
                        abstractItems[i].setQuantity(size);
                    } else {
                        abstractItems[i] = null;
                    }
                    isReduce = true;
                    break;
                }
            }
        }
        return isReduce;
    }

    /**
     * 通过itemResourceId减少道具数量
     *
     * @param itemResourceId
     * @param quantity
     */
    public boolean reduceStorageItemByItemResourceId(String itemResourceId, int quantity) {
        //检查数量是否够消耗
        int storageQuantity = 0;
        for (int i = 0; i < abstractItems.length; i++) {
            if (abstractItems[i] != null) {
                if (abstractItems[i].getItemResourceId().equals(itemResourceId)) {
                    storageQuantity += abstractItems[i].getQuantity();
                    if (storageQuantity >= quantity) {
                        break;
                    }
                }
            }
        }

        if (storageQuantity < quantity) {
            return false;
        }
        //消耗道具
        for (int i = 0; i < abstractItems.length; i++) {
            if (abstractItems[i] != null) {
                if (abstractItems[i].getItemResourceId().equals(itemResourceId)) {
                    quantity -= abstractItems[i].getQuantity();
                    if (quantity < 0) {
                        abstractItems[i].setQuantity(-quantity);
                        break;
                    } else {
                        abstractItems[i] = null;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 添加道具
     *
     * @param item
     * @return
     */
    public boolean addStorageItem(AbstractItem item) {
        String itemResourceId = item.getItemResourceId();
        ItemResource itemResource = SpringContext.getItemService().getItemResourceByItemResourceId(itemResourceId);
        //是否加入背包
        boolean isAdd = false;
        //道具可堆叠
        if (itemResource.getOverLimit() > 1) {
            for (AbstractItem abstractItem : abstractItems) {
                if (abstractItem != null) {
                    //可堆叠
                    //已经存在
                    if (item.getItemResourceId() == abstractItem.getItemResourceId()) {
                        //判断是否超过堆叠上限
                        if (item.getQuantity() >= itemResource.getOverLimit()) {
                            //TODO
                            isAdd = false;
                            break;
                        }
                        abstractItem.setQuantity(abstractItem.getQuantity() + 1);
                        isAdd = true;
                        break;
                    }
                }
            }
        }
        //不可堆叠 或者背包不存在
        if (!isAdd) {
            for (int i = 0; i < abstractItems.length; i++) {
                if (abstractItems[i] == null) {
                    abstractItems[i] = item;
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
                ", abstractItems=" + Arrays.toString(abstractItems) +
                '}';
    }
}
