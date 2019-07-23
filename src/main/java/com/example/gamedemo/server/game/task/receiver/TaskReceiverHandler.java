package com.example.gamedemo.server.game.task.receiver;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.equip.event.TakeOffEquipmentEvent;
import com.example.gamedemo.server.game.equip.event.WearEquipmentEvent;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import com.example.gamedemo.server.game.task.processor.AbstractProcessor;
import com.example.gamedemo.server.game.task.resource.TaskResource;
import com.example.gamedemo.server.game.task.service.TaskManager;
import com.example.gamedemo.server.game.task.storage.TaskStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wengj
 * @description：任务事件接收器
 * @date 2019/7/22
 */
@Component
public class TaskReceiverHandler {

  @Autowired private TaskManager taskManager;

  /**
   * 处理任务事件
   *
   * @param event
   */
  public void handleTaskEvent(TaskEvent event) {
    TaskStorage taskStorage = event.getPlayer().getTaskStorage();

    AbstractProcessor processor = AbstractProcessor.getProcessor(event.getTaskType());

    TaskResource taskResource;

    Map<Integer, Task> inTriggerTask = taskStorage.getInTriggerTask();
    for (Task task : inTriggerTask.values()) {
      taskResource = taskManager.getTaskResource(task.getTaskId());
      TaskCondition triggerCondition = taskResource.getTriggerCondition();
      if (event.getTaskType().equals(triggerCondition.getType())) {
        processor.refreshTrigger(triggerCondition, event, task);
        SpringContext.getTaskService().doAfterTriggerProgress(task, event.getPlayer());
      }
    }

    Map<Integer, Task> inExecuteTask = taskStorage.getInExecuteTask();
    for (Task task : inExecuteTask.values()) {
      taskResource = taskManager.getTaskResource(task.getTaskId());
      TaskCondition taskCondition = taskResource.getTaskCondition();
      if (event.getTaskType().equals(taskCondition.getType())) {
        processor.refreshProgress(taskCondition, event, task);
        SpringContext.getTaskService().doAfterExecuteProgress(task, event.getPlayer());
      }
    }
  }

  /**
   * 处理怪物死亡事件
   *
   * @param event
   */
  public void handleMonsterDeadEvent(MonsterDeadEvent event) {
    // 任务事件
    EventBusManager.submitEvent(
        TaskEvent.valueOf(event.getAttacker(), TaskTypeEnum.KILL_MONSTER_QUANTITY, 1));
  }

  /**
   * 处理穿装备事件
   *
   * @param event
   */
  public void handleWearEquipmentEvent(WearEquipmentEvent event) {
    Player player = event.getPlayer();
    EquipStorage equipBar = player.getEquipBar();
    EventBusManager.submitEvent(
        TaskEvent.valueOf(player, TaskTypeEnum.EQUIP_QUANTITY, equipBar.getEquipQuantity()));
  }

  /**
   * 处理脱装备事件
   *
   * @param event
   */
  public void handleTakeOffEquipmentEvent(TakeOffEquipmentEvent event) {
    Player player = event.getPlayer();
    EquipStorage equipBar = player.getEquipBar();
    EventBusManager.submitEvent(
        TaskEvent.valueOf(player, TaskTypeEnum.EQUIP_QUANTITY, equipBar.getEquipQuantity()));
  }
}
