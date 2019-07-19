package com.example.gamedemo.server.game.guild.model;

import com.example.gamedemo.server.game.base.gameobject.GameObject;
import com.example.gamedemo.server.game.guild.constant.PositionTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author wengj
 * @description
 * @date 2019/7/18
 */
public class Guild extends GameObject {
  /** 公会名称 */
  private String guildName;
  /** 行会公告 */
  private String notice;
  /** 会长 */
  private Long president;
  /** 副会长 */
  private Long vicePresident;
  /** 成员集合 */
  private Map<Long, PositionTypeEnum> members = new HashMap<>();

  /** 申请列表 */
  private Set<Long> applySet = new TreeSet<>();

  /**
   * @param guildId
   * @param guildName
   * @param president
   * @return
   */
  public static Guild valueOf(Long guildId, String guildName, Long president) {
    Guild guild = new Guild();
    guild.setId(guildId);
    guild.setGuildName(guildName);
    guild.setPresident(president);
    return guild;
  }

  public String getGuildName() {
    return guildName;
  }

  public void setGuildName(String guildName) {
    this.guildName = guildName;
  }

  public Long getPresident() {
    return president;
  }

  public void setPresident(Long president) {
    this.president = president;
  }

  public Long getVicePresident() {
    return vicePresident;
  }

  public void setVicePresident(Long vicePresident) {
    this.vicePresident = vicePresident;
  }

  public Map<Long, PositionTypeEnum> getMembers() {
    return members;
  }

  public void setMembers(Map<Long, PositionTypeEnum> members) {
    this.members = members;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }

  public Set<Long> getApplySet() {
    return applySet;
  }

  public void setApplySet(Set<Long> applySet) {
    this.applySet = applySet;
  }

  /**
   * 新增成员
   *
   * @param playerId
   * @param positionType
   */
  public void addMember(Long playerId, PositionTypeEnum positionType) {
    members.put(playerId, positionType);
  }

  /**
   * 移除成员
   *
   * @param playerId
   */
  public void removeMember(Long playerId) {
    members.remove(playerId);
  }

  /**
   * 新增请求
   *
   * @param playerId
   */
  public void addApply(Long playerId) {
    applySet.add(playerId);
  }

  /**
   * 移除请求
   *
   * @param playerId
   */
  public void removeApply(Long playerId) {
    applySet.remove(playerId);
  }

  /**
   * 获取成员职位
   *
   * @param playerId
   * @return
   */
  public PositionTypeEnum getMemberPositionType(Long playerId) {
    return members.get(playerId);
  }
}
