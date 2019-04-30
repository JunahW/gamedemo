package com.example.gamedemo.server.game.account.service;

import com.alibaba.fastjson.JSON;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import com.example.gamedemo.server.game.account.mapper.AccountMapper;
import com.example.gamedemo.server.game.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 账户业务实现层
 */
@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;


    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public Account getAccountById(String accountId) {
        logger.info("客户端查询accountId：" + accountId);
        return AccountManager.getAccountById(accountId);
    }

    @Override
    public void setAccount(Account account) {
        logger.info("新增用户：" + account);
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(account.getCountId());
        accountEnt.setAccountData(JSON.toJSONString(account));
        int result = accountMapper.addAcount(accountEnt);
        if (result == 1) {
            AccountManager.setAccount(account);
            logger.info("新增用户成功");
        } else {
            logger.info("新增用户失败");
        }

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
