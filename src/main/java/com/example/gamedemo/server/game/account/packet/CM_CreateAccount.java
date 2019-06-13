package com.example.gamedemo.server.game.account.packet;

/**
 * @author wengj
 * @description：创建用户
 * @date 2019/5/31
 */
public class CM_CreateAccount {
  /** 账号id */
  private String accountId;

  /** 账户名称 */
  private String accountName;

  public String getAccountId() {
    return accountId;
  }

  public String getAccountName() {
    return accountName;
  }
}
