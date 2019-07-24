package com.example.gamedemo.server.game.task.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.task.entity.TaskStorageEnt;
import com.example.gamedemo.server.game.task.resource.TaskResource;
import com.example.gamedemo.server.game.task.storage.TaskStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/15
 */
@Component
public class TaskManager {
  /** 静态资源 */
  private Map<Integer, TaskResource> taskResource =
      ResourceManager.getResourceMap(TaskResource.class);

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, TaskStorageEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(TaskStorageEnt.class);
    entityCacheService.setAccessor(accessor);
  }

  /**
   * 获取任务栏
   *
   * @param playerId
   * @return
   */
  public TaskStorageEnt getTaskStorageEnt(Long playerId) {
    TaskStorageEnt taskStorageEnt =
        entityCacheService.loadOrCreate(
            playerId,
            new EntityBuilder<Long, TaskStorageEnt>() {
              @Override
              public TaskStorageEnt newInstance(Long id) {
                TaskStorageEnt skillStorageEnt = new TaskStorageEnt();
                TaskStorage taskStorage = new TaskStorage();
                skillStorageEnt.setTaskStorage(taskStorage);
                skillStorageEnt.setPlayerId(playerId);
                return skillStorageEnt;
              }
            });
    return taskStorageEnt;
  }

  /**
   * 获取任务配置资源
   *
   * @param taskId
   * @return
   */
  public TaskResource getTaskResource(Integer taskId) {
    return taskResource.get(taskId);
  }

  /**
   * 保存技能栏
   *
   * @param taskStorageEnt
   */
  public void saveTaskStorageEnt(TaskStorageEnt taskStorageEnt) {
    entityCacheService.writeBack(taskStorageEnt.getEntityId(), taskStorageEnt);
  }
}
