package com.example.gamedemo.server.game.task.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.task.resource.TaskResource;

/**
 * @author wengj
 * @description:任务模型
 * @date 2019/7/15
 */
public class Task {
  /** 任务id，读配置表 唯一 */
  private int taskId;
  /** 执行进度 */
  private int executeProgress;
  /** 触发进度 */
  private int triggerProgress;

  /**
   * @param taskId
   * @return
   */
  public static Task valueOf(int taskId) {
    Task task = new Task();
    task.setTaskId(taskId);
    return task;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getExecuteProgress() {
    return executeProgress;
  }

  public void setExecuteProgress(int executeProgress) {
    this.executeProgress = executeProgress;
  }

  public int getTriggerProgress() {
    return triggerProgress;
  }

  public void setTriggerProgress(int triggerProgress) {
    this.triggerProgress = triggerProgress;
  }

  /**
   * 推进执行中任务
   *
   * @param value
   * @param canReplace
   */
  public void changeExecuteProgress(int value, boolean canReplace) {
    TaskResource taskResource = SpringContext.getTaskService().getTaskResource(taskId);
    int executeFinishValue = taskResource.getExecuteFinishValue();
    if (canReplace) {
      if (executeFinishValue >= value) {
        this.executeProgress = value;
      }
    } else {
      if (executeFinishValue > this.executeProgress) {
        this.executeProgress += value;
      }
    }
  }

  /**
   * 推进待触发的任务
   *
   * @param value
   * @param canReplace
   */
  public void changeTriggerProgress(int value, boolean canReplace) {
    if (canReplace) {
      this.triggerProgress = value;
    } else {
      this.triggerProgress += value;
    }
  }
}
