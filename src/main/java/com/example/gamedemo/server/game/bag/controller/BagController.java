package com.example.gamedemo.server.game.bag.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description ：背包请求处理
 * @date 2019/5/22
 */
@Component
@HandlerClass
public class BagController {

    /**
     * 添加道具
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "addItem")
    public void addItem(TSession session, String msg) {
        Account account = session.getAccount();


        System.out.println("添加道具");
    }


    /**
     * 使用道具
     *
     * @param session
     * @param msg
     */
    public void userItem(TSession session, String msg) {

        Account account = session.getAccount();
        System.out.println("使用道具");
    }

    /**
     * 展示背包
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "showBag")
    public void showBag(TSession session, String msg) {

        Account account = session.getAccount();
        SessionManager.sendMessage(session, account.getBag() + "\r\n");
    }


}
