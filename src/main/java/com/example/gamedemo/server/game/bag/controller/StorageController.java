package com.example.gamedemo.server.game.bag.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.bag.packet.*;
import com.example.gamedemo.server.game.bag.service.ItemService;
import com.example.gamedemo.server.game.player.model.Player;
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
        Player player = session.getPlayer();
        boolean addItem = false;
        try {
            addItem = itemService.addItem(player, req.getItemResourceId());
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "新增道具失败：错误码->" + e.getErrorCode() + "\r\n");
        }
        String returnMsg = null;
        if (addItem) {
            returnMsg = "添加成功";
        } else {
            returnMsg = "添加失败";
        }
        SessionManager.sendMessage(session, returnMsg + "\r\n");
    }


    /**
     * 使用道具
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "useItem")
    public void userItem(TSession session, CM_UseItem req) {

        Player player = session.getPlayer();
        boolean useItem = false;
        try {
            useItem = itemService.useItem(player, req.getGuid(), 1);
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "使用道具失败：错误码->" + e.getErrorCode() + "\r\n");
        }
        String returnMsg = null;
        if (useItem) {
            returnMsg = "使用成功";
        } else {
            returnMsg = "使用失败";
        }
        SessionManager.sendMessage(session, returnMsg + "\r\n");
    }

    /**
     * 展示背包
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "showBag")
    public void showBag(TSession session, CM_ShowStorage req) {
        Player player = session.getPlayer();
        SessionManager.sendMessage(session, "背包数据" + player.getPack() + "\r\n");
    }

    /**
     * 查询某个物品数量
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "getItemNum")
    public void getItemNum(TSession session, CM_GetItemNum req) {
        Player player = session.getPlayer();
        int itemNum = 0;
        try {
            itemNum = itemService.getItemNum(player, req.getGuid());
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "查看道具数量失败：错误码->" + e.getErrorCode() + "\r\n");
        }
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
        Player player = session.getPlayer();


    }

    /**
     * 检查背包是否已满
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "checkBag")
    public void checkBag(TSession session, CM_CheckStorage req) {
        Player player = session.getPlayer();
        int bagNum = itemService.checkBag(player);
        SessionManager.sendMessage(session, "把背包还剩容量：" + bagNum + "\r\n");

    }

}
