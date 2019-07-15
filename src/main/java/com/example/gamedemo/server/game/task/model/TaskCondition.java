package com.example.gamedemo.server.game.task.model;

import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;

/**
 * @author wengj
 * @description：任务条件
 * @date 2019/7/15
 */
public class TaskCondition {
  /** 任务类型 */
  private TaskTypeEnum type;

  /** 任务参数 */
  private int value;

  public TaskTypeEnum getType() {
    return type;
  }

  public void setType(TaskTypeEnum type) {
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
