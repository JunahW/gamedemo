package com.example.gamedemo.server.game.fight.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.fight.constant.AreaTypeEnum;
import com.example.gamedemo.server.game.fight.constant.FightConstant;
import com.example.gamedemo.server.game.fight.constant.SkillAreaTypeEnum;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.skill.command.UseSkillSceneCommand;
import com.example.gamedemo.server.game.skill.model.Skill;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wengj
 * @description
 * @date 2019/7/3
 */
@Component
public class FightServiceImpl implements FightService {
  private static final Logger logger = LoggerFactory.getLogger(FightServiceImpl.class);

  @Override
  public boolean useSkill(Player player, int index, Long targetId) {
    // 判断条件
    SkillStorage skillStorage = player.getSkillStorage();
    Skill skill = skillStorage.getSkillByIndex(index);
    if (skill == null) {
      logger.info("技能槽不存在该技能[{}]", index);
      RequestException.throwException(I18nId.SKILL_NO_EXIST);
    }
    // 判断魔法值是否满足要求
    long mp = player.getMp();
    SkillResource skillResource =
        SpringContext.getSkillService().getSkillResourceById(skill.getSkillId());
    // 检查技能cd
    boolean checkSkillCd = player.getCdComponent().isSkillInCd(skill.getSkillId());
    if (checkSkillCd) {
      logger.info("技能还未冷却");
      RequestException.throwException(I18nId.SKILL_NO_CD_YET);
    }
    if (mp < skillResource.getMp()) {
      logger.info("魔法值不足，无法使用技能[{}]", skill.getSkillId());
      RequestException.throwException(I18nId.MP_IS_NO_ENOUGH);
    }

    CreatureObject target = getTarget(targetId, player, skillResource);

    // 获取技能的目标集合
    Set<CreatureObject> targetSet = getTargetSet(player, target, skillResource);
    // 技能使用
    SpringContext.getSceneExecutorService()
        .submit(UseSkillSceneCommand.valueOf(player.getSceneId(), player, skill, targetSet));
    return true;
  }

  @Override
  public void doUseSkill(Player player, Skill skill, Set<CreatureObject> targetSet) {
    skill.useSkillProgress(player, targetSet);
  }

  /**
   * 获取主目标对象
   *
   * @param targetId
   * @param player
   * @param skillResource
   * @return
   */
  private CreatureObject getTarget(Long targetId, Player player, SkillResource skillResource) {
    SkillAreaTypeEnum skillAreaTypeEnum =
        SkillAreaTypeEnum.getAreaTypeEnumByAreaType(skillResource.getAreaType());
    // 获取主目标
    if (targetId == null) {
      if (skillResource.getContainSelf()) {
        targetId = player.getId();
      } else {
        Long targetIdByAreaType =
            skillAreaTypeEnum.getTargetIdByAreaType(player, skillResource.getSkillAreaParam());
        if (targetIdByAreaType == null) {
          logger.info("找不到目标物");
          RequestException.throwException(I18nId.TARGET_NO_FOUND);
        } else {
          targetId = targetIdByAreaType;
        }
      }
    }
    Scene scene = SpringContext.getSceneService().getSceneById(player, player.getSceneId());
    // 主目标
    CreatureObject target = scene.getCreatureObjectById(targetId);
    return target;
  }

  /**
   * 获取目标集合
   *
   * @param player
   * @param target
   * @param skillResource
   * @return
   */
  private Set<CreatureObject> getTargetSet(
      Player player, CreatureObject target, SkillResource skillResource) {
    // 获取技能的目标集合
    Set<CreatureObject> targetSet;

    AreaTypeEnum areaTypeEnum = AreaTypeEnum.getAreaTypeEnumByAreaType(skillResource.getAreaType());
    // 技能释放中心
    if (skillResource.getCenterType() == FightConstant.CENTER_TYPE_SELF) {
      targetSet =
          areaTypeEnum.getAreaCreatureObjectList(player, player, skillResource.getAreaParam());
    } else {
      targetSet =
          areaTypeEnum.getAreaCreatureObjectList(player, target, skillResource.getAreaParam());
    }
    targetSet.add(target);
    return targetSet;
  }
}
