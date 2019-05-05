package com.example.gamedemo.server.game.scene.controller;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountService;
import com.example.gamedemo.server.game.manager.ControllerManager;
import com.example.gamedemo.server.game.scene.constant.SceneCmd;
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
    public List<Scene> getSceneList(String msg) {
        return sceneService.getSceneList();
    }


    /**
     * 传送金进入场景
     *
     * @param msg
     * @return
     */
    public String gotoScene(String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = accountService.getAccountById(msgs[0]);

        Scene scene = sceneService.getSceneById(msgs[2]);
        sceneService.gotoScene(account, scene);
        String returnMsg = account.getCountName() + "进入" + scene.getSceneName();
        return returnMsg;
    }

    /**
     * 进入相邻的场景
     *
     * @param msg
     * @return
     */
    public String move2Scene(String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = accountService.getAccountById(msgs[0]);
        Scene scene = sceneService.getSceneById(msgs[2]);

        int isSuccess = sceneService.move2Scene(account, scene);
        if (isSuccess == 0) {
            return account.getCountName() + "进入" + scene.getSceneName() + "失败";
        }
        return account.getCountName() + "进入" + scene.getSceneName();
    }

    /**
     * 获取当前场景下的实体
     *
     * @param msg
     * @return
     */
    public String getSceneObject(String msg) {
        String[] msgs = msg.split(" ");
        //获取当前的账户信息
        Account account = accountService.getAccountById(msgs[0]);
        Scene scene = sceneService.getSceneById(account.getScene().getSceneId());

        return scene.toString();
    }

}
