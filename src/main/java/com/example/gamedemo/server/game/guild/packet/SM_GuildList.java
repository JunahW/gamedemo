package com.example.gamedemo.server.game.guild.packet;

import com.example.gamedemo.server.game.guild.model.Guild;
import com.example.gamedemo.server.game.guild.model.GuildVo;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wengj
 * @description：公会列表
 * @date 2019/7/18
 */
public class SM_GuildList {

  /** 公会列表 */
  private List<GuildVo> guilds;

  public static SM_GuildList valueOf(List<Guild> guilds) {
    SM_GuildList sm_guildList = new SM_GuildList();
    LinkedList<GuildVo> guildVos = new LinkedList<>();
    for (Guild guild : guilds) {
      GuildVo guildVo = new GuildVo();
      guildVo.setGuildId(guild.getId());
      guildVo.setGuildName(guild.getGuildName());
      guildVos.add(guildVo);
    }
    sm_guildList.setGuilds(guildVos);
    return sm_guildList;
  }

  public List<GuildVo> getGuilds() {
    return guilds;
  }

  public void setGuilds(List<GuildVo> guilds) {
    this.guilds = guilds;
  }
}
