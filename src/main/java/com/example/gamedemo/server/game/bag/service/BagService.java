package com.example.gamedemo.server.game.bag.service;

import com.example.gamedemo.server.game.account.model.Account;

/**
 * @author: wengj
 * @date: 2019/5/21
 * @description: 背包业务接口层
 */
public interface BagService {
    /**
     * 添加背包道具
     *
     * @param account
     * @param itemId
     * @return
     */
    int addItem(Account account, String itemId);

    /**
     * 使用背包道具
     *
     * @param account
     * @param itemId
     * @return
     */
    int useItem(Account account, String itemId);
}
