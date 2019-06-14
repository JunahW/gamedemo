package com.example.gamedemo.server.game.player.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.PlayerAttributeContainer;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/4/25
 * @description: 用户账号
 */
public class Player extends SceneObject implements Serializable {

  /** 玩家名称 */
  private String playerName;

  /** 玩家所对应的账户id */
  private String accountId;

  /** 玩家的类型 */
  private int playerType;

  /** 玩家战力 */
  private long combatIndex;

  /** 等级 */
  private int level;

  /** 玩家属性容器 */
  private PlayerAttributeContainer playerAttributeContainer = new PlayerAttributeContainer(this);

  /** 场景 */
  private int sceneId;

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

  public int getSceneId() {
    return sceneId;
  }

  public void setSceneId(int sceneId) {
    this.sceneId = sceneId;
  }

  public long getCombatIndex() {
    return combatIndex;
  }

  public void setCombatIndex(long combatIndex) {
    this.combatIndex = combatIndex;
  }

  public int getPlayerType() {
    return playerType;
  }

  public void setPlayerType(int playerType) {
    this.playerType = playerType;
  }

  public PlayerAttributeContainer getPlayerAttributeContainer() {
    return playerAttributeContainer;
  }

  public void setPlayerAttributeContainer(PlayerAttributeContainer playerAttributeContainer) {
    this.playerAttributeContainer = playerAttributeContainer;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * 获取背包
   *
   * @return
   */
  @JsonIgnore
  public ItemStorage getPack() {
    ItemStorageEnt pack = SpringContext.getItemService().getItemStorageEntByAccountId(accountId);
    return pack.getItemStorage();
  }

  /**
   * 获取装备栏
   *
   * @return
   */
  @JsonIgnore
  public EquipStorage getEquipBar() {
    EquipStorageEnt equipStorageEnt =
        SpringContext.getEquipmentService().getEquipStorageEnt(super.getId());
    return equipStorageEnt.getEquipStorage();
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.PLAYER;
  }

  @Override
  public String toString() {
    return "Player{"
        + "playerName='"
        + playerName
        + '\''
        + ", accountId='"
        + accountId
        + '\''
        + ", playerType="
        + playerType
        + ", combatIndex="
        + combatIndex
        + ", level="
        + level
        + ", sceneId="
        + sceneId
        + '}';
  }
}
