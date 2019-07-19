package com.example.gamedemo.server.game.guild.model;

/**
 * @author wengj
 * @description
 * @date 2019/7/18
 */
public class GuildVo {
  /** id */
  private Long guildId;

  /** 公会名称 */
  private String guildName;

  public Long getGuildId() {
    return guildId;
  }

  public void setGuildId(Long guildId) {
    this.guildId = guildId;
  }

  public String getGuildName() {
    return guildName;
  }

  public void setGuildName(String guildName) {
    this.guildName = guildName;
  }
}
