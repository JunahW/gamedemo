package com.example.gamedemo.server.game.skill.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author wengj
 * @description：技能
 * @date 2019/6/20
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
public class Skill {
  /** 配置表id */
  private int skillId;

  /** 等级 */
  private int level;

  /** 最后一次使用时间 毫秒值 */
  private long lastUseTime;

  public static Skill valueOf(int skillId) {
    Skill skill = new Skill();
    skill.setSkillId(skillId);
    return skill;
  }

  public int getSkillId() {
    return skillId;
  }

  public void setSkillId(int skillId) {
    this.skillId = skillId;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public long getLastUseTime() {
    return lastUseTime;
  }

  public void setLastUseTime(long lastUseTime) {
    this.lastUseTime = lastUseTime;
  }
}
