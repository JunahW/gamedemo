package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.common.anno.HandlerClass;
import com.example.gamedemo.common.anno.HandlerMethod;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.common.session.TSession;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.packet.CM_AoiScene;
import com.example.gamedemo.server.game.scene.packet.CM_GotoScene;
import com.example.gamedemo.server.game.scene.packet.CM_ListScene;
import com.example.gamedemo.server.game.scene.packet.CM_MoveScene;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
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
        List<SceneResource> sceneResourceList = sceneService.getSceneList();
        for (SceneResource sceneResource : sceneResourceList) {
            SessionManager.sendMessage(session, sceneResource.getSceneName() + sceneResource.getSceneId() + "\r\n");
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
        Player player = session.getPlayer();
        SceneResource sceneResource = sceneService.getSceneById(req.getSceneId());
        sceneService.gotoScene(player, sceneResource);
        String returnMsg = player.getPlayerName() + "进入" + sceneResource.getSceneName();
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
        Player player = session.getPlayer();
        SceneResource sceneResource = sceneService.getSceneById(req.getSceneId());
        if (null == sceneResource) {
            SessionManager.sendMessage(session, "该场景不存在\r\n");
            return;
        }
        int isSuccess = sceneService.move2Scene(player, sceneResource);
        if (isSuccess == 0) {
            returnMsg = player.getPlayerName() + "进入" + sceneResource.getSceneName() + "失败";
        } else {
            returnMsg = player.getPlayerName() + "进入" + sceneResource.getSceneName();
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
        Player player = session.getPlayer();
        SceneResource sceneResource = sceneService.getSceneById(player.getSceneResource().getSceneId());
        SessionManager.sendMessage(session, sceneResource.toString() + "\r\n");
    }

}
