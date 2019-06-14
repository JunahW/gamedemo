package com.example.gamedemo.server.game.account.entity;

import com.example.gamedemo.common.ramcache.Entity;
import com.example.gamedemo.server.game.account.model.Account;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wengj
 * @description:账户信息
 * @date 2019/5/31
 */
@javax.persistence.Entity
@Table
public class AccountEnt implements Entity<String> {
  /** 账户id */
  @Id private String accountId;

  /** 账户名称 */
  @Column private String accountName;

  @Transient private Account account;

  @Override
  public String getEntityId() {
    return accountId;
  }

  @Override
  public void setNullId() {
    accountId = null;
  }

  @Override
  public boolean serialize() {
    this.setAccountId(account.getAccountId());
    this.setAccountName(account.getAccountName());
    return true;
  }

  @Override
  public boolean deSerialize() {
    Account account = new Account();
    account.setAccountId(accountId);
    account.setAccountName(accountName);
    this.setAccount(account);
    return true;
  }

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

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
