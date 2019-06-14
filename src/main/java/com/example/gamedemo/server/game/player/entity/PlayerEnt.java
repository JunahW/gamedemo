package com.example.gamedemo.server.game.player.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.server.game.player.model.Player;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
@Table
@javax.persistence.Entity
public class PlayerEnt implements Entity<Long> {
  @Id private Long id;

  @Column private String playerName;

  @Column private String accountId;

  @Column private int playerType;

  /** 玩家战力 */
  @Column private long combatIndex;

  /** x轴位置 */
  @Column private int x;

  /** y轴位置 */
  @Column private int y;

  /** */
  @Column private int mapId;

  /** 等级 */
  @Column private int level;

  /** 场景 */
  @Transient private Player player;

  /**
   * 获取player的存储对象
   *
   * @param player
   * @return
   */
  public static PlayerEnt valueOf(Player player) {
    PlayerEnt playerEnt = new PlayerEnt();
    playerEnt.setPlayer(player);
    playerEnt.setId(player.getId());
    return playerEnt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public int getPlayerType() {
    return playerType;
  }

  public void setPlayerType(int playerType) {
    this.playerType = playerType;
  }

  public long getCombatIndex() {
    return combatIndex;
  }

  public void setCombatIndex(long combatIndex) {
    this.combatIndex = combatIndex;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getMapId() {
    return mapId;
  }

  public void setMapId(int mapId) {
    this.mapId = mapId;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public Long getEntityId() {
    return this.id;
  }

  @Override
  public void setNullId() {
    this.player = null;
  }

  @Override
  public boolean serialize() {
    this.setId(player.getId());
    this.setPlayerName(player.getPlayerName());
    this.setAccountId(player.getAccountId());
    this.setPlayerType(player.getPlayerType());
    this.setCombatIndex(player.getCombatIndex());
    this.setX(player.getX());
    this.setY(player.getY());
    this.setMapId(player.getSceneId());
    this.setLevel(player.getLevel());
    return true;
  }

  @Override
  public boolean deSerialize() {
    Player player = new Player();
    player.setId(getId());
    player.setPlayerName(getPlayerName());
    player.setAccountId(getAccountId());
    player.setPlayerType(getPlayerType());
    player.setCombatIndex(getCombatIndex());
    player.setX(getX());
    player.setY(getY());
    player.setSceneId(getMapId());
    player.setLevel(getLevel());
    this.setPlayer(player);
    return true;
  }
}
