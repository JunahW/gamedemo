package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.server.common.anno.HandlerClass;
import com.example.gamedemo.server.common.anno.HandlerMethod;
import com.example.gamedemo.server.common.session.SessionManager;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
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
@HandlerClass
public class SceneController {
    @Autowired
    private SceneService sceneService;

    @Autowired
    private AccountService accountService;


    /**
     * 获取场景列表
     *
     * @param msg
     * @return
     */
    @HandlerMethod(cmd = "list")
    public List<Scene> getSceneList(ChannelHandlerContext cxt, String msg) {
        List<Scene> sceneList = sceneService.getSceneList();
        cxt.channel().writeAndFlush(sceneList + "\r\n");
        return sceneService.getSceneList();
    }


    /**
     * 传送金进入场景
     *
     * @param msg
     * @return
     */
    @HandlerMethod(cmd = "goto")
    public String gotoScene(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());

        Scene scene = sceneService.getSceneById(msgs[1]);
        sceneService.gotoScene(account, scene);
        String returnMsg = account.getAcountName() + "进入" + scene.getSceneName();
        cxt.writeAndFlush(returnMsg + "\r\n");
        return returnMsg;
    }

    /**
     * 进入相邻的场景
     *
     * @param msg
     * @return
     */
    @HandlerMethod(cmd = "move")
    public String move2Scene(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());
        Scene scene = sceneService.getSceneById(msgs[1]);
        String returnMsg = null;
        int isSuccess = sceneService.move2Scene(account, scene);
        if (isSuccess == 0) {
            returnMsg = account.getAcountName() + "进入" + scene.getSceneName() + "失败";
        } else {
            returnMsg = account.getAcountName() + "进入" + scene.getSceneName();
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
    @HandlerMethod(cmd = "aoi")
    public String getSceneObject(ChannelHandlerContext cxt, String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = SessionManager.getAccountByChannel(cxt.channel());
        Scene scene = sceneService.getSceneById(account.getScene().getSceneId());
        cxt.writeAndFlush(scene.toString() + "\r\n");
        return scene.toString();
    }

}
