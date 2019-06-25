package com.example.gamedemo.server.game.skill.constant;

import com.example.gamedemo.server.game.skill.model.BuffSkill;
import com.example.gamedemo.server.game.skill.model.CommonSkill;
import com.example.gamedemo.server.game.skill.model.DurationSkill;

/**
 * @author: wengj
 * @date: 2019/6/20
 * @description: 技能类型
 */
public enum SkillTypeEnum {
  /** 普通技能 */
  COMMON_SKILL(0, CommonSkill.class),

  /** 持久性技能 */
  DURATION_SKILL(1, DurationSkill.class),

  /** buff技能 */
  BUFF_SKILL(2, BuffSkill.class);

  private int id;

  private Class skillClazz;

  SkillTypeEnum(int id, Class skillClazz) {
    this.id = id;
    this.skillClazz = skillClazz;
  }
}
