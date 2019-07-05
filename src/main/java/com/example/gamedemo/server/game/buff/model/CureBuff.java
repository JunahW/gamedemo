package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengj
 * @description：中毒buff
 * @date 2019/7/4
 */
public class CureBuff extends Buff {
  private static final Logger logger = LoggerFactory.getLogger(CureBuff.class);

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
            "[{}][{}]恢复血量[{}]", owner.getSceneObjectType(), owner.getId(), hp - owner.getHp());
        owner.setHp(hp);
      } else if (attribute.getType().equals(AttributeTypeEnum.MP)) {
        long mp = attribute.getValue() + owner.getMp();
        Long maxMp = owner.getAttributeContainer().getAttributeValue(AttributeTypeEnum.MP);
        if (maxMp < mp) {
          mp = maxMp;
        }
        logger.info(
            "[{}][{}]恢复魔法量[{}]", owner.getSceneObjectType(), owner.getId(), mp - owner.getMp());
        owner.setMp(mp);
      }
    }
  }
}
