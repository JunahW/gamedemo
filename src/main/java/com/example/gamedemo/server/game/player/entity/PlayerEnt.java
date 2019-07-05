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

  @Column private String jobName;

  @Column private String accountId;

  @Column private int jobId;

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

  /** 经验 */
  private long exp;

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

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
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

  public long getExp() {
    return exp;
  }

  public void setExp(long exp) {
    this.exp = exp;
  }

  @Override
  public boolean serialize() {
    this.setId(player.getId());
    this.setJobName(player.getJobName());
    this.setAccountId(player.getAccountId());
    this.setJobId(player.getJobId());
    this.setCombatIndex(player.getCombatIndex());
    this.setX(player.getX());
    this.setY(player.getY());
    this.setMapId(player.getSceneId());
    this.setLevel(player.getLevel());
    this.setExp(player.getExp());
    return true;
  }

  @Override
  public boolean deSerialize() {
    Player player = new Player();
    player.setId(getId());
    player.setJobName(getJobName());
    player.setAccountId(getAccountId());
    player.setJobId(getJobId());
    player.setCombatIndex(getCombatIndex());
    player.setX(getX());
    player.setY(getY());
    player.setSceneId(getMapId());
    player.setLevel(getLevel());
    player.setExp(getExp());
    this.setPlayer(player);
    return true;
  }
}
