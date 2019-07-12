package com.example.gamedemo.server.game.buff.service;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.model.AbstractBuff;
import com.example.gamedemo.server.game.buff.resource.BuffResource;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/6/25
 * @description: buff业务接口层
 */
public interface BuffService {

  /**
   * 移除buff
   *
   * @param creature
   * @param buffTypeEnum
   * @return
   */
  boolean removeBuff(CreatureObject creature, BuffTypeEnum buffTypeEnum);

  /**
   * 添加buff
   *
   * @param caster
   * @param owner
   * @param buffId
   * @return
   */
  AbstractBuff addBuff(CreatureObject caster, CreatureObject owner, int buffId);

  /**
   * 获取buff的配置资源
   *
   * @param id
   * @return
   */
  BuffResource getBuffResourceById(Integer id);

  /**
   * 添加buffs
   *
   * @param caster
   * @param owner
   * @param buffArray
   */
  void addBuffsByBuffIdArray(Player caster, CreatureObject owner, int[] buffArray);
}
