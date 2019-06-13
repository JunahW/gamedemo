package com.example.gamedemo.server.game.bag.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;

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

    @Column(length = 10000)
    private String bagData;

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
    public boolean serialize() {
        this.setBagData(JsonUtils.serializeEntity(itemStorage));
        return true;
    }

    @Override
    public boolean deSerialize() {
        this.itemStorage = JsonUtils.deSerializeEntity(this.getBagData(), ItemStorage.class);
        return true;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBagData() {
        return bagData;
    }

    public ItemStorage getItemStorage() {
        // FIXME deSerialize();
        return itemStorage;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setBagData(String bagData) {
        this.bagData = bagData;
    }

    public void setItemStorage(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }
}
