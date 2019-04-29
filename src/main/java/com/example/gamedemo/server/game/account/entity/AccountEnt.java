package com.example.gamedemo.server.game.account.entity; 
/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 
 */
public class AccountEnt {
    private String accountId;
    private byte[] accountData;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountData(byte[] accountData) {
        this.accountData = accountData;
    }

    public String getAccountId() {
        return accountId;
    }

    public byte[] getAccountData() {
        return accountData;
    }
}
