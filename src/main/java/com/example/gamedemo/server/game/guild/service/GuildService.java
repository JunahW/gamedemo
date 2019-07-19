package com.example.gamedemo.server.game.guild.service;

import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author: wengj
 * @date: 2019/7/18
 * @description：公会业务接口
 */
public interface GuildService {
  /**
   * 创建公会
   *
   * @param player
   * @param guildId
   * @param guildName
   */
  void createGuild(Player player, Long guildId, String guildName);

  /**
   * 获取公会列表
   *
   * @param player
   */
  void getGuildList(Player player);

  /**
   * 申请加入
   *
   * @param player
   * @param guildId
   */
  void joinGuild(Player player, Long guildId);

  /**
   * 处理加入公会请求
   *
   * @param player
   * @param applyPlayerId
   * @param agree
   */
  void handleApply(Player player, Long applyPlayerId, boolean agree);

  /**
   * 退出公会
   *
   * @param player
   */
  void quitGuild(Player player);

  /**
   * 显示获取请求列表
   *
   * @param player
   */
  void showApply(Player player);

  /**
   * 查看成员列表
   *
   * @param player
   */
  void showMembers(Player player);
}
