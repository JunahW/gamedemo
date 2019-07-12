package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;

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

  /**
   * 获取副本地图
   *
   * @param playerId
   * @return
   */
  Scene getDungeonSceneByPlayerId(Long playerId);
}
