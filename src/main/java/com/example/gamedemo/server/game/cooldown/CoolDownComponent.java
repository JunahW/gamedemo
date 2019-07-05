package com.example.gamedemo.server.game.cooldown;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description：cd组件
 * @date 2019/7/5
 */
public class CoolDownComponent {
  /** 技能cd */
  private Map<Integer, Long> skillCoolDowns = new HashMap<>();
  /** 技能共有cd */
  private Map<Integer, Long> publicSkillCoolDowns = new HashMap<>();

  /**
   * 返回技能的cd
   *
   * @param skillId
   * @return
   */
  public long getSkillCd(Integer skillId) {
    Long skillCoolDown = skillCoolDowns.get(skillId);
    Long publicSillCoolDown = publicSkillCoolDowns.get(CoolDownConstant.SKILL_PUBLIC_CD_KEY);
    if (publicSillCoolDown == null) {
      if (skillCoolDown == null) {
        return 0;
      } else {
        return skillCoolDown;
      }
    } else {
      if (skillCoolDown == null) {
        return publicSillCoolDown;
      } else {
        return skillCoolDown > publicSillCoolDown ? skillCoolDown : publicSillCoolDown;
      }
    }
  }

  /**
   * 判断技能是否处于cd
   *
   * @param skillId
   * @return
   */
  public boolean isSkillInCd(Integer skillId) {
    Long coolDown = skillCoolDowns.get(skillId);
    if (coolDown == null) {
      return false;
    }
    if (coolDown <= System.currentTimeMillis()) {
      skillCoolDowns.remove(skillId);
      return false;
    }
    return true;
  }

  /**
   * 设置技能cd
   *
   * @param skillId
   * @param time
   */
  public void setSkillCd(Integer skillId, long time) {
    skillCoolDowns.put(skillId, time);
  }
}
