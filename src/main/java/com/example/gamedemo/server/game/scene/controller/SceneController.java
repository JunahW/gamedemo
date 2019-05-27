package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.common.session.TSession;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.packet.CM_AoiScene;
import com.example.gamedemo.server.game.scene.packet.CM_GotoScene;
import com.example.gamedemo.server.game.scene.packet.CM_ListScene;
import com.example.gamedemo.server.game.scene.packet.CM_MoveScene;
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

    /**
     * 获取场景列表
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "list")
    public void getSceneList(TSession session, CM_ListScene req) {
        List<Scene> sceneList = sceneService.getSceneList();
        for (Scene scene : sceneList) {
            SessionManager.sendMessage(session, scene + "\r\n");
        }

    }

    /**
     * 传送金进入场景
     *
     * @param session
     * @param req
     */
    @HandlerMethod(cmd = "goto")
    public void gotoScene(TSession session, CM_GotoScene req) {

        //获取当前的账户信息
        Account account = session.getAccount();
        Scene scene = sceneService.getSceneById(req.getSceneId());
        sceneService.gotoScene(account, scene);
        String returnMsg = account.getAcountName() + "进入" + scene.getSceneName();
        SessionManager.sendMessage(session, returnMsg + "\r\n");
    }

    /**
     * 进入相邻的场景
     *
     * @param session
     * @param req
     * @return
     */
    @HandlerMethod(cmd = "move")
    public void move2Scene(TSession session, CM_MoveScene req) {
        String returnMsg = null;
        //获取当前的账户信息
        Account account = session.getAccount();
        Scene scene = sceneService.getSceneById(req.getSceneId());
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
     * @param req
     * @return
     */
    @HandlerMethod(cmd = "aoi")
    public void getSceneObject(TSession session, CM_AoiScene req) {
        //获取当前的账户信息
        Account account = session.getAccount();
        Scene scene = sceneService.getSceneById(account.getScene().getSceneId());
        SessionManager.sendMessage(session, scene.toString() + "\r\n");
    }

}
