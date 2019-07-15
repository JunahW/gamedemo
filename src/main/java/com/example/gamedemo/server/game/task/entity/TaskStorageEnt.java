package com.example.gamedemo.server.game.task.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.task.storage.TaskStorage;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wengj
 * @description
 * @date 2019/7/15
 */
@Table
@javax.persistence.Entity
public class TaskStorageEnt implements Entity<Long> {
  @Id private Long playerId;
  @Column private String taskStorageData;
  @Transient private TaskStorage taskStorage;

  @Override
  public Long getEntityId() {
    return playerId;
  }

  @Override
  public void setNullId() {}

  @Override
  public boolean serialize() {
    setTaskStorageData(JsonUtils.serializeEntity(getTaskStorage()));
    return true;
  }

  @Override
  public boolean deSerialize() {
    setTaskStorage(JsonUtils.deSerializeEntity(getTaskStorageData(), TaskStorage.class));
    return true;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
  }

  public String getTaskStorageData() {
    return taskStorageData;
  }

  public void setTaskStorageData(String taskStorageData) {
    this.taskStorageData = taskStorageData;
  }

  public TaskStorage getTaskStorage() {
    return taskStorage;
  }

  public void setTaskStorage(TaskStorage taskStorage) {
    this.taskStorage = taskStorage;
  }
}
