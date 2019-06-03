package com.example.gamedemo.server.game.account.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.packet.CM_CreateAccount;
import com.example.gamedemo.server.game.account.packet.CM_LoginAccount;
import com.example.gamedemo.server.game.account.packet.CM_LogoutAccount;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/29
 */
@HandlerClass
@Component
public class AccountController {

    /**
     * 创建用户
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "createAccount")
    public void createAccount(TSession session, CM_CreateAccount req) {
        Account account = new Account();
        account.setAccountId(req.getAccountId());
        account.setAccountName(req.getAccountName());
        boolean flag = false;
        try {
            flag = SpringContext.getAccountService().createAccount(account);
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "创建账户失败：错误码->" + e.getErrorCode() + "\r\n");
        }

        if (flag) {
            SessionManager.sendMessage(session, "创建成功\r\n");
        } else {
            SessionManager.sendMessage(session, "创建失败\r\n");
        }
    }

    /**
     * 登陆账户
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "loginAccount")
    public void loginAccount(TSession session, CM_LoginAccount req) {
        Account account = null;
        try {
            account = SpringContext.getAccountService().loginAccount(req.getAccountId());
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "登陆账户失败：错误码->" + e.getErrorCode() + "\r\n");
        }
        if (account == null) {
            SessionManager.sendMessage(session, "登录失败\r\n");
        } else {
            SessionManager.registerAccount(session, account);
            SessionManager.sendMessage(session, "登录成功\r\n");
        }
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
        returnMsg = account.getAccountName() + "注销登录\r\n";
        SessionManager.sendMessage(session, returnMsg);
        SessionManager.close(session.getChannel());
    }
}
