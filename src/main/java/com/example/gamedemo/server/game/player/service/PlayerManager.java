package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.common.ramcache.orm.Accessor;
import com.example.gamedemo.common.ramcache.service.EntityCacheServiceImpl;
import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.player.entity.PlayerEnt;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;
import com.example.gamedemo.server.game.player.resource.PlayerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description:
 */
@Component
public class PlayerManager {

  private Map<Integer, PlayerResource> playerResource =
      ResourceManager.getResourceMap(PlayerResource.class);

  private Map<Integer, BaseAttributeResource> baseAttributeResource =
      ResourceManager.getResourceMap(BaseAttributeResource.class);

  @Autowired private Accessor accessor;

  private EntityCacheServiceImpl<Long, PlayerEnt> entityCacheService =
      new EntityCacheServiceImpl<>();

  @PostConstruct
  public void init() {
    entityCacheService.setClazz(PlayerEnt.class);
    entityCacheService.setAccessor(accessor);
  }

  /**
   * 获取玩家配置信息
   *
   * @param modeId
   * @return
   */
  public PlayerResource getPlayerResourceById(int modeId) {
    return playerResource.get(modeId);
  }

  /**
   * 获取玩家的基础属性
   *
   * @param playerType
   * @return
   */
  public BaseAttributeResource getAttributeResourceByPlayerType(int playerType) {
    return baseAttributeResource.get(playerType);
  }

  /**
   * 获取玩家信息
   *
   * @param playerId
   * @return
   */
  public PlayerEnt getPlayerEntByPlayerId(Long playerId) {
    return entityCacheService.load(playerId);
  }

  /**
   * 保存玩家信息
   *
   * @param playerEnt
   */
  public void savePlayerEnt(PlayerEnt playerEnt) {
    entityCacheService.writeBack(playerEnt.getId(), playerEnt);
  }

  /**
   * 获取玩家列表
   *
   * @return
   */
  public List<Player> getPlayerList() {
    List<PlayerEnt> entityList = entityCacheService.getEntityList();
    LinkedList<Player> players = new LinkedList<>();
    for (PlayerEnt playerEnt : entityList) {
      players.add(playerEnt.getPlayer());
    }
    return players;
  }
}
