package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.server.game.account.model.Account;

/**
 * 账户业务层接口
 */
public interface AccountService {

    Account getAccountById(String accountId);

    void setAccount(Account account);

    int login(Account account);
}
