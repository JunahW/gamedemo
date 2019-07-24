package com.example.gamedemo.server.game.task.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.common.packet.SM_NoticeMessge;
import com.example.gamedemo.server.game.base.resource.bean.RewardDef;
import com.example.gamedemo.server.game.base.utils.RewardUtils;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
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

import java.util.List;
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
              task.getExecuteProgress(),
              Integer.parseInt(taskResource.getTaskCondition().getValue()),
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
    Task task = taskStorage.getExecuteTTaskById(taskId);
    if (task == null) {
      logger.info("该任务不存在（这请求不该出现！）");
      RequestException.throwException(I18nId.TARGET_NO_FOUND);
    }
    TaskResource taskResource = taskManager.getTaskResource(taskId);
    if (taskResource == null) {
      logger.info("该任务配置资源不存在（这请求不该出现！）");
      RequestException.throwException(I18nId.TARGET_NO_FOUND);
    }
    // 判断任务是否真的已经完成
    if (!isTaskFinish(task)) {
      logger.error("该请求非法，该任务还无完成");
      RequestException.throwException(I18nId.TASK_NO_FINISH);
    }
    List<RewardDef> rewardDefs = taskResource.getRewardDefs();
    // TODO 校验背包是否足够
    boolean enoughPackSize = RewardUtils.isEnoughPackSize(player, rewardDefs);
    if (enoughPackSize) {
      RewardUtils.reward(player, rewardDefs);
      logger.info("获得奖励[{}]", rewardDefs);
    } else {
      logger.info("{玩家[{}]背包无法装完奖励道具", player.getId());
    }

    // 移除旧任务
    taskStorage.removeExecuteTTask(taskId);
    taskStorage.addFinishTask(taskId);
    saveTaskStorage(player);

    SessionManager.sendMessage(player, SM_NoticeMessge.valueOf("任务已完成"));

    // 抛出任务完成事件
    triggerNewTasks(player, taskId);
    EventBusManager.submitEvent(TaskEvent.valueOf(player, TaskTypeEnum.TASK_FINISH, 1));

    return true;
  }

  @Override
  public void saveTaskStorage(Player player) {
    taskManager.saveTaskStorageEnt(getTaskStorageEnt(player.getId()));
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
    if (isTaskFinish(task)) {
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
    player.getTaskStorage().putExecuteTask(task);
    doAfterRefreshProgress(player, task);
    // 保存任务
    saveTaskStorage(player);
  }

  /**
   * 判断任务是否完成
   *
   * @param task
   * @return
   */
  private boolean isTaskFinish(Task task) {
    TaskResource taskResource = taskManager.getTaskResource(task.getTaskId());
    int executeFinishValue = taskResource.getExecuteFinishValue();
    int executeProgress = task.getExecuteProgress();
    // 任务完成
    if (executeProgress >= executeFinishValue) {
      return true;
    }
    return false;
  }

  @Override
  public void doAfterTriggerProgress(Task task, Player player) {
    TaskResource taskResource = taskManager.getTaskResource(task.getTaskId());
    int triggerFinishValue = taskResource.getTriggerFinishValue();
    int triggerProgress = task.getTriggerProgress();
    // 可以触发
    if (triggerProgress >= triggerFinishValue) {
      TaskStorage taskStorage = player.getTaskStorage();
      taskStorage.removeTriggerTask(task.getTaskId());
      taskStorage.putExecuteTask(task);
      saveTaskStorage(player);

      TaskCondition taskCondition = taskResource.getTaskCondition();
      AbstractProcessor processor = AbstractProcessor.getProcessor(taskCondition.getType());
      processor.initProgress(taskCondition, task, player);
    }
  }

  @Override
  public void doAfterExecuteProgress(Task task, Player player) {
    // 任务完成
    if (isTaskFinish(task)) {
      // 发送信息通知完成
      SessionManager.sendMessage(player, SM_TaskFinish.valueOf(task.getTaskId()));
    }
  }

  /**
   * 触发下一个任务
   *
   * @param player
   * @param taskId
   */
  public void triggerNewTasks(Player player, int taskId) {
    // 开始新任务
    TaskResource taskResource = taskManager.getTaskResource(taskId);

    int[] nextTaskIdArray = taskResource.getNextTaskIdArray();
    if (nextTaskIdArray != null) {
      for (int nextTaskId : nextTaskIdArray) {
        // 接受新任务
        TaskResource nextTaskResource = taskManager.getTaskResource(nextTaskId);
        if (nextTaskResource == null) {
          logger.error("该任务[{}]配置资源不存在", taskId);
          RequestException.throwException(I18nId.TASK_RESOURCE_NO_EXIST);
        }
        Task task = Task.valueOf(nextTaskId);
        TaskCondition triggerCondition = taskResource.getTriggerCondition();
        AbstractProcessor processor = AbstractProcessor.getProcessor(triggerCondition.getType());
        processor.initTrigger(triggerCondition, task, player);
        TaskStorage taskStorage = player.getTaskStorage();
        taskStorage.putTriggerTask(task);
        this.doAfterTriggerProgress(task, player);
        // 保存任务
        saveTaskStorage(player);
      }
    }
  }

  @Override
  public TaskResource getTaskResource(Integer taskId) {
    return taskManager.getTaskResource(taskId);
  }
}
