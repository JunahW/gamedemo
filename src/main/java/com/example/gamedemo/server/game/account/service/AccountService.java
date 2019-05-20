package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.server.game.account.model.Account;

import java.util.List;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 账户业务层接口
 */
public interface AccountService {

    /**
     * 通过id获取账户信息
     *
     * @param accountId
     * @return
     */
    Account getAccountById(String accountId);

    /**
     * 新增账户
     *
     * @param account
     * @return
     */
    int setAccount(Account account);

    /**
     * 账户登录
     *
     * @param accountId
     * @return
     */
    Account login(String accountId);

    /**
     * 更细账户
     *
     * @param account
     */
    void updateAccount(Account account);

    /**
     * 获取登陆账户的集合
     *
     * @return
     */
    List<Account> getAccountList();

    /**
     * 玩家移动
     *
     * @param account
     * @param x
     * @param y
     * @return
     */
    boolean move2Coordinate(Account account, int x, int y);


}
