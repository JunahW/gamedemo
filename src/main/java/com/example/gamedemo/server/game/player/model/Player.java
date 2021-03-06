package com.example.gamedemo.server.game.player.model;

import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.attribute.PlayerAttributeContainer;
import com.example.gamedemo.server.game.bag.entity.ItemStorageEnt;
import com.example.gamedemo.server.game.bag.storage.ItemStorage;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.base.cooldown.CoolDownComponent;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.equip.entity.EquipStorageEnt;
import com.example.gamedemo.server.game.equip.storage.EquipStorage;
import com.example.gamedemo.server.game.skill.entity.SkillStorageEnt;
import com.example.gamedemo.server.game.skill.storage.SkillStorage;
import com.example.gamedemo.server.game.task.entity.TaskStorageEnt;
import com.example.gamedemo.server.game.task.storage.TaskStorage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author: wengj
 * @date: 2019/4/25
 * @description: 玩家
 */
public class Player extends CreatureObject<Player> implements Serializable {

  /** 玩家名称 */
  private String jobName;
  /** 玩家所对应的账户id */
  private String accountId;
  /** 玩家的类型 */
  private int jobId;
  /** 玩家战力 */
  private long combatIndex;
  /** 等级 */
  private int level;
  /** 玩家属性容器 */
  // private PlayerAttributeContainer playerAttributeContainer = new PlayerAttributeContainer(this);

  /** 进入副本前的场景id */
  private int beforeSceneId;

  /** 公会id */
  private Long guild;

  /** cd组件 */
  private CoolDownComponent cdComponent = new CoolDownComponent();

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

  public long getCombatIndex() {
    return combatIndex;
  }

  public void setCombatIndex(long combatIndex) {
    this.combatIndex = combatIndex;
  }

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  @Override
  public PlayerAttributeContainer getAttributeContainer() {
    if (super.getAttributeContainer() == null) {
      super.setAttributeContainer(new PlayerAttributeContainer(this));
    }
    return (PlayerAttributeContainer) super.getAttributeContainer();
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public CoolDownComponent getCdComponent() {
    return cdComponent;
  }

  public void setCdComponent(CoolDownComponent cdComponent) {
    this.cdComponent = cdComponent;
  }

  public int getBeforeSceneId() {
    return beforeSceneId;
  }

  public void setBeforeSceneId(int beforeSceneId) {
    this.beforeSceneId = beforeSceneId;
  }

  public Long getGuild() {
    return guild;
  }

  public void setGuild(Long guild) {
    this.guild = guild;
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

  /**
   * 技能栏
   *
   * @return
   */
  @JsonIgnore
  public SkillStorage getSkillStorage() {
    SkillStorageEnt skillStorageEnt =
        SpringContext.getSkillService().getSkillStorageEnt(super.getId());
    return skillStorageEnt.getSkillStorage();
  }

  /**
   * 任务栏
   *
   * @return
   */
  @JsonIgnore
  public TaskStorage getTaskStorage() {
    TaskStorageEnt taskStorageEnt = SpringContext.getTaskService().getTaskStorageEnt(super.getId());
    return taskStorageEnt.getTaskStorage();
  }

  @Override
  public SceneObjectTypeEnum getSceneObjectType() {
    return SceneObjectTypeEnum.PLAYER;
  }

  @Override
  public String toString() {
    return "Player{"
        + "jobName='"
        + jobName
        + '\''
        + ", accountId='"
        + accountId
        + '\''
        + ", jobId="
        + jobId
        + ", combatIndex="
        + combatIndex
        + ", level="
        + level
        + ", sceneId="
        + super.getSceneId()
        + '}';
  }
}
