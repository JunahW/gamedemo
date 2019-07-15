package com.example.gamedemo.server.game.task.event;

import com.example.gamedemo.common.event.Event;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;

/**
 * @author wengj
 * @description：任务事件
 * @date 2019/7/15
 */
public class TaskEvent implements Event {
  /** 玩家 */
  private Player player;

  /** 任务类型 */
  private TaskTypeEnum taskType;

  /** 改变的值 */
  private int value;

  /**
   * @param player
   * @param taskTypeEnum
   * @param value
   * @return
   */
  public static TaskEvent valueOf(Player player, TaskTypeEnum taskTypeEnum, int value) {
    TaskEvent event = new TaskEvent();
    event.setPlayer(player);
    event.setTaskType(taskTypeEnum);
    event.setValue(value);
    return event;
  }

  @Override
  public Object getOwnerId() {
    return player.getId();
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public TaskTypeEnum getTaskType() {
    return taskType;
  }

  public void setTaskType(TaskTypeEnum taskType) {
    this.taskType = taskType;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
