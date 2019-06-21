package com.example.gamedemo.server.game.skill.packet;

/**
 * @author wengj
 * @description：选择技能
 * @date 2019/6/21
 */
public class CM_SelectSkill {
  /** 技能id */
  private int skillId;

  /** 技能的位置 */
  private int index;

  public int getSkillId() {
    return skillId;
  }

  public int getIndex() {
    return index;
  }
}
