package com.example.gamedemo.server.game.mapinfo.service;

import com.example.gamedemo.server.game.mapinfo.entity.PlayerMapInfoEnt;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author wengj
 * @description:地图信息业务接口
 * @date 2019/7/23
 */
public interface PlayerMapInfoService {
  /**
   * 获取
   *
   * @param playerId
   * @return
   */
  PlayerMapInfoEnt getPlayerMapInfoEnt(Long playerId);

  /**
   * 保存
   *
   * @param player
   */
  void savePlayerMapInfoEnt(Player player);
}
