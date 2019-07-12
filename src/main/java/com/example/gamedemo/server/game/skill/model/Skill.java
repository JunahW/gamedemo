package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Set;

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

  /**
   * 使用技能
   *
   * @param attacker
   * @param targetSet
   */
  public abstract void useSkill(Player attacker, Set<CreatureObject> targetSet);

  public void useSkillProgress(Player attacker, Set<CreatureObject> targetSet) {
    beforeUseSkill(attacker, targetSet);
    useSkill(attacker, targetSet);
    afterUseSkill(attacker, targetSet);
  }

  /**
   * 使用技能前的操作
   *
   * @param attacker
   * @param targetSet
   */
  private void beforeUseSkill(Player attacker, Set<CreatureObject> targetSet) {
    SkillResource skillResource =
        SpringContext.getSkillService().getSkillResourceById(this.getSkillId());
    // 设置cd
    attacker
        .getCdComponent()
        .setSkillCd(skillId, System.currentTimeMillis() + skillResource.getCd());
    // 减少mp
    attacker.setMp(attacker.getMp() - skillResource.getMp());
    int[] casterBeforeBuffArray = skillResource.getCasterBeforeBuffArray();
    addBuff2Target(attacker, attacker, casterBeforeBuffArray);
    addBuff2TargetSet(attacker, targetSet, skillResource.getBeforeBuffArray());
  }

  /**
   * 技能使用后
   *
   * @param attacker
   * @param targetSet
   */
  public void afterUseSkill(Player attacker, Set<CreatureObject> targetSet) {
    SkillResource skillResource =
        SpringContext.getSkillService().getSkillResourceById(this.getSkillId());
    int[] casterAfterBuffArray = skillResource.getCasterAfterBuffArray();
    addBuff2Target(attacker, attacker, casterAfterBuffArray);
    addBuff2TargetSet(attacker, targetSet, skillResource.getAfterBuffArray());
  }

  /**
   * 为目标物添加buff
   *
   * @param caster
   * @param targetSet
   * @param buffArray
   */
  private void addBuff2TargetSet(Player caster, Set<CreatureObject> targetSet, int[] buffArray) {
    if (buffArray != null) {
      for (CreatureObject owner : targetSet) {
        SpringContext.getBuffService().addBuffsByBuffIdArray(caster, owner, buffArray);
      }
    }
  }

  /**
   * 为目标物添加buff
   *
   * @param caster
   * @param target
   * @param buffArray
   */
  private void addBuff2Target(Player caster, CreatureObject target, int[] buffArray) {
    if (buffArray != null) {
      SpringContext.getBuffService().addBuffsByBuffIdArray(caster, target, buffArray);
    }
  }
}
