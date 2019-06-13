package com.example.gamedemo.server.game.account.model;

import com.example.gamedemo.server.game.account.entity.AccountEnt;

/**
 * @author wengj
 * @description:账户信息
 * @date 2019/5/31
 */
public class Account {
  /** 账户id */
  private String accountId;

  /** 账户名称 */
  private String accountName;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  /**
   * 获取账户的数据库对象
   *
   * @return
   */
  public AccountEnt getAccountEnt() {
    AccountEnt accountEnt = new AccountEnt();
    accountEnt.setAccountId(accountId);
    accountEnt.setAccountName(accountName);
    return accountEnt;
  }
}
