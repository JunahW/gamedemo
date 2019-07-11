package com.example.gamedemo.server.game.buff.service;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.model.Buff;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description：buff业务层
 * @date 2019/6/25
 */
@Service
public class BuffServiceImpl implements BuffService {

  @Autowired private BuffManager buffManager;

  @Override
  public boolean removeBuff(CreatureObject creature, BuffTypeEnum buffTypeEnum) {
    creature.getBuffContainer().removeBuff(buffTypeEnum.getBuffType());
    return true;
  }

  @Override
  public Buff addBuff(CreatureObject caster, CreatureObject owner, int buffId) {
    BuffResource buffResource = SpringContext.getBuffService().getBuffResourceById(buffId);
    Buff buff =
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
  public void addBuffsByBuffIdArray(CreatureObject caster, CreatureObject owner, int[] buffArray) {
    for (int buffId : buffArray) {
      addBuff(caster, owner, buffId);
    }
  }
}
