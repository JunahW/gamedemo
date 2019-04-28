package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.server.game.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 账户业务实现层
 */
@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {


    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public Account getAccountById(String accountId) {
        logger.info("客户端查询accountId：" + accountId);
        return AccountManager.getAccountById(accountId);
    }

    @Override
    public void setAccount(Account account) {
        logger.info("新增/修改用户：" + account);
        AccountManager.setAccount(account);
    }

    @Override
    public int login(Account account) {
        Account existAccount = AccountManager.getAccountById(account.getCountId());
        if (null != existAccount) {
            logger.info(account + "登录游戏");
            return 1;
        }
        logger.info(account + "登录失败");
        return 0;
    }
}
