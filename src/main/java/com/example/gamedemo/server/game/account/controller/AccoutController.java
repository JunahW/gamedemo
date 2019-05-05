package com.example.gamedemo.server.game.account.controller;

import com.example.gamedemo.server.game.account.constant.AccountCmd;
import com.example.gamedemo.server.game.account.mapper.AccountMapper;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountManager;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
public class AccoutController {


    @Autowired
    private AccountService accountService;


    {
        ControllerManager.add(AccountCmd.GET, this::getAccountById);
        ControllerManager.add(AccountCmd.LOGIN, this::login);
        ControllerManager.add(AccountCmd.CREATE, this::setAccount);
    }

    /**
     * 通过id获取账户信息
     *
     * @param msg
     * @return
     */
    public Account getAccountById(String msg) {
        return accountService.getAccountById(msg.split(" ")[0]);
    }

    /**
     * 新增用户
     *
     * @param msg
     * @return
     */
    public int setAccount(String msg) {
        String[] msgs = msg.split(" ");
        Account account = new Account();
        account.setCountId(msgs[2]);
        account.setCountName(msgs[3]);
        accountService.setAccount(account);
        return 1;
    }

    /**
     * 用户登录
     *
     * @param msg
     * @return
     */
    public String login(String msg) {
        String[] msgs = msg.split(" ");
        String returnMsg = "";

        Account account = accountService.login(msgs[2]);
        if (account == null) {
            returnMsg = "用户不存在";
        } else {
            /**
             * 将登陆信息写会客户端
             */
            returnMsg = "loginSuccess " + msgs[2];
        }
        return returnMsg;
    }


}
