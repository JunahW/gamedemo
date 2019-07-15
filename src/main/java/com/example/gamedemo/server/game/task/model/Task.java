package com.example.gamedemo.server.game.task.model;

/**
 * @author wengj
 * @description:任务模型
 * @date 2019/7/15
 */
public class Task {
  /** 任务id，读配置表 唯一 */
  private int taskId;

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
}
