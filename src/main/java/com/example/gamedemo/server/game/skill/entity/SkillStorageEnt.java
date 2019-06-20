package com.example.gamedemo.server.game.skill.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wengj
 * @description
 * @date 2019/6/20
 */
@Table
@javax.persistence.Entity
public class SkillStorageEnt implements Entity<Long> {
  @Id private Long playerId;

  @Column(length = 10000)
  private String skillData;

  @Transient private SkillStorage skillStorage;

  @Override
  public Long getEntityId() {
    return playerId;
  }

  @Override
  public void setNullId() {
    playerId = null;
  }

  @Override
  public boolean serialize() {
    setSkillData(JsonUtils.serializeEntity(skillStorage));
    return true;
  }

  @Override
  public boolean deSerialize() {
    SkillStorage skillStorage = JsonUtils.deSerializeEntity(getSkillData(), SkillStorage.class);
    this.setSkillStorage(skillStorage);
    return true;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public String getSkillData() {
    return skillData;
  }

  public void setSkillData(String skillData) {
    this.skillData = skillData;
  }

  public SkillStorage getSkillStorage() {
    return skillStorage;
  }

  public void setSkillStorage(SkillStorage skillStorage) {
    this.skillStorage = skillStorage;
  }
}
