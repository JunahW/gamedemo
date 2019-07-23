package com.example.gamedemo.server.game.task.resource;

import com.example.gamedemo.common.anno.ExcelColumn;
import com.example.gamedemo.common.anno.Resource;
import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.common.resource.ResourceInterface;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.base.resource.bean.RewardDef;
import com.example.gamedemo.server.game.task.model.TaskCondition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.StringUtils;

import java.util.List;

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

  /** 触发条件 */
  @ExcelColumn(columnName = "triggerConditionString")
  private String triggerConditionString;

  private TaskCondition triggerCondition;

  /** 下一个任务 */
  @ExcelColumn(columnName = "nextTaskIds")
  private String nextTaskIds;

  private int[] nextTaskIdArray;

  /** 完成条件 */
  @ExcelColumn(columnName = "finishConditionString")
  private String finishConditionString;

  private TaskCondition taskCondition;

  /** 完成奖励 */
  @ExcelColumn(columnName = "rewardString")
  private String rewardString;

  private List<RewardDef> rewardDefs;

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

  public List<RewardDef> getRewardDefs() {
    return rewardDefs;
  }

  public void setRewardDefs(List<RewardDef> rewardDefs) {
    this.rewardDefs = rewardDefs;
  }

  public TaskCondition getTriggerCondition() {
    return triggerCondition;
  }

  public void setTriggerCondition(TaskCondition triggerCondition) {
    this.triggerCondition = triggerCondition;
  }

  public int[] getNextTaskIdArray() {
    return nextTaskIdArray;
  }

  public void setNextTaskIdArray(int[] nextTaskIdArray) {
    this.nextTaskIdArray = nextTaskIdArray;
  }

  @Override
  public void postInit() {
    if (!StringUtils.isEmpty(finishConditionString)) {
      taskCondition = JsonUtils.deSerializeEntity(finishConditionString, TaskCondition.class);
    }
    if (!StringUtils.isEmpty(triggerConditionString)) {
      triggerCondition = JsonUtils.deSerializeEntity(triggerConditionString, TaskCondition.class);
    }
    if (!StringUtils.isEmpty(nextTaskIds)) {
      String[] taskSplit = nextTaskIds.split(SystemConstant.SPLIT_TOKEN_COMMA);
      int[] array = new int[taskSplit.length];
      for (int i = 0; i < taskSplit.length; i++) {
        array[i] = Integer.parseInt(taskSplit[i]);
      }
      nextTaskIdArray = array;
    }

    if (!StringUtils.isEmpty(rewardString)) {
      rewardDefs = JsonUtils.getListByString(rewardString, new TypeReference<List<RewardDef>>() {});
    }
  }

  /**
   * 获取完成触发任务的值
   *
   * @return
   */
  @JsonIgnore
  public int getTriggerFinishValue() {
    if (triggerCondition != null) {
      int finishValue = Integer.parseInt(getTriggerCondition().getValues()[0]);
      return finishValue;
    }
    return -1;
  }

  /**
   * 获取完成任务的值
   *
   * @return
   */
  @JsonIgnore
  public int getExecuteFinishValue() {
    if (taskCondition != null) {
      int finishValue = Integer.parseInt(getTaskCondition().getValues()[0]);
      return finishValue;
    }
    return -1;
  }
}
