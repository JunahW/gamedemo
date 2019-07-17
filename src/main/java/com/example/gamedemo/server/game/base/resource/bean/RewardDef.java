package com.example.gamedemo.server.game.base.resource.bean;

import com.example.gamedemo.server.game.base.resource.RewardTypeEnum;

/**
 * @author wengj
 * @description:奖励定义
 * @date 2019/7/17
 */
public class RewardDef {
  /** 奖励类型 */
  private RewardTypeEnum type;

  /** 奖励的值 */
  private String value;

  public RewardTypeEnum getType() {
    return type;
  }

  public void setType(RewardTypeEnum type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "RewardDef{" + "type=" + type + ", value='" + value + '\'' + '}';
  }
}
