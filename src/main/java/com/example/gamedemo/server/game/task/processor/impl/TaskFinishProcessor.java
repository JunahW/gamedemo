package com.example.gamedemo.server.game.task.processor.impl;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import com.example.gamedemo.server.game.task.processor.AbstractProcessor;
import com.example.gamedemo.server.game.task.storage.TaskStorage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/7/22
 */
@Component
public class TaskFinishProcessor extends AbstractProcessor {

  @Override
  public TaskTypeEnum getTaskTypeEnum() {
    return TaskTypeEnum.TASK_FINISH;
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
  public void initTrigger(TaskCondition triggerCondition, Task task, Player player) {
    TaskStorage taskStorage = player.getTaskStorage();
    List<Integer> finishTaskList = taskStorage.getFinishTaskList();
    if (finishTaskList.contains(triggerCondition.getValues()[1])) {
      task.setTriggerProgress(1);
    }
  }
}
