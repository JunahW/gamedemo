package com.example.gamedemo.server.game.task.model;

import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.game.task.constant.TaskTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

/**
 * @author wengj
 * @description：任务条件
 * @date 2019/7/15
 */
public class TaskCondition {
  /** 任务类型 */
  private TaskTypeEnum type;

  /** 任务参数 */
  private String value;

  /** 分割参数 */
  private transient String[] values;

  @JsonIgnore
  public String[] getValues() {
    if (!StringUtils.isEmpty(value)) {
      // 分号分割
      values = value.split(GameConstant.SPLIT_TOKEN_COLON);
    }
    return values;
  }

  public TaskTypeEnum getType() {
    return type;
  }

  public void setType(TaskTypeEnum type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
