package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
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

  @Autowired private ItemManager itemManager;

  @Override
  public boolean addItem(Player player, int itemId) {
    ItemStorage pack = player.getPack();
    AbstractItem abstractItem = createItem(itemId);
    if (abstractItem == null) {
      return false;
    }
    boolean isAdd = pack.addStorageItem(abstractItem);
    if (isAdd) {
      // 保存入库
      saveItemStorageEnt(player);
    }
    return isAdd;
  }

  @Override
  public boolean useItem(Player player, long guid, int quantity) {
    ItemStorage pack = player.getPack();
    AbstractItem commonItem = pack.getStorageItemByObjectId(guid);
    if (commonItem == null) {
      logger.info("背包不存在道具[{}]", guid);
      RequestException.throwException(I18nId.BAG_NO_EXIST_ITEM);
    }
    if (commonItem.getQuantity() < quantity) {
      logger.info("道具数量不足");
      RequestException.throwException(I18nId.ITEM_QUANTITY_NO_ENOUGH);
    }
    // 减少道具
    pack.reduceStorageItemByObjectId(guid, quantity);
    logger.info("[{}]玩家使用了道具[{}]", player.getPlayerName(), commonItem.getItemName());

    // 保存入库
    saveItemStorageEnt(player);

    // 产生效果

    return true;
  }

  @Override
  public boolean useItem(Player player, int itemResourceId, int quantity) {
    ItemStorage pack = player.getPack();

    boolean isUse = pack.reduceStorageItemByItemResourceId(itemResourceId, quantity);
    // 保存入库
    saveItemStorageEnt(player);

    return isUse;
  }

  @Override
  public int getItemNum(Player player, long guid) {
    ItemStorage pack = player.getPack();
    AbstractItem item = pack.getStorageItemByObjectId(guid);
    if (item == null) {
      RequestException.throwException(I18nId.BAG_NO_EXIST_ITEM);
      logger.info("[{}]背包不存在该物品[{}]", player.getPlayerName(), guid);
    }
    return item.getQuantity();
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
  public AbstractItem createItem(int itemResourceId) {
    ItemResource itemResource = itemManager.getResourceById(itemResourceId);
    if (itemResource == null) {
      logger.warn("参数有误[{}]该道具不存在", itemResourceId);
      RequestException.throwException(I18nId.ITEM_NO_EXIST);
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
    // 唯一id
    abstractItem.setObjectId(UniqueIdUtils.nextId());
    abstractItem.setItemName(itemResource.getName());
    abstractItem.setItemResourceId(itemResource.getItemId());
    abstractItem.setQuantity(num);
    return abstractItem;
  }

  @Override
  public boolean reduceItem(Player player, CommonItem item, int quanlity) {
    if (item.getQuantity() < quanlity) {
      logger.info("道具数量不足");
      return false;
    }
    ItemStorage pack = player.getPack();
    boolean isReduce = pack.reduceStorageItemByObjectId(item.getObjectId(), quanlity);
    // 保存入库
    saveItemStorageEnt(player);
    return isReduce;
  }

  @Override
  public ItemStorageEnt getItemStorageEntByAccountId(String accountId) {
    return itemManager.getItemStorageEnt(accountId);
  }

  @Override
  public ItemResource getItemResourceByItemResourceId(int itemResourceId) {
    return itemManager.getResourceById(itemResourceId);
  }

  @Override
  public void saveItemStorageEnt(Player player) {
    ItemStorageEnt itemStorageEnt = getItemStorageEntByAccountId(player.getAccountId());
    itemManager.saveItemStorageEnt(itemStorageEnt);
  }

  @Override
  public boolean removeItemByGuid(Player player, long guid) {
    int quantity = getItemNum(player, guid);
    boolean flag = player.getPack().reduceStorageItemByObjectId(guid, quantity);
    // 保存背包
    saveItemStorageEnt(player);
    return flag;
  }
}
