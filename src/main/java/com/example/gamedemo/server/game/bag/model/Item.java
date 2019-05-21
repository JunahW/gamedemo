package com.example.gamedemo.server.game.bag.model;

import com.example.gamedemo.server.game.account.model.Account;

/**
 * @author: wengj
 * @date: 2019/5/21
 * @description: 道具顶层接口
 */
interface Item {
    /**
     * 使用道具
     */
    void useBagItem(Account account);
}
