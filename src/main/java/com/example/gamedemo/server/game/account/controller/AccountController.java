package com.example.gamedemo.server.game.account.controller;


import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.executer.WorkThreadPool;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.packet.*;
import com.example.gamedemo.server.game.account.service.AccountService;
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
     * @param req
     */
    @HandlerMethod(cmd = "get")
    public void getAccountById(TSession session, CM_GetAccount req) {
        //获取当前的账户信息
        Account account = session.getAccount();
        String returnMsg = null;
        if (account == null) {
            returnMsg = "玩家未登录\r\n";
        } else {
            returnMsg = account.toString() + "\r\n";
        }

        SessionManager.sendMessage(session, returnMsg);
    }

    /**
     * 新增用户
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "create")
    public void setAccount(TSession session, CM_CreateAccount req) {

        Account account = new Account();
        account.setAcountId(req.getAccountId());
        account.setAcountName(req.getAccountName());
        int isSuccess = accountService.setAccount(account);
        if (isSuccess == 1) {
            SessionManager.sendMessage(session, "注册成功\r\n");
        } else {
            SessionManager.sendMessage(session, "注册失败\r\n");
        }

    }

    /**
     * 用户登录
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "login")
    public void login(TSession session, CM_LoginAccount req) {

        Account account = accountService.login(req.getAccountId());
        String returnMsg = null;
        if (account == null) {
            returnMsg = "用户不存在\r\n";
        } else {
            /**
             * 将登陆信息写会客户端
             */
            SessionManager.register(session, account);

            returnMsg = "loginSuccess " + req.getAccountId() + "\r\n";
        }
        SessionManager.sendMessage(session, returnMsg);
    }

    /**
     * 退出登录
     *
     * @param session
     * @param req
     * @return
     */
    @HandlerMethod(cmd = "logout")
    public void logout(TSession session, CM_LogoutAccount req) {
        String returnMsg = null;
        Account account = session.getAccount();
        //异步保存用户信息
        WorkThreadPool.singleThreadSchedule(0, () -> accountService.updateAccount(account));
        returnMsg = "注销登录\r\n";
        SessionManager.sendMessage(session, returnMsg);
        SessionManager.close(session.getChannel());
    }

    /**
     * 获取当前位置
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "where")
    public void getWhere(TSession session, CM_Location req) {
        Account account = session.getAccount();
        SessionManager.sendMessage(session, account.getScene().getSceneName() + "\r\n");
    }

    /**
     * 玩家移动到指定坐标
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "moveto")
    public void move2Coordinate(TSession session, CM_MovePosition req) {
        Account account = session.getAccount();
        boolean isSuccess = accountService.move2Coordinate(account, req.getX(), req.getY());
        if (isSuccess) {
            SessionManager.sendMessage(session, "移动成功\r\n");
        } else {
            SessionManager.sendMessage(session, "移动失败\r\n");
        }
    }
}
