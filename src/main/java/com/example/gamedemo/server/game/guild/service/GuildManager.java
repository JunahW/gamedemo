package com.example.gamedemo.server.game.guild.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.server.game.guild.entity.GuildEnt;
import com.example.gamedemo.server.game.guild.model.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/7/18
 */
@Component
public class GuildManager {

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, GuildEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(GuildEnt.class);
    entityCacheService.setAccessor(accessor);
  }

  /**
   * 获取公会
   *
   * @param id
   * @return
   */
  public Guild getGuildById(Long id) {
    GuildEnt load = entityCacheService.load(id);
    if (load != null) {
      return load.getGuild();
    }
    return null;
  }

  /**
   * 保存
   *
   * @param guildEnt
   */
  public void saveGuildEnt(GuildEnt guildEnt) {
    entityCacheService.writeBack(guildEnt.getId(), guildEnt);
  }

  /**
   * 获取公会列表
   *
   * @return
   */
  public List<Guild> getGuildList() {
    List<GuildEnt> entityList = entityCacheService.getEntityList();
    LinkedList<Guild> guildList = new LinkedList<>();
    for (GuildEnt guildEnt : entityList) {
      guildList.add(guildEnt.getGuild());
    }
    return guildList;
  }
}
