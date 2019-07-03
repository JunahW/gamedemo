package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * @author wengj
 * @description：技能
 * @date 2019/6/20
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = false)
public abstract class Skill {
  /** 配置表id */
  private int skillId;

  /** 等级 */
  private int level;

  /** 最后一次使用时间 毫秒值 */
  private long lastUseTime;

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

  /**
   * 使用技能
   *
   * @param attacker
   * @param targetList
   */
  public abstract void useSkill(CreatureObject attacker, List<CreatureObject> targetList);
}
