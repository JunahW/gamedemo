package com.example.gamedemo.server.game.account.packet;

/**
 * @author wengj
 * @description：创建玩家消息
 * @date 2019/5/27
 */
public class CM_CreateAccount {

    /**
     * 账户id
     */
    private String accountId;

    /**
     * 账户名称
     */
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public String getAccountId() {
        return accountId;

    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
