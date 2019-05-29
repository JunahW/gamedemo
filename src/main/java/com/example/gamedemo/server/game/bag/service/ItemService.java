package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.StorageItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;

/**
 * @author: wengj
 * @date: 2019/5/21
 * @description: 背包业务接口层
 */
public interface ItemService {
    /**
     * 添加背包道具
     *
     * @param account
     * @param itemId
     * @return
     */
    boolean addItem(Account account, String itemId);

    /**
     * 使用背包道具
     *
     * @param account
     * @param guid
     * @param quanlity
     * @return
     */
    int useItem(Account account, long guid, int quanlity);

    /**
     * 查看查询某个物品数量
     *
     * @param account
     * @param guid
     * @return
     */
    int getItemNum(Account account, long guid);

    /**
     * 查看背包是否已满
     *
     * @param account
     * @return
     */
    int checkBag(Account account);

    /**
     * 创建道具
     *
     * @param itemResourceId
     * @return
     */
    StorageItem createItem(String itemResourceId);

    /**
     * 减少道具
     *
     * @param account
     * @param item
     * @param quanlity
     * @return
     */
    boolean reduceItem(Account account, StorageItem item, int quanlity);

    /**
     * 获取背包
     *
     * @param accountId
     * @return
     */
    ItemStorageEnt getItemStorageEntByAccountId(String accountId);

    /**
     * 获取配置的数据
     *
     * @param itemResourceId
     * @return
     */
    ItemResource getItemResourceByItemResourceId(String itemResourceId);

}
