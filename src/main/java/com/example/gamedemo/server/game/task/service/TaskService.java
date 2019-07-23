package com.example.gamedemo.server.game.task.service;

import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.entity.TaskStorageEnt;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.resource.TaskResource;

/**
 * @author: wengj
 * @date: 2019/7/15
 * @description: 任务模块接口
 */
public interface TaskService {

  /**
   * 获取任务信息
   *
   * @param player
   */
  void getTaskInfo(Player player);
  /**
   * 处理任务事件
   *
   * @param event
   */
  void handleTaskEvent(TaskEvent event);

  /**
   * 处理玩家登陆事件
   *
   * @param event
   */
  void handlePlayerLoadEvent(PlayerLoadEvent event);
  /**
   * 获取任务实体
   *
   * @param playerId
   * @return
   */
  TaskStorageEnt getTaskStorageEnt(Long playerId);

  /**
   * 接受奖励
   *
   * @param player
   * @param taskId
   * @return
   */
  boolean rewardTaskAfterFinish(Player player, int taskId);

  /**
   * 保存任务栏
   *
   * @param player
   */
  void saveTaskStorage(Player player);

  /**
   * 推进触发
   *
   * @param task
   * @param player
   */
  void doAfterTriggerProgress(Task task, Player player);

  /**
   * 推进任务
   *
   * @param task
   * @param player
   */
  void doAfterExecuteProgress(Task task, Player player);

  /**
   * 获取任务配置资源
   *
   * @param taskId
   * @return
   */
  TaskResource getTaskResource(Integer taskId);
}
