package com.example.gamedemo.server.game.buff.service;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.model.AbstractBuff;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.example.gamedemo.server.game.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description：buff业务层
 * @date 2019/6/25
 */
@Service
public class BuffServiceImpl implements BuffService {

  private static final Logger logger = LoggerFactory.getLogger(BuffServiceImpl.class);

  @Autowired private BuffManager buffManager;

  @Override
  public boolean removeBuff(CreatureObject creature, BuffTypeEnum buffTypeEnum) {
    creature.getBuffContainer().removeBuff(buffTypeEnum.getBuffType());
    return true;
  }

  @Override
  public AbstractBuff addBuff(CreatureObject caster, CreatureObject owner, int buffId) {
    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(buffId);
    AbstractBuff buff =
        buffResource
            .getBuffTypeEnum()
            .createBuff(
                caster,
                buffId,
                System.currentTimeMillis() + buffResource.getDuration(),
                buffResource.getDuration());
    owner.getBuffContainer().addBuff(buff);
    return buff;
  }

  @Override
  public BuffResource getBuffResourceById(Integer id) {
    return buffManager.getBuffResourceById(id);
  }

  @Override
  public void addBuffsByBuffIdArray(Player caster, CreatureObject owner, int[] buffArray) {
    for (int buffId : buffArray) {
      addBuff(caster, owner, buffId);
      logger.info(
          "[{}][{}]给加[{}]buff[{}]",
          caster.getSceneObjectType(),
          caster.getId(),
          owner.getId(),
          buffId);
    }
  }
}
