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
        boolean exist = isExist(item);
        //物品存在
        if (exist) {
            for (AbstractItem abstractItem : abstractItems) {
                if (abstractItem != null) {
                    //可堆叠
                    //已经存在
                    if (item.getItemResourceId().equals(abstractItem.getItemResourceId())) {
                        //判断是否超过堆叠上限
                        if (abstractItem.getQuantity() + item.getQuantity() <= itemResource.getOverLimit()) {
                            abstractItem.setQuantity(abstractItem.getQuantity() + item.getQuantity());
                            item.setQuantity(0);
                            break;
                        } else {
                            int add = itemResource.getOverLimit() - abstractItem.getQuantity();
                            int rest = item.getQuantity() - add;

                            abstractItem.setQuantity(abstractItem.getQuantity() + add);
                            item.setQuantity(rest);
                        }

                    }
                }
            }
        }
        //不可堆叠 堆叠达到上限 或者道具不存在
        if (!exist || item.getQuantity() > 0) {
            for (int i = 0; i < abstractItems.length; i++) {
                if (abstractItems[i] == null) {
                    if (itemResource.getOverLimit() >= item.getQuantity()) {
                        abstractItems[i] = item;
                        break;
                    } else {
                        //超过上限
                        AbstractItem cloneItem = null;
                        try {
                            cloneItem = (AbstractItem) item.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }

                        int rest = item.getQuantity() - itemResource.getOverLimit();
                        cloneItem.setQuantity(itemResource.getOverLimit());
                        abstractItems[i] = cloneItem;
                        item.setQuantity(rest);
                    }
                }
                if (i == abstractItems.length - 1 && item.getQuantity() > 0) {
                    //格子已满
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemStorage{" +
                "size=" + size +
                ", abstractItems=" + Arrays.toString(abstractItems) +
                '}';
    }

    /**
     * 判断物品是否存在于背包
     *
     * @param item
     * @return
     */
    private boolean isExist(AbstractItem item) {
        for (AbstractItem abstractItem : abstractItems) {
            if (abstractItem != null) {
                if (abstractItem.getItemResourceId().equals(item.getItemResourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查背包的道具数量是否满足
     *
     * @param itemResourceId
     * @param quantity
     * @return
     */
    public boolean checkPackItemQuantity(String itemResourceId, int quantity) {
        int total = 0;
        for (AbstractItem item : abstractItems) {
            if (item != null) {
                if (item.getItemResourceId().equals(itemResourceId)) {
                    total = +item.getQuantity();
                    if (total >= quantity) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
