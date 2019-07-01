package com.example.gamedemo.server.game.buff.service;

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
  public boolean addBuff(CreatureObject creature, Buff buff) {
    creature.getBuffContainer().putBuff(buff);
    return true;
  }

  @Override
  public BuffResource getBuffResourceById(Integer id) {
    return buffManager.getBuffResourceById(id);
  }
}
