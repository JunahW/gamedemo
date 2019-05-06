package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import com.example.gamedemo.server.game.scene.constant.SceneCmd;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.service.SceneService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@Component
public class SceneController {
    @Autowired
    private SceneService sceneService;

    @Autowired
    private AccountService accountService;


    {
        ControllerManager.add(SceneCmd.GOTO, this::gotoScene);
        ControllerManager.add(SceneCmd.LIST, this::getSceneList);
        ControllerManager.add(SceneCmd.MOVE, this::move2Scene);
        ControllerManager.add(SceneCmd.AOI, this::getSceneObject);
    }

    /**
     * 获取场景列表
     *
     * @param msg
     * @return
     */
    public List<Scene> getSceneList(ChannelHandlerContext cxt, String msg) {
        return sceneService.getSceneList();
    }


    /**
     * 传送金进入场景
     *
     * @param msg
     * @return
     */
    public String gotoScene(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());

        Scene scene = sceneService.getSceneById(msgs[1]);
        sceneService.gotoScene(account, scene);
        String returnMsg = account.getCountName() + "进入" + scene.getSceneName();
        cxt.writeAndFlush(returnMsg + "\r\n");
        return returnMsg;
    }

    /**
     * 进入相邻的场景
     *
     * @param msg
     * @return
     */
    public String move2Scene(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());
        Scene scene = sceneService.getSceneById(msgs[1]);
        String returnMsg = null;
        int isSuccess = sceneService.move2Scene(account, scene);
        if (isSuccess == 0) {
            returnMsg = account.getCountName() + "进入" + scene.getSceneName() + "失败";
        } else {
            returnMsg = account.getCountName() + "进入" + scene.getSceneName();
        }
        cxt.writeAndFlush(returnMsg + "\r\n");
        return returnMsg;
    }

    /**
     * 获取当前场景下的实体
     *
     * @param msg
     * @return
     */
    public String getSceneObject(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());
        Scene scene = sceneService.getSceneById(account.getScene().getSceneId());
        cxt.writeAndFlush(scene.toString() + "\r\n");
        return scene.toString();
    }

}
