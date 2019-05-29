package com.example.gamedemo.server.game.bag.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.bag.model.ItemStorage;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wengj
 * @description
 * @date 2019/5/29
 */
@javax.persistence.Entity
@Table
public class ItemStorageEnt implements Entity<String> {

    @Id
    private String accountId;

    @Column
    private String BagData;

    @Transient
    private ItemStorage itemStorage;

    @Override
    public String getId() {
        return this.accountId;
    }

    @Override
    public void setNullId() {
        this.accountId = null;
    }

    @Override
    public boolean doSerialize() {
        this.setBagData(JsonUtils.serializeEntity(itemStorage));
        return true;
    }

    @Override
    public boolean doDeSerialize() {
        this.itemStorage = JsonUtils.deSerializeEntity(this.getBagData(), ItemStorage.class);
        return false;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBagData() {
        return BagData;
    }

    public ItemStorage getItemStorage() {
        return itemStorage;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setBagData(String bagData) {
        BagData = bagData;
    }

    public void setItemStorage(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }
}
