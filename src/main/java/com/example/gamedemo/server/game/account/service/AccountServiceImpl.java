package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import com.example.gamedemo.server.game.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description:账户业务层
 * @date 2019/4/29
 */
@Component
public class AccountServiceImpl implements AccountService {
  private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

  @Autowired private AccountManager accountManager;

  @Override
  public boolean createAccount(Account account) {
    // 判断用户是否存在
    String accountId = account.getAccountId();
    AccountEnt load = accountManager.getAccountEntByAccountId(accountId);
    if (load != null) {
      logger.info("[{}]该账户已存在", load.getAccountName());
      RequestException.throwException(I18nId.ACCOUNT_EXIST);
    }
    AccountEnt accountEnt = new AccountEnt();
    accountEnt.setAccount(account);
    accountManager.saveAccountEnt(accountEnt);
    logger.info("[{}]创建成功", accountEnt.getAccountName());
    return true;
  }

  @Override
  public Account loginAccount(String accountId) {
    AccountEnt accountEnt = accountManager.getAccountEntByAccountId(accountId);
    if (accountEnt != null) {
      accountEnt.deSerialize();
      Account account = accountEnt.getAccount();
      logger.info("[{}]登陆成功", accountId);
      return account;
    } else {
      logger.info("[{}]账户不存在", accountId);
      RequestException.throwException(I18nId.ACCOUNT_NO_EXIST);
    }
    return null;
  }
}
