package com.example.gamedemo.server.game.account.controller;


import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.base.executor.WorkThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
@HandlerClass
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 通过id获取账户信息
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "get")
    public void getAccountById(TSession session, String msg) {
        //获取当前的账户信息
        Account account = session.getAccount();
        String returnMsg = account.toString() + "\r\n";
        SessionManager.sendMessage(session, returnMsg);
    }

    /**
     * 新增用户
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "create")
    public void setAccount(TSession session, String msg) {
        String[] msgs = msg.split(" ");
        Account account = new Account();
        account.setAcountId(msgs[1]);
        account.setAcountName(msgs[2]);
        accountService.setAccount(account);
    }

    /**
     * 用户登录
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "login")
    public void login(TSession session, String msg) {
        String[] msgs = msg.split(" ");
        String returnMsg = "";

        Account account = accountService.login(msgs[1]);
        if (account == null) {
            returnMsg = "用户不存在\r\n";
        } else {
            /**
             * 将登陆信息写会客户端
             */
            SessionManager.register(session, account);

            returnMsg = "loginSuccess " + msgs[1] + "\r\n";
        }
        SessionManager.sendMessage(session, returnMsg);
    }

    /**
     * 退出登录
     *
     * @param session
     * @param msg
     * @return
     */
    @HandlerMethod(cmd = "logout")
    public void logout(TSession session, String msg) {
        String returnMsg = null;
        Account account = session.getAccount();
        //异步保存用户信息
        WorkThreadPool.singleThreadSchedule(5000, () -> accountService.updateAccount(account));
        returnMsg = "注销登录\n";
        SessionManager.sendMessage(session, returnMsg);
        SessionManager.close(session.getChannel());
    }

}
