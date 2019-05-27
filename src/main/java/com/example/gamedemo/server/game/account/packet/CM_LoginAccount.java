package com.example.gamedemo.server.game.account.packet;

/**
 * @author wengj
 * @description:登陆消息
 * @date 2019/5/27
 */
public class CM_LoginAccount {
    /**
     * 账户id
     */
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
