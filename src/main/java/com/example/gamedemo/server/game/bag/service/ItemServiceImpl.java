package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.constant.ItemType;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.CommonItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description
 * @date 2019/5/21
 */
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemManager itemManager;

    @Override
    public boolean addItem(Account account, String itemId) {
        ItemStorage pack = account.getPack();
        AbstractItem abstractItem = createItem(itemId);
        if (abstractItem == null) {
            return false;
        }
        boolean isAdd = pack.addStorageItem(abstractItem);
        if (isAdd) {
            //保存入库
            ItemStorageEnt itemStorageEnt = getItemStorageEntByAccountId(account.getAcountId());
            itemManager.saveItemStorageEnt(itemStorageEnt);
        }
        return isAdd;
    }

    @Override
    public boolean useItem(Account account, long guid, int quanlity) {
        ItemStorage pack = account.getPack();
        AbstractItem commonItem = pack.getStorageItemByObjectId(guid);
        if (commonItem == null) {
            logger.info("道具不存在");
            return false;
        }
        if (commonItem.getQuanlity() < quanlity) {
            logger.info("道具数量不足");
            return false;
        }
        //减少道具
        pack.reduceStorageItemByObjectId(guid, quanlity);
        logger.info("[{}]玩家使用了道具[{}]", account.getAcountName(), commonItem.getItemName());

        //保存入库
        ItemStorageEnt itemStorageEnt = getItemStorageEntByAccountId(account.getAcountId());
        itemManager.saveItemStorageEnt(itemStorageEnt);

        //产生效果

        return true;
    }

    @Override
    public int getItemNum(Account account, long guid) {
        ItemStorage pack = account.getPack();
        AbstractItem item = pack.getStorageItemByObjectId(guid);
        if (item == null) {
            logger.info("[{}]背包不存在该物品[{}]", account.getAcountName(), guid);
            return -1;
        }
        return item.getQuanlity();
    }

    @Override
    public int checkBag(Account account) {
        ItemStorage pack = account.getPack();
        AbstractItem[] commonItems = pack.getAbstractItems();
        int size = 0;
        for (AbstractItem item : commonItems) {
            if (item == null) {
                size++;
            }
        }
        return size;
    }

    @Override
    public AbstractItem createItem(String itemResourceId) {
        ItemResource itemResource = itemManager.getResourceById(itemResourceId);
        if (itemResource == null) {
            logger.warn("参数有误[{}]该道具不存在", itemResourceId);
            return null;
        }
        AbstractItem abstractItem = doCreateItem(itemResource, 1);
        return abstractItem;
    }

    /**
     * 创建道具
     *
     * @param itemResource
     * @param num
     * @return
     */
    private AbstractItem doCreateItem(ItemResource itemResource, int num) {
        int itemType = itemResource.getItemType();
        AbstractItem abstractItem = ItemType.create(itemType);
        //唯一id
        abstractItem.setObjectId(UniqueIdUtils.nextId());
        abstractItem.setItemName(itemResource.getName());
        abstractItem.setItemResourceId(itemResource.getItemId());
        abstractItem.setQuanlity(num);
        return abstractItem;
    }

    @Override
    public boolean reduceItem(Account account, CommonItem item, int quanlity) {
        if (item.getQuanlity() < quanlity) {
            logger.info("道具数量不足");
            return false;
        }
        ItemStorage pack = account.getPack();
        boolean isReduce = pack.reduceStorageItemByObjectId(item.getObjectId(), quanlity);
        //保存入库
        ItemStorageEnt itemStorageEnt = getItemStorageEntByAccountId(account.getAcountId());
        itemManager.saveItemStorageEnt(itemStorageEnt);
        return isReduce;
    }

    @Override
    public ItemStorageEnt getItemStorageEntByAccountId(String accountId) {
        return itemManager.getItemStorageEnt(accountId);
    }

    @Override
    public ItemResource getItemResourceByItemResourceId(String itemResourceId) {
        return itemManager.getResourceById(itemResourceId);
    }
}
