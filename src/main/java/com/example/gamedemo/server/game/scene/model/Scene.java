package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.player.model.Player;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wengj
 * @description：场景模型
 * @date 2019/6/13
 */
public class Scene {
  /** 场景id */
  private int sceneResourceId;

  /** 场景玩家 */
  private ConcurrentHashMap<String, Player> playerMap;

  /** 场景怪物 */
  private ConcurrentHashMap<Long, Monster> monsterMap;

  public static Scene valueOf(int sceneResourceId) {
    Scene scene = new Scene();
    scene.setSceneResourceId(sceneResourceId);
    return scene;
  }

  public int getSceneResourceId() {
    return sceneResourceId;
  }

  public void setSceneResourceId(int sceneResourceId) {
    this.sceneResourceId = sceneResourceId;
  }

  public ConcurrentHashMap<String, Player> getPlayerMap() {
    return playerMap;
  }

  public void setPlayerMap(ConcurrentHashMap<String, Player> playerMap) {
    this.playerMap = playerMap;
  }

  public ConcurrentHashMap<Long, Monster> getMonsterMap() {
    return monsterMap;
  }

  public void setMonsterMap(ConcurrentHashMap<Long, Monster> monsterMap) {
    this.monsterMap = monsterMap;
  }

  /**
   * 进入场景
   *
   * @param player
   */
  public void enterScene(Player player) {
    playerMap.put(player.getPlayerId(), player);
  }

  /**
   * 离开场景
   *
   * @param playerId
   */
  public void leaveScene(String playerId) {
    playerMap.remove(playerId);
  }
}
