package com.example.gamedemo.server.game.task.packet;

/**
 * @author wengj
 * @description：任务完成信息
 * @date 2019/7/16
 */
public class SM_TaskFinish {
  /** 任务操作 */
  private String content;
  /** 任务id */
  private int taskId;

  public static SM_TaskFinish valueOf(int taskId) {
    SM_TaskFinish sm_taskFinish = new SM_TaskFinish();
    sm_taskFinish.setTaskId(taskId);
    sm_taskFinish.setContent("该任务已完成");
    return sm_taskFinish;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
}
