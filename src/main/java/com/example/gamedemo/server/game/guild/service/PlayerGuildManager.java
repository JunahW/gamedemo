package com.example.gamedemo.server.game.guild.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.server.game.guild.entity.PlayerGuildEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wengj
 * @description:玩家公会信息
 * @date 2019/7/19
 */
@Component
public class PlayerGuildManager {

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, PlayerGuildEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(PlayerGuildEnt.class);
    entityCacheService.setAccessor(accessor);
  }

  /**
   * 获取
   *
   * @param playerId
   * @return
   */
  public PlayerGuildEnt getPlayerGuildEnt(Long playerId) {
    return entityCacheService.loadOrCreate(
        playerId,
        new EntityBuilder<Long, PlayerGuildEnt>() {
          @Override
          public PlayerGuildEnt newInstance(Long id) {
            PlayerGuildEnt playerGuildEnt = new PlayerGuildEnt();
            playerGuildEnt.setPlayerId(playerId);
            return playerGuildEnt;
          }
        });
  }

  /**
   * 保存
   *
   * @param playerGuildEnt
   */
  public void savePlayerGuildEnt(PlayerGuildEnt playerGuildEnt) {
    entityCacheService.writeBack(playerGuildEnt.getEntityId(), playerGuildEnt);
  }
}
