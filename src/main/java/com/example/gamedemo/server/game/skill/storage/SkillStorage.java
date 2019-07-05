package com.example.gamedemo.server.game.skill.storage;

import com.example.gamedemo.common.constant.SystemConstant;
import com.example.gamedemo.server.game.skill.model.Skill;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description:技能容器
 * @date 2019/6/20
 */
public class SkillStorage {
  /** 已学习技能 */
  private Map<Integer, Skill> skills = new HashMap<>();

  /** 技能槽 */
  private Integer[] skillSlots = new Integer[SystemConstant.SKILL_SLOT_SIZE];

  public Map<Integer, Skill> getSkills() {
    return skills;
  }

  public void setSkills(Map<Integer, Skill> skills) {
    this.skills = skills;
  }

  public Integer[] getSkillSlots() {
    return skillSlots;
  }

  public void setSkillSlots(Integer[] skillSlots) {
    this.skillSlots = skillSlots;
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

  /**
   * 获取技能栏的技能
   *
   * @param index
   * @return
   */
  public Skill getSkillByIndex(int index) {
    return skills.get(skillSlots[index]);
  }
}
