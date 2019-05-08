package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
@HandlerClass
public class SceneController {
    @Autowired
    private SceneService sceneService;

    @Autowired
    private AccountService accountService;

    /**
     * 获取场景列表
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "list")
    public void getSceneList(TSession session, String msg) {
        List<Scene> sceneList = sceneService.getSceneList();
        SessionManager.sendMessage(session, sceneList + "\r\n");
    }

    /**
     * 传送金进入场景
     *
     * @param session
     * @param msg
     */
    @HandlerMethod(cmd = "goto")
    public void gotoScene(TSession session, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = session.getAccount();
        Scene scene = sceneService.getSceneById(msgs[1]);
        sceneService.gotoScene(account, scene);
        String returnMsg = account.getAcountName() + "进入" + scene.getSceneName();
        SessionManager.sendMessage(session, returnMsg + "\r\n");
    }

    /**
     * 进入相邻的场景
     *
     * @param session
     * @param msg
     * @return
     */
    @HandlerMethod(cmd = "move")
    public void move2Scene(TSession session, String msg) {
        String returnMsg = null;
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = session.getAccount();
        Scene scene = sceneService.getSceneById(msgs[1]);
        if (null == scene) {
            SessionManager.sendMessage(session, "该场景不存在\r\n");
            return;
        }
        int isSuccess = sceneService.move2Scene(account, scene);
        if (isSuccess == 0) {
            returnMsg = account.getAcountName() + "进入" + scene.getSceneName() + "失败";
        } else {
            returnMsg = account.getAcountName() + "进入" + scene.getSceneName();
        }
        SessionManager.sendMessage(session, returnMsg + "\r\n");
    }

    /**
     * 获取当前场景下的实体
     *
     * @param session
     * @param msg
     * @return
     */
    @HandlerMethod(cmd = "aoi")
    public void getSceneObject(TSession session, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = session.getAccount();
        Scene scene = sceneService.getSceneById(account.getScene().getSceneId());
        SessionManager.sendMessage(session, scene.toString() + "\r\n");
    }

}
