package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 账户业务层接口
 */
public interface PlayerService {

  /**
   * 通过id获取账户信息
   *
   * @param playerId
   * @return
   */
  Player getPlayerById(String playerId);

  /**
   * 新增账户
   *
   * @param player
   * @return
   */
  int createPlayer(Player player);

  /**
   * 账户登录
   *
   * @param accountId
   * @param playerId
   * @return
   */
  Player selectPlayer(String accountId, Long playerId);

  /**
   * 更细账户
   *
   * @param player
   */
  void updateAccount(Player player);

  /**
   * 获取登陆账户的集合
   *
   * @return
   */
  List<Player> getAccountList();

  /**
   * 玩家移动
   *
   * @param player
   * @param x
   * @param y
   * @return
   */
  boolean move2Coordinate(Player player, int x, int y);

  /**
   * 玩家类型
   *
   * @param playerType
   * @return
   */
  BaseAttributeResource getBaseAttributeResourceByPlayerType(int playerType);

  /**
   * 获取玩家的属性集合，触发事件
   *
   * @param player
   * @param playerId
   * @return
   */
  ConcurrentMap<AttributeTypeEnum, Attribute> getPlayerAttrByPlayerId(Player player, Long playerId);

  /**
   * 计算玩家的职业基础属性
   *
   * @param event
   */
  void computePlayerBaseAttributes(PlayerLoadEvent event);

  /**
   * 保存玩家信息
   *
   * @param player
   */
  void savePlayerEnt(Player player);
}
