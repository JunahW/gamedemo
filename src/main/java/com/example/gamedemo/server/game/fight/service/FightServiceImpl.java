package com.example.gamedemo.server.game.fight.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.fight.constant.AreaTypeEnum;
import com.example.gamedemo.server.game.fight.constant.SkillAreaTypeEnum;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.skill.model.Skill;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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

    Scene scene = SpringContext.getSceneService().getSceneById(player.getSceneId());
    // 主目标
    CreatureObject target = scene.getCreatureObjectById(targetId);
    AreaTypeEnum areaTypeEnum = AreaTypeEnum.getAreaTypeEnumByAreaType(skillResource.getAreaType());
    // 获取技能的目标集合
    List<CreatureObject> targetCreatureObjectList =
        areaTypeEnum.getAreaCreatureObjectList(player, target, skillResource.getAreaParam());

    targetCreatureObjectList.add(target);

    // 技能使用
    skill.useSkillProgress(player, targetCreatureObjectList);

    return true;
  }
}
