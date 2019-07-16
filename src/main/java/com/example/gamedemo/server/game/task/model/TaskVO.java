package com.example.gamedemo.server.game.task.model;

import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;

/**
 * @author wengj
 * @description:任务vo
 * @date 2019/7/16
 */
public class TaskVO {
  /** 任务id */
  private int taskId;

  /** 当前进度 */
  private int progress;

  /** 任务完成进度 */
  private int finishValue;

  private TaskTypeEnum taskType;

  /**
   * @param taskId
   * @param progress
   * @param finishValue
   * @return
   */
  public static TaskVO valueOf(int taskId, int progress, int finishValue, TaskTypeEnum taskType) {
    TaskVO taskVO = new TaskVO();
    taskVO.setTaskId(taskId);
    taskVO.setProgress(progress);
    taskVO.setFinishValue(finishValue);
    taskVO.setTaskType(taskType);
    return taskVO;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public int getFinishValue() {
    return finishValue;
  }

  public void setFinishValue(int finishValue) {
    this.finishValue = finishValue;
  }

  public TaskTypeEnum getTaskType() {
    return taskType;
  }

  public void setTaskType(TaskTypeEnum taskType) {
    this.taskType = taskType;
  }
}
