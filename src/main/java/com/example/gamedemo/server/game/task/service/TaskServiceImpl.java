package com.example.gamedemo.server.game.task.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.entity.TaskStorageEnt;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import com.example.gamedemo.server.game.task.model.TaskVO;
import com.example.gamedemo.server.game.task.packet.SM_TaskDetail;
import com.example.gamedemo.server.game.task.packet.SM_TaskFinish;
import com.example.gamedemo.server.game.task.processor.AbstractProcessor;
import com.example.gamedemo.server.game.task.resource.TaskResource;
import com.example.gamedemo.server.game.task.storage.TaskStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
  public void getTaskInfo(Player player) {
    TaskStorage taskStorage = player.getTaskStorage();
    SM_TaskDetail sm_taskDetail = new SM_TaskDetail();
    Map<Integer, Task> inExecuteTask = taskStorage.getInExecuteTask();
    for (Task task : inExecuteTask.values()) {
      TaskResource taskResource = taskManager.getTaskResource(task.getTaskId());
      TaskVO taskVO =
          TaskVO.valueOf(
              task.getTaskId(),
              task.getProgress(),
              taskResource.getTaskCondition().getValue(),
              taskResource.getTaskCondition().getType());
      sm_taskDetail.addTaskVo(taskVO);
    }
    SessionManager.sendMessage(player, sm_taskDetail);
  }

  @Override
  public void handleTaskEvent(TaskEvent event) {
    logger.info("处理任务事件");
    AbstractProcessor processor = AbstractProcessor.getProcessor(event.getTaskType());
    Player player = event.getPlayer();
    TaskStorage taskStorage = player.getTaskStorage();
    Map<Integer, Task> inExecuteTasks = taskStorage.getInExecuteTask();
    for (Task task : inExecuteTasks.values()) {
      TaskResource taskResource = taskManager.getTaskResource(task.getTaskId());
      if (taskResource == null) {
        logger.error("任务[{}]配置资源不存在", task.getTaskId());
        continue;
      }
      TaskCondition taskCondition = taskResource.getTaskCondition();
      if (taskCondition.getType() == event.getTaskType()) {
        // 刷新任务进度
        processor.refreshProgress(taskCondition, event, task);
        doAfterRefreshProgress(player, task);
      }
    }
    // 保存任务
    saveTaskStorage(player);
  }

  @Override
  public TaskStorageEnt getTaskStorageEnt(Long playerId) {
    return taskManager.getTaskStorageEnt(playerId);
  }

  @Override
  public boolean rewardTaskAfterFinish(Player player, int taskId) {
    TaskStorage taskStorage = player.getTaskStorage();
    Task task = taskStorage.getTaskById(taskId);
    TaskResource taskResource = taskManager.getTaskResource(taskId);
    TaskCondition taskCondition = taskResource.getTaskCondition();
    // 判断任务是否真的已经完成
    if (!isTaskFinish(task, taskCondition)) {
      logger.error("该请求非法，该任务还无完成");
      RequestException.throwException(I18nId.TASK_NO_FINISH);
    }
    // TODO 奖励
    // taskResource.getRewardString();

    // 开始新任务
    int nextTaskId = taskResource.getNextTaskId();

    // 移除旧任务
    taskStorage.removeTask(taskId);
    // 接受新任务
    acceptNewTask(player, nextTaskId);

    return true;
  }

  @Override
  public void saveTaskStorage(Player player) {
    taskManager.savetaskStorageEnt(getTaskStorageEnt(player.getId()));
  }

  @Override
  public void handlePlayerLoadEvent(PlayerLoadEvent event) {
    Player player = event.getPlayer();
    TaskStorage taskStorage = player.getTaskStorage();
    // 玩家第一次登陆 分配任务
    if (taskStorage == null || taskStorage.getInExecuteTask().size() == 0) {
      acceptNewTask(player, GameConstant.FIRST_TASK);
    }
  }

  /**
   * 任务进度变化后的处理
   *
   * @param player
   * @param task
   */
  private void doAfterRefreshProgress(Player player, Task task) {
    TaskResource taskResource = taskManager.getTaskResource(task.getTaskId());
    TaskCondition taskCondition = taskResource.getTaskCondition();
    if (isTaskFinish(task, taskCondition)) {
      SessionManager.sendMessage(player, SM_TaskFinish.valueOf(task.getTaskId()));
    }
  }

  /**
   * 接受新任务
   *
   * @param player
   * @param taskId
   */
  private void acceptNewTask(Player player, int taskId) {
    TaskResource taskResource = taskManager.getTaskResource(taskId);
    if (taskResource == null) {
      logger.error("该任务[{}]配置资源不存在", taskId);
      RequestException.throwException(I18nId.TASK_RESOURCE_NO_EXIST);
    }
    Task task = Task.valueOf(taskId);
    TaskCondition taskCondition = taskResource.getTaskCondition();
    AbstractProcessor processor = AbstractProcessor.getProcessor(taskCondition.getType());
    processor.initProgress(taskCondition, task, player);
    player.getTaskStorage().putTask(task);
    doAfterRefreshProgress(player, task);
    // 保存任务
    saveTaskStorage(player);
  }

  /**
   * 判断任务是否完成
   *
   * @param task
   * @param taskCondition
   * @return
   */
  private boolean isTaskFinish(Task task, TaskCondition taskCondition) {
    if (task.getProgress() >= taskCondition.getValue()) {
      return true;
    }
    return false;
  }
}
