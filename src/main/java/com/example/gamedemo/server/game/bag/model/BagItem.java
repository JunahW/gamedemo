package com.example.gamedemo.server.game.bag.model;

import com.example.gamedemo.server.game.account.model.Account;

/**
 * @author wengj
 * @description
 * @date 2019/5/21
 */
public class BagItem implements Item {

    /**
     * 道具id
     */
    private String itemId;

    /**
     * 道具名称
     */
    private String itemName;

    /**
     * 道具数量
     */
    private int quanlity;

    /**
     * 道具类型
     */
    private int itemType;

    /**
     * 描述
     */
    private String description;


    @Override
    public void useBagItem(Account account) {

    }
}
