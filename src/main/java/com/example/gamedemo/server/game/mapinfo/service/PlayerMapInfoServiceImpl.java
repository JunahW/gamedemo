package com.example.gamedemo.server.game.mapinfo.service;

import com.example.gamedemo.server.game.mapinfo.entity.PlayerMapInfoEnt;
import com.example.gamedemo.server.game.player.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wengj
 * @description
 * @date 2019/7/23
 */
@Service
public class PlayerMapInfoServiceImpl implements PlayerMapInfoService {

  @Autowired private PlayerMapInfoManager playerMapInfoManager;

  @Override
  public PlayerMapInfoEnt getPlayerMapInfoEnt(Long playerId) {
    return playerMapInfoManager.getPlayerMapInfoEnt(playerId);
  }

  @Override
  public void savePlayerMapInfoEnt(Player player) {
    playerMapInfoManager.savePlayerMapInfoEnt(getPlayerMapInfoEnt(player.getId()));
  }
}
