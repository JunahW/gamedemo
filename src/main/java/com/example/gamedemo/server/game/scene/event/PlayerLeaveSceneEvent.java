package com.example.gamedemo.server.game.scene.event;

import com.example.gamedemo.common.event.Event;

/**
 * @author wengj
 * @description：玩家离开场景事件
 * @date 2019/6/20
 */
public class PlayerLeaveSceneEvent implements Event {
  /** 场景id */
  private int sceneId;

  /** 玩家id */
  private long playerId;

  /**
   * @param sceneId
   * @param playerId
   * @return
   */
  public static PlayerLeaveSceneEvent valueOf(int sceneId, long playerId) {
    PlayerLeaveSceneEvent event = new PlayerLeaveSceneEvent();
    event.setSceneId(sceneId);
    event.setPlayerId(playerId);
    return event;
  }

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

  public long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(long playerId) {
    this.playerId = playerId;
  }

  @Override
  public Object getOwnerId() {
    return null;
  }
}
