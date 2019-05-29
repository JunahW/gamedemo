package com.example.gamedemo.server.game.bag.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.bag.packet.*;
import com.example.gamedemo.server.game.bag.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description ：背包请求处理
 * @date 2019/5/22
 */
@Component
@HandlerClass
public class StorageController {

    @Autowired
    private ItemService itemService;

    /**
     * 添加道具
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "addItem")
    public void addItem(TSession session, CM_AddItem req) {
        Account account = session.getAccount();
        itemService.addItem(account, req.getItemResourceId());
        System.out.println("添加道具");
    }


    /**
     * 使用道具
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "useItem")
    public void userItem(TSession session, CM_UseItem req) {

        Account account = session.getAccount();
        itemService.useItem(account, req.getGuid(), 1);
        System.out.println("使用道具");
    }

    /**
     * 展示背包
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "showBag")
    public void showBag(TSession session, CM_ShowStorage req) {

        Account account = session.getAccount();

        SessionManager.sendMessage(session, "背包数据" + account.getPack() + "\r\n");
    }

    /**
     * 查询某个物品数量
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "getItemNum")
    public void getItemNum(TSession session, CM_GetItemNum req) {
        Account account = session.getAccount();

        int itemNum = itemService.getItemNum(account, req.getGuid());

        SessionManager.sendMessage(session, "数量还有：" + itemNum + "\r\n");

    }

    /**
     * 移除道具
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "removeItem")
    public void removeItem(TSession session, CM_RemoveItem req) {
        Account account = session.getAccount();

    }

    @HandlerMethod(cmd = "checkBag")
    public void checkBag(TSession session, CM_CheckStorage req) {
        Account account = session.getAccount();
        int bagNum = itemService.checkBag(account);

        SessionManager.sendMessage(session, "把背包还剩容量：" + bagNum + "\r\n");

    }

}
