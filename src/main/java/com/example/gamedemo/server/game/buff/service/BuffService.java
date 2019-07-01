package com.example.gamedemo.server.game.buff.service;

import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.buff.constant.BuffTypeEnum;
import com.example.gamedemo.server.game.buff.model.Buff;
import com.example.gamedemo.server.game.buff.resource.BuffResource;

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
   * @param creature
   * @param buff
   * @return
   */
  boolean addBuff(CreatureObject creature, Buff buff);

  /**
   * 获取buff的配置资源
   *
   * @param id
   * @return
   */
  BuffResource getBuffResourceById(Integer id);
}
