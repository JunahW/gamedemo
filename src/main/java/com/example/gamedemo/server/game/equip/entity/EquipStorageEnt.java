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
public class EquipStorageEnt implements Entity<Long> {

  /** 主键 */
  @Id private Long id;

  /** 装备栏数据 */
  @Column private String equipStorageData;

  @Transient private EquipStorage equipStorage;

  @Override
  public Long getEntityId() {
    return this.id;
  }

  @Override
  public void setNullId() {
    this.id = null;
  }

  @Override
  public boolean serialize() {
    this.setEquipStorageData(JsonUtils.serializeEntity(this.getEquipStorage()));
    return true;
  }

  @Override
  public boolean deSerialize() {
    this.setEquipStorage(
        JsonUtils.deSerializeEntity(this.getEquipStorageData(), EquipStorage.class));
    return true;
  }

  public String getEquipStorageData() {
    return equipStorageData;
  }

  public void setEquipStorageData(String equipStorageData) {
    this.equipStorageData = equipStorageData;
  }

  public EquipStorage getEquipStorage() {
    return equipStorage;
  }

  public void setEquipStorage(EquipStorage equipStorage) {
    this.equipStorage = equipStorage;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
