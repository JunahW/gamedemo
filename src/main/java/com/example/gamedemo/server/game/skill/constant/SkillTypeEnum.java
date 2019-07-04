package com.example.gamedemo.server.game.skill.constant;

import com.example.gamedemo.server.game.skill.model.AttackSkill;
import com.example.gamedemo.server.game.skill.model.CureSkill;
import com.example.gamedemo.server.game.skill.model.DurationSkill;
import com.example.gamedemo.server.game.skill.model.Skill;

/**
 * @author: wengj
 * @date: 2019/6/20
 * @description: 技能类型
 */
public enum SkillTypeEnum {
  /** 攻击技能 */
  ATTACK_SKILL(0, AttackSkill.class),

  /** 治疗技能 */
  CURE_SKILL(1, CureSkill.class),

  /** buff技能 */
  DURATION_SKILL(2, DurationSkill.class);

  private int id;

  private Class<? extends Skill> skillClazz;

  SkillTypeEnum(int id, Class skillClazz) {
    this.id = id;
    this.skillClazz = skillClazz;
  }

  /**
   * 通过id获取技能的类型
   *
   * @param id
   * @return
   */
  public static SkillTypeEnum getSkillTypeEnumById(int id) {
    for (SkillTypeEnum skillTypeEnum : SkillTypeEnum.values()) {
      if (skillTypeEnum.getId() == id) {
        return skillTypeEnum;
      }
    }
    return null;
  }

  /**
   * 创建技能
   *
   * @param id
   * @return
   */
  public static Skill createSkill(int id, int skillId) {
    Skill skill = null;
    for (SkillTypeEnum skillTypeEnum : SkillTypeEnum.values()) {
      if (skillTypeEnum.getId() == id) {
        try {
          skill = skillTypeEnum.getSkillClazz().newInstance();
          skill.setSkillId(skillId);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return skill;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Class<? extends Skill> getSkillClazz() {
    return skillClazz;
  }

  public void setSkillClazz(Class<? extends Skill> skillClazz) {
    this.skillClazz = skillClazz;
  }
}
