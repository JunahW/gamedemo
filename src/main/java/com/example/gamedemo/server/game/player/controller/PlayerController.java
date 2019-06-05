package com.example.gamedemo.server.game.player.controller;


import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.SpringContext;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.player.packet.*;
import org.springframework.stereotype.Component;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
@HandlerClass
public class PlayerController {

    /**
     * 通过id获取账户信息
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "getPlayer")
    public void getPlayerById(TSession session, CM_GetPlayer req) {
        //获取当前的账户信息
        Player player = session.getPlayer();
        String returnMsg = null;
        if (player == null) {
            returnMsg = "未选择角色\r\n";
        } else {
            returnMsg = player.toString() + "\r\n";
        }

        SessionManager.sendMessage(session, returnMsg);
    }

    /**
     * 创建用户
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "createPlayer")
    public void createPlayer(TSession session, CM_CreatePlayer req) {
        Account account = session.getAccount();
        Player player = new Player();
        player.setPlayerId(req.getPlayerId());
        player.setPlayerType(req.getPlayerType());
        player.setAccountId(account.getAccountId());
        int isSuccess = 0;
        try {
            isSuccess = SpringContext.getPlayerService().createPlayer(player);
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "创建玩家失败：错误码->" + e.getErrorCode() + "\r\n");
        }
        if (isSuccess == 1) {
            SessionManager.sendMessage(session, "创建角色成功\r\n");
        } else {
            SessionManager.sendMessage(session, "创建角色失败\r\n");
        }

    }

    /**
     * 选择角色
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "selectPlayer")
    public void selectPlayer(TSession session, CM_LoginAccount req) {

        Player player = null;
        try {
            player = SpringContext.getPlayerService().selectPlayer(req.getPlayerId());
        } catch (RequestException e) {
            SessionManager.sendMessage(session, "选择玩家失败：错误码->" + e.getErrorCode() + "\r\n");
        }
        String returnMsg = null;
        if (player == null) {
            returnMsg = "玩家不存在\r\n";
        } else {
            /**
             * 注册玩家
             */
            SessionManager.registerPlayer(session, player);
            returnMsg = "选择玩家： " + req.getPlayerId() + "\r\n";
        }
        SessionManager.sendMessage(session, returnMsg);
    }

    /**
     * 获取当前位置
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "where")
    public void getWhere(TSession session, CM_Location req) {
        Player player = session.getPlayer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(player.getSceneResource().getSceneName())
                .append("坐标(")
                .append(player.getX())
                .append(",")
                .append(player.getY())
                .append(")\r\n");
        SessionManager.sendMessage(session, stringBuilder.toString());
    }

    /**
     * 玩家移动到指定坐标
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "moveto")
    public void move2Coordinate(TSession session, CM_MovePosition req) {
        Player player = session.getPlayer();
        boolean isSuccess = SpringContext.getPlayerService().move2Coordinate(player, req.getX(), req.getY());
        if (isSuccess) {
            SessionManager.sendMessage(session, "移动成功\r\n");
        } else {
            SessionManager.sendMessage(session, "移动失败\r\n");
        }
    }
}
