package com.example.gamedemo.server.game.skill.packet;

/**
 * @author wengj
 * @description:使用技能
 * @date 2019/6/20
 */
public class CM_UseSkill {
  /** 技能id */
  private int index;

  /** 目标id */
  private long targetId;

  public int getIndex() {
    return index;
  }

  public long getTargetId() {
    return targetId;
  }
}
