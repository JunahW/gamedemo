package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/7/10
 * @description: 副本业务接口
 */
public interface DungeonService {
  /**
   * 进入副本
   *
   * @param player
   * @param sceneId
   */
  void enterDungeon(Player player, int sceneId);

  /**
   * 离开副本
   *
   * @param player
   */
  void leaveDungeon(Player player);
}
