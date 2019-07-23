package com.example.gamedemo.server.game.base.resource.bean;

import com.example.gamedemo.server.game.base.resource.ConditionTypeEnum;

/**
 * @author wengj
 * @description：条件定义
 * @date 2019/7/22
 */
public class ConditionDef {
  /** 条件类型 */
  private ConditionTypeEnum type;

  /** 条件的值 */
  private int value;

  public ConditionTypeEnum getType() {
    return type;
  }

  public void setType(ConditionTypeEnum type) {
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
