package com.example.gamedemo.server.game.task.service;

import com.example.gamedemo.server.game.task.entity.TaskStorageEnt;
import com.example.gamedemo.server.game.task.event.TaskEvent;

/**
 * @author: wengj
 * @date: 2019/7/15
 * @description: 任务模块接口
 */
public interface TaskService {
  /**
   * 处理任务事件
   *
   * @param event
   */
  void handleTaskEvent(TaskEvent event);

  /**
   * 获取任务实体
   *
   * @param playerId
   * @return
   */
  TaskStorageEnt getTaskStorageEnt(Long playerId);
}
