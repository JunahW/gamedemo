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

    /**
     * 已有数量
     */
    private int num;


    public Integer getSize() {
        return size;
    }

    public void setAbstractItems(AbstractItem[] abstractItems) {
        this.abstractItems = abstractItems;
    }

    public int getNum() {
        return num;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public AbstractItem[] getAbstractItems() {
        return abstractItems;
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
                if (abstractItems[i].getQuanlity() >= quanlity) {
                    int size = abstractItems[i].getQuanlity() - quanlity;
                    if (size > 0) {
                        abstractItems[i].setQuanlity(size);
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
                        abstractItem.setQuanlity(abstractItem.getQuanlity() + 1);
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
                ", num=" + num +
                '}';
    }
}
