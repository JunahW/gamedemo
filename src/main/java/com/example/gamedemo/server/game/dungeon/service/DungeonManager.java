package com.example.gamedemo.server.game.dungeon.service;

import com.example.gamedemo.server.game.scene.model.Scene;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/12
 */
@Component
public class DungeonManager {
  /** 玩家 副本场景 */
  private Map<Long, Scene> playerIdSceneMap = new HashMap<>();

  /**
   * 获取副本信息
   *
   * @param playerId
   * @return
   */
  public Scene getSceneByPlayerId(Long playerId) {
    return playerIdSceneMap.get(playerId);
  }

  /**
   * 移除副本信息
   *
   * @param playerId
   */
  public void removeScene(Long playerId) {
    playerIdSceneMap.remove(playerId);
  }

  /**
   * 新增玩家 副本信息
   *
   * @param playerId
   * @param scene
   */
  public void putScene(Long playerId, Scene scene) {
    playerIdSceneMap.put(playerId, scene);
  }
}
