package com.example.gamedemo.server.game.fight.service;

import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/7/3
 * @description: 战斗业务接口
 */
public interface FightService {
  /**
   * 使用技能
   *
   * @param player
   * @param index
   * @param targetId
   * @return
   */
  boolean useSkill(Player player, int index, Long targetId);
}
