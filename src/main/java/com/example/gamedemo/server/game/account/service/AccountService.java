package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.player.model.Player;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
public interface AccountService {

  /**
   * 创建账户
   *
   * @param account
   * @return
   */
  boolean createAccount(Account account);

  /**
   * 账户登录
   *
   * @param accountId
   * @return
   */
  Account loginAccount(String accountId);

  /**
   * 退出登录
   *
   * @param player
   */
  void logoutAccount(Player player);
}
