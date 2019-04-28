package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import com.example.gamedemo.server.game.scene.constant.SceneCmd;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/4/28
 */
@ComponentScan
public class SceneController {
    @Autowired
    private SceneService sceneService;

    @Autowired
    private AccountService accountService;


    {
        ControllerManager.add(SceneCmd.GOTO, this::gotoScene);
        ControllerManager.add(SceneCmd.LIST, this::getSceneList);
    }

    public List<Scene> getSceneList(String msg) {
        return sceneService.getSceneList();
    }


    public String gotoScene(String msg) {
        String[] msgs = msg.split(" ");
        Account account = accountService.getAccountById(msgs[1]);
        Scene scene = sceneService.getSceneById(msgs[2]);
        sceneService.gotoScene(account, scene);
        String returnMsg = account.getCountName() + "进入" + scene.getSceneName();
        return returnMsg;
    }

    public String move(String msg){

        return "";
    }


}
