package com.example.gamedemo.server.game.player.service;

import com.example.gamedemo.server.game.attribute.Attribute;
import com.example.gamedemo.server.game.attribute.constant.AttributeTypeEnum;
import com.example.gamedemo.server.game.player.event.PlayerLoadEvent;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.resource.BaseAttributeResource;

import java.util.List;
import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 玩家业务层接口
 */
public interface PlayerService {

  /**
   * 通过id获取账户信息
   *
   * @param playerId
   * @return
   */
  Player getPlayerById(long playerId);

  /**
   * 新增账户
   *
   * @param player
   * @return
   */
  boolean createPlayer(Player player);

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
  void updatePlayer(Player player);

  /**
   * 获取登陆账户的集合
   *
   * @return
   */
  List<Player> getPlayerList();

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
  Map<AttributeTypeEnum, Attribute> getPlayerAttrByPlayerId(Player player, Long playerId);

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
