package com.example.gamedemo.server.game.skill.model;

import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.command.RemoveBuffCommand;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.model.Buff;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
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

  public void useSkillProgress(CreatureObject attacker, List<CreatureObject> targetList) {
    beforeUseSkillProgress(attacker, targetList);
    useSkill(attacker, targetList);
    addBuff2Target(targetList);
  }

  /**
   * 使用技能前的操作
   *
   * @param attacker
   * @param targetList
   */
  private void beforeUseSkillProgress(CreatureObject attacker, List<CreatureObject> targetList) {
    SkillResource skillResource =
        SpringContext.getSkillService().getSkillResourceById(this.getSkillId());
    this.setLastUseTime(System.currentTimeMillis());
    // 减少mp
    attacker.setMp(attacker.getMp() - skillResource.getMp());
  }

  /**
   * 为目标物添加buff
   *
   * @param targetList
   */
  private void addBuff2Target(List<CreatureObject> targetList) {
    SkillResource skillResource =
        SpringContext.getSkillService().getSkillResourceById(this.getSkillId());
    int[] buffArray = skillResource.getBuffArray();
    for (CreatureObject target : targetList) {
      if (buffArray == null) {
        break;
      }
      for (int buffId : buffArray) {
        BuffResource buffResourceById = SpringContext.getBuffService().getBuffResourceById(buffId);
        Buff buff = BuffTypeEnum.createBuff(buffResourceById.getBuffType(), buffId);
        target.addBuff(buff);
        // 移除buff
        SceneExecutor.addDelayTask(
            RemoveBuffCommand.valueOf(target.getSceneId(), target.getBuffContainer(), buffId),
            buffResourceById.getDuration());
      }
    }
  }
}
