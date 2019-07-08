package com.example.gamedemo.common.executer.account.impl;

import com.example.gamedemo.common.executer.AbstractCommand;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public abstract class AbstractAccountCommand extends AbstractCommand {
  /** 账户id */
  private String accountId;

  public AbstractAccountCommand(String accountId) {
    this.accountId = accountId;
  }

  @Override
  public void doAction() {}

  @Override
  public int modIndex(int poolSize) {
    return getAccountId().hashCode() % poolSize;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
}
