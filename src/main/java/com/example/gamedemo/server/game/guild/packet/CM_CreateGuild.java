package com.example.gamedemo.server.game.guild.packet;

/**
 * @author wengj
 * @description：创建行会
 * @date 2019/7/18
 */
public class CM_CreateGuild {

  /** 公会id */
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
