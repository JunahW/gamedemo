package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.server.game.account.model.Account;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 账户业务层接口
 */
public interface AccountService {

    Account getAccountById(String accountId);

    int setAccount(Account account);

    Account login(String accountId);

    void updateAccount(Account account);
}
