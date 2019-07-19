package com.example.gamedemo.server.game.guild.entity;

import com.example.gamedemo.common.ramcache.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wengj
 * @description:玩家行会信息
 * @date 2019/7/18
 */
@javax.persistence.Entity
@Table
public class PlayerGuildEnt implements Entity<Long> {
  /** 玩家id */
  @Id private Long playerId;

  /** 行会id */
  @Column private Long guildId;

  /** 职位 */
  @Column private Integer position;

  @Override
  public Long getEntityId() {
    return playerId;
  }

  @Override
  public void setNullId() {}

  @Override
  public boolean serialize() {
    return true;
  }

  @Override
  public boolean deSerialize() {
    return true;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public Long getGuildId() {
    return guildId;
  }

  public void setGuildId(Long guildId) {
    this.guildId = guildId;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }
}
