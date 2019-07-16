package com.example.gamedemo.server.game.task.processor.impl;

import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
import com.example.gamedemo.server.game.task.event.TaskEvent;
import com.example.gamedemo.server.game.task.model.Task;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import com.example.gamedemo.server.game.task.processor.AbstractProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description：装备数量处理进程
 * @date 2019/7/16
 */
@Component
public class EquipQuantityProcessor extends AbstractProcessor {
  @Override
  public TaskTypeEnum getTaskTypeEnum() {
    return TaskTypeEnum.EQUIP_QUANTITY;
  }

  @Override
  public int getValue(TaskEvent event, TaskCondition taskCondition) {
    return 0;
  }

  @Override
  public boolean canReplace() {
    return true;
  }

  @Override
  public void initProgress(TaskCondition taskCondition, Task task, Player player) {
    EquipStorage equipBar = player.getEquipBar();
    int equipQuantity = equipBar.getEquipQuantity();
    int value = taskCondition.getValue();
    task.setProgress(Math.min(equipQuantity, value));
  }
}
