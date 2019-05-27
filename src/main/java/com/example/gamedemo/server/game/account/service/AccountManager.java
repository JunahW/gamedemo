package com.example.gamedemo.server.game.account.service;

import com.example.gamedemo.common.ramcache.anno.Inject;
import com.example.gamedemo.common.ramcache.service.EntityCacheService;
import com.example.gamedemo.server.game.account.entity.AccountEnt;
import com.example.gamedemo.server.game.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description:
 */
@Component
public class AccountManager {

    @Autowired
    private AccountService accountService;


    @Inject
    private EntityCacheService<String, AccountEnt> entEntityCacheService;

    /**
     * 所有账户
     */
    private static ConcurrentHashMap<String, Account> accountId2AccountMap = new ConcurrentHashMap<String, Account>();

    /**
     * 在线账户
     */
    private static ConcurrentHashMap<String, Account> loginAccountMap = new ConcurrentHashMap<String, Account>();

    /**
     * 通过id获取账户信息
     *
     * @param accountId
     * @return
     */
    public static Account getAccountById(String accountId) {
        return accountId2AccountMap.get(accountId);
    }

    /**
     * 通过ID获取已登录账户信息
     *
     * @param accountId
     * @return
     */
    public static Account getLoginAccountById(String accountId) {
        return loginAccountMap.get(accountId);
    }

    /**
     * 添加或者账户信息修改
     *
     * @param account
     */
    public static void setAccount(Account account) {
        if (null != account) {
            accountId2AccountMap.put(account.getAcountId(), account);
        }
    }

    /**
     * 新曾在线用户
     *
     * @param account
     */
    public static void setLoginAccount(Account account) {
        loginAccountMap.put(account.getAcountId(), account);
    }

    public static ConcurrentHashMap<String, Account> getAccountId2AccountMap() {
        return accountId2AccountMap;
    }

    public static ConcurrentHashMap<String, Account> getLoginAccountMap() {
        return loginAccountMap;
    }

    /**
     * 获取所有登陆用户
     *
     * @return
     */
    public static List<Account> getLoginAccountList() {
        LinkedList<Account> list = new LinkedList<Account>();
        Set<Map.Entry<String, Account>> entries = loginAccountMap.entrySet();
        Iterator<Map.Entry<String, Account>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }

        return list;
    }

    /**
     * 将用户信息加载到内存
     */
    @PostConstruct
    public void initAccountMap() {
        List<Account> accountList = accountService.getAccountList();
        for (Account account : accountList) {
            AccountManager.setAccount(account);
        }
    }
}
