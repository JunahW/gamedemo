package com.example.gamedemo.server.game.task.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import org.springframework.util.StringUtils;

/**
 * @author wengj
 * @description
 * @date 2019/7/15
 */
@Resource
public class TaskResource implements ResourceInterface {
  /** 任务id */
  @ExcelColumn(columnName = "taskId")
  private int taskId;

  /** 任务名称 */
  @ExcelColumn(columnName = "taskName")
  private String taskName;

  /** 下一个任务 */
  @ExcelColumn(columnName = "nextTaskId")
  private int nextTaskId;

  /** 完成条件 */
  @ExcelColumn(columnName = "finishConditionString")
  private String finishConditionString;

  private TaskCondition taskCondition;

  /** 完成奖励 */
  @ExcelColumn(columnName = "rewardString")
  private String rewardString;

  @Override
  public Object getId() {
    return taskId;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getNextTaskId() {
    return nextTaskId;
  }

  public void setNextTaskId(int nextTaskId) {
    this.nextTaskId = nextTaskId;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public TaskCondition getTaskCondition() {
    return taskCondition;
  }

  public void setTaskCondition(TaskCondition taskCondition) {
    this.taskCondition = taskCondition;
  }

  public String getRewardString() {
    return rewardString;
  }

  public void setRewardString(String rewardString) {
    this.rewardString = rewardString;
  }

  @Override
  public void postInit() {
    if (!StringUtils.isEmpty(finishConditionString)) {
      taskCondition = JsonUtils.deSerializeEntity(finishConditionString, TaskCondition.class);
    }
  }
}
