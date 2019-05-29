package com.example.gamedemo.server.game.account.entity;

import com.example.gamedemo.common.ramcache.Entity;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
public class AccountEnt implements Entity<String> {
    private String accountId;
    private String accountData;

    public String getAccountId() {
        return accountId;
    }

    public String getAccountData() {
        return accountData;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setNullId() {

    }

    @Override
    public boolean doSerialize() {
        return false;
    }

    @Override
    public boolean doDeSerialize() {
        return false;
    }
}
