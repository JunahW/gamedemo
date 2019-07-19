package com.example.gamedemo.server.game.guild.packet;

/**
 * @author wengj
 * @description：加入公会
 * @date 2019/7/18
 */
public class CM_JoinGuild {
  /** 公会id */
  private Long guildId;

  public Long getGuildId() {
    return guildId;
  }

  public void setGuildId(Long guildId) {
    this.guildId = guildId;
  }
}
