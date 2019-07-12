package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.common.event.EventBusManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.example.gamedemo.server.game.monster.event.MonsterDeadEvent;
import com.example.gamedemo.server.game.monster.model.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengj
 * @description：中毒buff
 * @date 2019/7/4
 */
public class PoisonBuff extends AbstractBuff {
  private static final Logger logger = LoggerFactory.getLogger(PoisonBuff.class);

  @Override
  public void active(CreatureObject owner) {
    logger.info("[{}][{}]buff[{}]执行", owner.getSceneObjectType(), owner.getId(), getBuffId());
    /** 添加buff */
    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(getBuffId());
    List<Attribute> effectList = buffResource.getEffectList();
    for (Attribute attribute : effectList) {
      if (attribute.getType().equals(AttributeTypeEnum.HP)) {
        long hp = attribute.getValue() + owner.getHp();
        Long maxHp = owner.getAttributeContainer().getAttributeValue(AttributeTypeEnum.HP);
        if (maxHp < hp) {
          hp = maxHp;
        }
        logger.info(
            "[{}][{}]血量[{}]", owner.getSceneObjectType(), owner.getId(), hp - owner.getHp());
        owner.setHp(hp);
        if (owner.getHp() <= 0) {
          // 对象死亡
          if (owner instanceof Monster) {
            Monster monster = (Monster) owner;
            EventBusManager.submitEvent(
                MonsterDeadEvent.valueOf(
                    getCaster(),
                    monster.getSceneId(),
                    monster.getId(),
                    monster.getMonsterResourceId()));
          }
        }
      } else if (attribute.getType().equals(AttributeTypeEnum.MP)) {
        long mp = attribute.getValue() + owner.getMp();
        Long maxMp = owner.getAttributeContainer().getAttributeValue(AttributeTypeEnum.MP);
        if (maxMp < mp) {
          mp = maxMp;
        }
        logger.info(
            "[{}][{}]魔法量[{}]", owner.getSceneObjectType(), owner.getId(), mp - owner.getMp());
        owner.setMp(mp);
      }
    }
  }

  @Override
  public void merge(AbstractBuff buff) {
    haveMergeTime++;
    setDuration(getDuration() + buff.getDuration());
    setEndTime(getEndTime() + buff.getDuration());
    logger.info("buff[{}]叠加，叠加的次数[{}]", buff.getBuffId(), haveMergeTime);
  }
}
