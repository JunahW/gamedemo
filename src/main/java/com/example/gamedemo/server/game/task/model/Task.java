package com.example.gamedemo.server.game.task.model;

/**
 * @author wengj
 * @description:任务模型
 * @date 2019/7/15
 */
public class Task {
  /** 任务id，读配置表 唯一 */
  private int taskId;

  /** 进度 */
  private int progress;

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

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  /**
   * 推进任务
   *
   * @param value
   * @param canReplace
   */
  public void changeProgress(int value, boolean canReplace) {
    if (canReplace) {
      this.progress = value;
    } else {
      this.progress += value;
    }
  }
}
