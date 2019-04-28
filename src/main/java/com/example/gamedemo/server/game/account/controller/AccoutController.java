package com.example.gamedemo.server.game.account.controller;

import com.example.gamedemo.server.game.account.constant.AccountCmd;
import com.example.gamedemo.server.game.account.model.Account;
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

    public Account getAccountById(String msg) {
        return accountService.getAccountById(msg.split(" ")[1]);
    }

    public int setAccount(String msg) {
        String[] msgs = msg.split(" ");
        Account account = new Account();
        account.setCountId(msgs[1]);
        account.setCountName(msgs[2]);
        accountService.setAccount(account);
        return 1;
    }

    public String login(String msg) {
        String[] msgs = msg.split(" ");
        String returnMsg = "";
        Account account = accountService.getAccountById(msgs[1]);
        if (null == account) {
            returnMsg = "用户不存在";
        } else {
            returnMsg = account.toString();
        }
        return returnMsg;
    }


}
