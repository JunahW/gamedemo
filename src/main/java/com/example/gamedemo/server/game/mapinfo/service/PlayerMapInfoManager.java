package com.example.gamedemo.server.game.mapinfo.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityBuilder;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.server.game.mapinfo.entity.PlayerMapInfoEnt;
import com.example.gamedemo.server.game.mapinfo.model.PlayerMapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wengj
 * @description
 * @date 2019/7/23
 */
@Component
public class PlayerMapInfoManager {
  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, PlayerMapInfoEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(PlayerMapInfoEnt.class);
    entityCacheService.setAccessor(accessor);
  }

  /**
   * 获取
   *
   * @param playerId
   * @return
   */
  public PlayerMapInfoEnt getPlayerMapInfoEnt(Long playerId) {
    PlayerMapInfoEnt playerMapInfoEnt =
        entityCacheService.loadOrCreate(
            playerId,
            new EntityBuilder<Long, PlayerMapInfoEnt>() {
              @Override
              public PlayerMapInfoEnt newInstance(Long id) {
                PlayerMapInfoEnt playerMapInfoEnt = new PlayerMapInfoEnt();
                PlayerMapInfo playerMapInfo = new PlayerMapInfo();
                playerMapInfoEnt.setPlayerMapInfo(playerMapInfo);
                playerMapInfoEnt.setId(playerId);
                return playerMapInfoEnt;
              }
            });
    return playerMapInfoEnt;
  }

  /**
   * 保存
   *
   * @param playerMapInfoEnt
   */
  public void savePlayerMapInfoEnt(PlayerMapInfoEnt playerMapInfoEnt) {
    entityCacheService.writeBack(playerMapInfoEnt.getId(), playerMapInfoEnt);
  }
}
