package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.bag.model.CommonItem;
import com.example.gamedemo.server.game.bag.resource.ItemResource;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/5/21
 * @description: 背包业务接口层
 */
public interface ItemService {
    /**
     * 添加背包道具
     *
     * @param player
     * @param itemId
     * @return
     */
    boolean addItem(Player player, String itemId);

    /**
     * 使用背包道具
     *
     * @param player
     * @param guid
     * @param quanlity
     * @return
     */
    boolean useItem(Player player, long guid, int quanlity);

    /**
     * 查看查询某个物品数量
     *
     * @param player
     * @param guid
     * @return
     */
    int getItemNum(Player player, long guid);

    /**
     * 查看背包是否已满
     *
     * @param player
     * @return
     */
    int checkBag(Player player);

    /**
     * 创建道具
     *
     * @param itemResourceId
     * @return
     */
    AbstractItem createItem(String itemResourceId);

    /**
     * 减少道具
     *
     * @param player
     * @param item
     * @param quanlity
     * @return
     */
    boolean reduceItem(Player player, CommonItem item, int quanlity);

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

    /**
     * 保存背包信息
     *
     * @param player
     */
    void saveItemStorageEnt(Player player);
}
