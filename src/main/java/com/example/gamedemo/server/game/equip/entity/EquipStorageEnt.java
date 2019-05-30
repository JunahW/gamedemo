package com.example.gamedemo.server.game.equip.entity;


import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wengj
 * @description：装备栏实体
 * @date 2019/5/30
 */
@Table
@javax.persistence.Entity
public class EquipStorageEnt implements Entity<String> {

    /**
     * 主键
     */
    @Id
    private String accountId;

    /**
     * 装备栏数据
     */
    @Column
    private String equipStorageData;

    @Transient
    private EquipStorage equipStorage;


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
        this.setEquipStorageData(JsonUtils.serializeEntity(this.getEquipStorage()));
        return true;
    }

    @Override
    public boolean doDeSerialize() {
        this.setEquipStorage(JsonUtils.deSerializeEntity(this.getEquipStorageData(), EquipStorage.class));
        return true;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEquipStorageData() {
        return equipStorageData;
    }

    public EquipStorage getEquipStorage() {
        return equipStorage;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setEquipStorageData(String equipStorageData) {
        this.equipStorageData = equipStorageData;
    }

    public void setEquipStorage(EquipStorage equipStorage) {
        this.equipStorage = equipStorage;
    }
}
