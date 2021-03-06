package com.example.gamedemo.server.game.task.processor.impl;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import com.example.gamedemo.server.game.task.processor.AbstractProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：首次任务处理器
 * @date 2019/7/22
 */
@Component
public class FirstTaskProcessor extends AbstractProcessor {

  @Override
  public TaskTypeEnum getTaskTypeEnum() {
    return TaskTypeEnum.FIRST_TASK;
  }

  @Override
  public int getValue(TaskEvent event, TaskCondition taskCondition) {
    return event.getValue();
  }

  @Override
  public boolean canReplace() {
    return false;
  }

  @Override
  public void initProgress(TaskCondition taskCondition, Task task, Player player) {}

  @Override
  public void initTrigger(TaskCondition taskCondition, Task task, Player player) {}
}
