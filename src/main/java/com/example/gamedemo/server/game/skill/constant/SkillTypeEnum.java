package com.example.gamedemo.server.game.skill.constant;

import com.example.gamedemo.server.game.skill.model.BuffSkill;
import com.example.gamedemo.server.game.skill.model.CommonSkill;
import com.example.gamedemo.server.game.skill.model.RangeSkill;

/**
 * @author: wengj
 * @date: 2019/6/20
 * @description: 技能类型
 */
public enum SkillTypeEnum {
  /** 普通技能 */
  COMMON_SKILL(0, CommonSkill.class),

  /** 范围技能 */
  RANGE_SKILL(1, RangeSkill.class),

  /** buff技能 */
  BUFF_SKILL(2, BuffSkill.class);

  private int id;

  private Class skillClazz;

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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Class getSkillClazz() {
    return skillClazz;
  }

  public void setSkillClazz(Class skillClazz) {
    this.skillClazz = skillClazz;
  }
}
