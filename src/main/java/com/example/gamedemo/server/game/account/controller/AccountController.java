package com.example.gamedemo.server.game.account.controller;


import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.constant.SystemConstant;
import com.example.gamedemo.server.common.executer.WorkThreadPool;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.common.utils.ParameterCheckUtils;
import com.example.gamedemo.server.game.account.model.Account;
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
     * @param msg
     */
    @HandlerMethod(cmd = "get")
    public void getAccountById(TSession session, String msg) {
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
     * @param msg
     */
    @HandlerMethod(cmd = "create")
    public void setAccount(TSession session, String msg) {
        boolean flag = ParameterCheckUtils.checkParams(session, msg, 3);
        if (!flag) {
            return;
        }
        String[] msgs = msg.split(SystemConstant.SPLIT_TOKEN);
        Account account = new Account();
        account.setAcountId(msgs[1]);
        account.setAcountName(msgs[2]);
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
     * @param msg
     */
    @HandlerMethod(cmd = "login")
    public void login(TSession session, String msg) {
        boolean flag = ParameterCheckUtils.checkParams(session, msg, 2);
        if (!flag) {
            return;
        }
        String[] msgs = msg.split(SystemConstant.SPLIT_TOKEN);
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
        boolean flag = ParameterCheckUtils.checkParams(session, msg, 1);
        if (!flag) {
            return;
        }
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
     * @param msg
     */
    @HandlerMethod(cmd = "where")
    public void getWhere(TSession session, String msg) {
        boolean flag = ParameterCheckUtils.checkParams(session, msg, 1);
        if (!flag) {
            return;
        }
        Account account = session.getAccount();
        SessionManager.sendMessage(session, account.getScene().getSceneName() + "\r\n");
    }

    /**
     * 玩家移动到指定坐标
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "moveto")
    public void move2Coordinate(TSession session, String msg) {
        boolean flag = ParameterCheckUtils.checkParams(session, msg, 3);
        if (!flag) {
            return;
        }
        Account account = session.getAccount();
        String[] msgs = msg.split(SystemConstant.SPLIT_TOKEN);
        int x = Integer.parseInt(msgs[1]);
        int y = Integer.parseInt(msgs[2]);
        boolean isSuccess = accountService.move2Coordinate(account, x, y);
        if (isSuccess) {
            SessionManager.sendMessage(session, "移动成功\r\n");
        } else {
            SessionManager.sendMessage(session, "移动失败\r\n");
        }
    }
}
