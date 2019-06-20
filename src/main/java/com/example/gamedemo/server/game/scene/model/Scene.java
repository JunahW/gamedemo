package com.example.gamedemo.server.game.scene.model;

import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.npc.model.Npc;
import com.example.gamedemo.server.game.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wengj
 * @description：场景模型
 * @date 2019/6/13
 */
public class Scene {
  private static final Logger logger = LoggerFactory.getLogger(Scene.class);
  /** 场景id */
  private int sceneResourceId;

  /** 场景玩家 */
  private Map<Long, Player> playerMap = new HashMap<>();

  /** 场景怪物 */
  private Map<Long, Monster> monsterMap = new HashMap<>();

  /** 场景中的npc */
  private Map<Long, Npc> npcMap = new HashMap<>();

  public Scene(int sceneResourceId) {
    this.sceneResourceId = sceneResourceId;
    /** 启动定时器 */
    this.statMonsterTimer();
  }

  public static Scene valueOf(int sceneResourceId) {
    Scene scene = new Scene(sceneResourceId);
    return scene;
  }

  public int getSceneResourceId() {
    return sceneResourceId;
  }

  public void setSceneResourceId(int sceneResourceId) {
    this.sceneResourceId = sceneResourceId;
  }

  public Map<Long, Player> getPlayerMap() {
    return playerMap;
  }

  public void setPlayerMap(Map<Long, Player> playerMap) {
    this.playerMap = playerMap;
  }

  public Map<Long, Monster> getMonsterMap() {
    return monsterMap;
  }

  public void setMonsterMap(Map<Long, Monster> monsterMap) {
    this.monsterMap = monsterMap;
  }

  public Map<Long, Npc> getNpcMap() {
    return npcMap;
  }

  public void setNpcMap(Map<Long, Npc> npcMap) {
    this.npcMap = npcMap;
  }

  /**
   * 进入场景
   *
   * @param player
   */
  public void enterScene(Player player) {
    playerMap.put(player.getId(), player);
  }

  /**
   * 离开场景
   *
   * @param playerId
   */
  public void leaveScene(Long playerId) {
    playerMap.remove(playerId);
  }

  /**
   * 新增npc
   *
   * @param npc
   */
  public void putNpc(Npc npc) {
    npcMap.put(npc.getId(), npc);
  }

  /**
   * 移除npc
   *
   * @param npcId
   */
  public void removeNpc(Long npcId) {
    npcMap.remove(npcId);
  }

  /**
   * 新增怪物
   *
   * @param monster
   */
  public void putMonster(Monster monster) {
    monsterMap.put(monster.getId(), monster);
  }

  /**
   * 移除怪物
   *
   * @param id
   */
  public void removeMonster(Integer id) {
    monsterMap.remove(id);
  }

  public void statMonsterTimer() {
    SceneExecutor.addScheduleTask(
        sceneResourceId,
        0,
        1,
        TimeUnit.SECONDS,
        new Runnable() {
          @Override
          public void run() {
            logger.info("周期执行[{}]", sceneResourceId);
          }
        });
  }
}
