package com.example.gamedemo.server.game.guild.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.guild.constant.PositionTypeEnum;
import com.example.gamedemo.server.game.guild.model.Guild;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Map;
import java.util.Set;

/**
 * @author wengj
 * @description
 * @date 2019/7/18
 */
@javax.persistence.Entity
@Table
public class GuildEnt implements Entity<Long> {
  /** 公会主键 */
  @Id private Long id;

  /** 公会名称 */
  @Column private String guildName;

  /** 行会公告 */
  @Column private String notice;

  /** 会长 */
  @Column private Long president;

  /** 副会长 */
  @Column private Long vicePresident;

  /** 成员集合 */
  private String memberData;

  /** 申请列表 */
  private String applyData;

  /** 公会模型 */
  @Transient private Guild guild;

  /**
   * @param guild
   * @return
   */
  public static GuildEnt valueOf(Guild guild) {
    GuildEnt guildEnt = new GuildEnt();
    guildEnt.setGuild(guild);
    guildEnt.serialize();
    return guildEnt;
  }

  @Override
  public Long getEntityId() {
    return id;
  }

  @Override
  public void setNullId() {}

  @Override
  public boolean serialize() {
    setId(guild.getId());
    setGuildName(guild.getGuildName());
    setNotice(guild.getNotice());
    setPresident(guild.getPresident());
    setVicePresident(guild.getVicePresident());
    setMemberData(JsonUtils.serializeEntity(guild.getMembers()));
    setApplyData(JsonUtils.serializeEntity(guild.getApplySet()));
    return true;
  }

  @Override
  public boolean deSerialize() {
    guild = new Guild();
    guild.setId(getId());
    guild.setGuildName(getGuildName());
    guild.setNotice(getNotice());
    guild.setPresident(getPresident());
    guild.setVicePresident(getVicePresident());
    guild.setMembers(
        (Map<Long, PositionTypeEnum>)
            JsonUtils.deSerializeMap(
                Map.class, Long.class, PositionTypeEnum.class, getMemberData()));
    guild.setApplySet((Set<Long>) JsonUtils.deSerializeSet(Set.class, Long.class, getApplyData()));
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGuildName() {
    return guildName;
  }

  public void setGuildName(String guildName) {
    this.guildName = guildName;
  }

  public Guild getGuild() {
    return guild;
  }

  public void setGuild(Guild guild) {
    this.guild = guild;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
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

  public String getMemberData() {
    return memberData;
  }

  public void setMemberData(String memberData) {
    this.memberData = memberData;
  }

  public String getApplyData() {
    return applyData;
  }

  public void setApplyData(String applyData) {
    this.applyData = applyData;
  }
}
