package com.example.gamedemo.server.game.mapinfo.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.mapinfo.model.PlayerMapInfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wengj
 * @description
 * @date 2019/7/23
 */
@Table
@javax.persistence.Entity
public class PlayerMapInfoEnt implements Entity<Long> {
  /** 主键 */
  @Id private Long id;

  /** 地图信息 */
  @Column(length = 20000)
  private String mapInfoData;

  @Transient private PlayerMapInfo playerMapInfo;

  @Override
  public Long getEntityId() {
    return id;
  }

  @Override
  public void setNullId() {}

  @Override
  public boolean serialize() {
    mapInfoData = JsonUtils.serializeEntity(playerMapInfo);
    return true;
  }

  @Override
  public boolean deSerialize() {
    playerMapInfo = JsonUtils.deSerializeEntity(mapInfoData, PlayerMapInfo.class);
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMapInfoData() {
    return mapInfoData;
  }

  public void setMapInfoData(String mapInfoData) {
    this.mapInfoData = mapInfoData;
  }

  public PlayerMapInfo getPlayerMapInfo() {
    return playerMapInfo;
  }

  public void setPlayerMapInfo(PlayerMapInfo playerMapInfo) {
    this.playerMapInfo = playerMapInfo;
  }
}
