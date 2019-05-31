package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.common.utils.UniqueIdUtils;
import com.example.gamedemo.server.game.bag.constant.ItemType;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.CommonItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.player.model.Player;
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
    public boolean addItem(Player player, String itemId) {
        ItemStorage pack = player.getPack();
        AbstractItem abstractItem = createItem(itemId);
        if (abstractItem == null) {
            return false;
        }
        boolean isAdd = pack.addStorageItem(abstractItem);
        if (isAdd) {
            //保存入库
            saveItemStorageEnt(player);
        }
        return isAdd;
    }

    @Override
    public boolean useItem(Player player, long guid, int quanlity) {
        ItemStorage pack = player.getPack();
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
        logger.info("[{}]玩家使用了道具[{}]", player.getPlayerName(), commonItem.getItemName());

        //保存入库
        saveItemStorageEnt(player);

        //产生效果

        return true;
    }

    @Override
    public int getItemNum(Player player, long guid) {
        ItemStorage pack = player.getPack();
        AbstractItem item = pack.getStorageItemByObjectId(guid);
        if (item == null) {
            logger.info("[{}]背包不存在该物品[{}]", player.getPlayerName(), guid);
            return -1;
        }
        return item.getQuanlity();
    }

    @Override
    public int checkBag(Player player) {
        ItemStorage pack = player.getPack();
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
    public boolean reduceItem(Player player, CommonItem item, int quanlity) {
        if (item.getQuanlity() < quanlity) {
            logger.info("道具数量不足");
            return false;
        }
        ItemStorage pack = player.getPack();
        boolean isReduce = pack.reduceStorageItemByObjectId(item.getObjectId(), quanlity);
        //保存入库
        saveItemStorageEnt(player);
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

    @Override
    public void saveItemStorageEnt(Player player) {
        ItemStorageEnt itemStorageEnt = getItemStorageEntByAccountId(player.getAccountId());
        itemManager.saveItemStorageEnt(itemStorageEnt);
    }
}