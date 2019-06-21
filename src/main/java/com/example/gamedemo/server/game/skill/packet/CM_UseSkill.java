package com.example.gamedemo.server.game.skill.packet;

/**
 * @author wengj
 * @description:使用技能
 * @date 2019/6/20
 */
public class CM_UseSkill {
  /** 技能id */
  private int skillId;

  /** 目标id */
  private long targetId;

  public int getSkillId() {
    return skillId;
  }

  public long getTargetId() {
    return targetId;
  }
}
