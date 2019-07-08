package com.example.gamedemo.server.game.buff.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wengj
 * @description：修改属性的buff
 * @date 2019/7/8
 */
public class AttributeBuff extends Buff {
  private static final Logger logger = LoggerFactory.getLogger(AttributeBuff.class);

  @Override
  public void active(CreatureObject owner) {}

  @Override
  public void gainBuff(CreatureObject owner) {

    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(getBuffId());
    List<Attribute> effectList = buffResource.getEffectList();
    owner
        .getAttributeContainer()
        .putAndComputeAttributes(buffResource.getBuffTypeEnum(), effectList);
    logger.info(
        "[{}][{}]获得buff[{}]增强属性[{}]",
        owner.getSceneObjectType(),
        owner.getId(),
        getBuffId(),
        effectList);
  }

  @Override
  public void loseBuff(CreatureObject owner) {
    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(getBuffId());
    owner.getAttributeContainer().removeAndComputeAttributeSet(buffResource.getBuffTypeEnum());
    logger.info(
        "[{}][{}]buff[{}]移除，减弱属性[{}]",
        owner.getSceneObjectType(),
        owner.getId(),
        getBuffId(),
        buffResource.getEffectList());
  }
}
