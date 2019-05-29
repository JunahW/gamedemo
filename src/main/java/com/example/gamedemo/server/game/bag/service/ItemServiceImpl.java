package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.constant.ItemType;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.ItemStorage;
import com.example.gamedemo.server.game.bag.model.StorageItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
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
        StorageItem storageItem = createItem(itemId);
        boolean isAdd = pack.addStorageItem(storageItem);
        return isAdd;
    }

    @Override
    public int useItem(Account account, long guid, int quanlity) {
        ItemStorage pack = account.getPack();
        StorageItem storageItem = pack.getStorageItemByObjectId(guid);
        if (storageItem == null) {
            logger.info("道具不存在");
            return 0;
        }
        if (storageItem.getQuanlity() < quanlity) {
            logger.info("道具数量不足");
            return 0;
        }
        //减少道具
        pack.reduceStorageItemByObjectId(guid, quanlity);

        //产生效果


        return 1;
    }

    @Override
    public int getItemNum(Account account, long guid) {
        ItemStorage pack = account.getPack();
        StorageItem item = pack.getStorageItemByObjectId(guid);
        if (item == null) {
            logger.info("[{}]背包不存在该物品[{}]", account.getAcountName(), guid);
            return -1;
        }
        return item.getQuanlity();
    }

    @Override
    public int checkBag(Account account) {
        ItemStorage pack = account.getPack();
        StorageItem[] storageItems = pack.getStorageItems();
        int size = 0;
        for (StorageItem item : storageItems) {
            if (item == null) {
                size++;
            }
        }
        return size;
    }

    @Override
    public StorageItem createItem(String itemResourceId) {
        ItemResource itemResource = itemManager.getResourceById(itemResourceId);
        StorageItem storageItem = doCreateItem(itemResource, 1);
        return storageItem;
    }

    /**
     * 创建道具
     *
     * @param itemResource
     * @param num
     * @return
     */
    private StorageItem doCreateItem(ItemResource itemResource, int num) {
        int itemType = itemResource.getItemType();
        StorageItem storageItem = ItemType.create(itemType);
        //唯一id
        storageItem.setObjectId(UniqueIdUtils.nextId());
        storageItem.setItemResourceId(itemResource.getItemId());
        storageItem.setQuanlity(num);
        return storageItem;
    }

    @Override
    public boolean reduceItem(Account account, StorageItem item, int quanlity) {
        if (item.getQuanlity() < quanlity) {
            logger.info("道具数量不足");
            return false;
        }
        ItemStorage pack = account.getPack();
        boolean isReduce = pack.reduceStorageItemByObjectId(item.getObjectId(), quanlity);
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
