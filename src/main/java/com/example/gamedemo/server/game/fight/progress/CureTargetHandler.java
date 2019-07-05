package com.example.gamedemo.server.game.fight.progress;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.skill.model.Skill;
import com.example.gamedemo.server.game.skill.resource.SkillResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengj
 * @description:治疗技能处理器
 * @date 2019/7/4
 */
public class CureTargetHandler {
  private static final Logger logger = LoggerFactory.getLogger(CureTargetHandler.class);

  public static void handle(CreatureObject attacker, CreatureObject target, Skill skill) {
    SkillResource skillResource =
        SpringContext.getSkillService().getSkillResourceById(skill.getSkillId());
    List<Attribute> effectList = skillResource.getEffectList();
    for (Attribute attribute : effectList) {
      if (attribute.getType().equals(AttributeTypeEnum.HP)) {
        long hp = target.getHp() + attribute.getValue();
        Long maxHp = target.getAttributeContainer().getAttributeValue(AttributeTypeEnum.HP);
        if (maxHp < hp) {
          hp = maxHp;
        }
        logger.info(
            "[{}][{}]恢复血量[{}]", target.getSceneObjectType(), target.getId(), hp - target.getHp());
        target.setHp(hp);
      }
      if (attribute.getType().equals(AttributeTypeEnum.MP)) {
        long mp = target.getMp() + attribute.getValue();
        Long maxMp = target.getAttributeContainer().getAttributeValue(AttributeTypeEnum.MP);
        if (maxMp < mp) {
          mp = maxMp;
        }
        logger.info(
            "[{}][{}]恢复魔法量[{}]", target.getSceneObjectType(), target.getId(), mp - target.getMp());
        target.setMp(mp);
      }
    }
  }
}
