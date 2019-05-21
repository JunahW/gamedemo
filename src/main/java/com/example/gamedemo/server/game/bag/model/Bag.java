package com.example.gamedemo.server.game.bag.model;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author wengj
 * @description 背包
 * @date 2019/5/21
 */
public class Bag {

    /**
     * 玩家
     */
    private String accountId;

    /**
     * 背包名称
     */
    private String bagName;

    /**
     * 背包容量
     */
    private Integer bagSize;

    /**
     * 背包物品
     */
    Map<Integer, BagItem> itemMap = new ConcurrentSkipListMap<>();

    public String getAccountId() {
        return accountId;
    }

    public String getBagName() {
        return bagName;
    }

    public Integer getBagSize() {
        return bagSize;
    }

    public Map<Integer, BagItem> getItemMap() {
        return itemMap;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setBagName(String bagName) {
        this.bagName = bagName;
    }

    public void setBagSize(Integer bagSize) {
        this.bagSize = bagSize;
    }

    public void setItemMap(Map<Integer, BagItem> itemMap) {
        this.itemMap = itemMap;
    }
}
