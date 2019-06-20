package com.example.gamedemo.server.game.skill.storage;

import com.example.gamedemo.server.game.skill.model.Skill;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description:技能容器
 * @date 2019/6/20
 */
public class SkillStorage {
  private Map<Integer, Skill> skills = new HashMap<>();

  public Map<Integer, Skill> getSkills() {
    return skills;
  }

  public void setSkills(Map<Integer, Skill> skills) {
    this.skills = skills;
  }

  /**
   * 新增技能
   *
   * @param skill
   */
  public void putSkill(Skill skill) {
    skills.put(skill.getSkillId(), skill);
  }

  /**
   * 移除技能
   *
   * @param skillId
   */
  public void removeSkill(Integer skillId) {
    skills.remove(skillId);
  }
}
