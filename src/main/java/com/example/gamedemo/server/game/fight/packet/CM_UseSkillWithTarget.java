package com.example.gamedemo.server.game.fight.packet;

/**
 * @author wengj
 * @description
 * @date 2019/7/3
 */
public class CM_UseSkillWithTarget {
  /** 技能id */
  private int index;

  /** 目标物id */
  private long targetId;

  public int getIndex() {
    return index;
  }

  public long getTargetId() {
    return targetId;
  }
}
