package com.example.gamedemo.server.game.account.controller;


import com.example.gamedemo.server.common.constant.SessionAttributeKey;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.common.utils.AttributeUtils;
import com.example.gamedemo.server.game.account.constant.AccountCmd;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
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
    public String getAccountById(ChannelHandlerContext cxt, String msg) {
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());
        String returnMsg = account.toString() + "\r\n";
        cxt.channel().writeAndFlush(returnMsg);
        return returnMsg;
    }

    /**
     * 新增用户
     *
     * @param msg
     * @return
     */
    public int setAccount(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        Account account = new Account();
        account.setCountId(msgs[1]);
        account.setCountName(msgs[2]);
        accountService.setAccount(account);
        return 1;
    }

    /**
     * 用户登录
     *
     * @param msg
     * @return
     */
    public String login(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        String returnMsg = "";

        Account account = accountService.login(msgs[1]);
        if (account == null) {
            returnMsg = "用户不存在";
        } else {
            /**
             * 将登陆信息写会客户端
             */
            TSession tSession = AttributeUtils.get(cxt.channel(), SessionAttributeKey.SESSION);
            tSession.setAccount(account);

            returnMsg = "loginSuccess " + msgs[1];
        }
        cxt.channel().writeAndFlush(returnMsg + "\r\n");
        return returnMsg;
    }


}
