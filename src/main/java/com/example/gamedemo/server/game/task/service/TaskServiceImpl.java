package com.example.gamedemo.server.game.task.service;

import com.example.gamedemo.server.game.task.entity.TaskStorageEnt;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description
 * @date 2019/7/15
 */
@Service
public class TaskServiceImpl implements TaskService {
  private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

  @Autowired TaskManager taskManager;

  @Override
  public void handleTaskEvent(TaskEvent event) {
    logger.info("处理任务事件");
  }

  @Override
  public TaskStorageEnt getTaskStorageEnt(Long playerId) {
    return taskManager.getTaskStorageEnt(playerId);
  }
}
