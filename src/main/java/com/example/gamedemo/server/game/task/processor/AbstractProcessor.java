package com.example.gamedemo.server.game.task.processor;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.model.TaskCondition;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description:任务处理器
 * @date 2019/7/15
 */
public abstract class AbstractProcessor {

  /** 任务进程处理器集合 */
  private static final Map<TaskTypeEnum, AbstractProcessor> processorMap = new HashMap<>();

  /**
   * 获取任务进程处理器
   *
   * @param taskTypeEnum
   * @return
   */
  public static AbstractProcessor getProcessor(TaskTypeEnum taskTypeEnum) {
    return processorMap.get(taskTypeEnum);
  }

  /**
   * 获取任务类型
   *
   * @return
   */
  public abstract TaskTypeEnum getTaskTypeEnum();

  @PostConstruct
  private void init() {
    processorMap.put(getTaskTypeEnum(), this);
  }

  /**
   * 获取进度值
   *
   * @param event
   * @param taskCondition
   * @return
   */
  public abstract int getValue(TaskEvent event, TaskCondition taskCondition);

  /**
   * true表示可以累加之前的值 false表示不能
   *
   * @return
   */
  public abstract boolean canReplace();

  /**
   * 刷新进度
   *
   * @param taskCondition
   * @param event
   * @param task
   */
  public void refreshProgress(TaskCondition taskCondition, TaskEvent event, Task task) {
    if (!taskCondition.getType().equals(event.getTaskType())) {
      return;
    }
    if (taskCondition.getValue() > task.getProgress()) {
      int value = event.getValue();
      task.changeProgress(value, canReplace());
    }
  }

  /**
   * 初始化任务进度
   *
   * @param taskCondition
   * @param task
   * @param player
   */
  public abstract void initProgress(TaskCondition taskCondition, Task task, Player player);
}
