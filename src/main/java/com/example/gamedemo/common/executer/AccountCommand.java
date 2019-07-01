package com.example.gamedemo.common.executer;

/**
 * @author wengj
 * @description
 * @date 2019/7/1
 */
public class AccountCommand extends AbstractCommand {
  /** 账户id */
  private String acountId;

  public AccountCommand(String acountId) {
    this.acountId = acountId;
  }

  @Override
  public void doAction() {}

  @Override
  public int modIndex(int poolSize) {
    return getAcountId().hashCode() % poolSize;
  }

  public String getAcountId() {
    return acountId;
  }

  public void setAcountId(String acountId) {
    this.acountId = acountId;
  }
}
