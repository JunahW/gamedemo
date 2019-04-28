package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.scene.model.Scene;

import java.util.List;

public interface SceneService {
    /**
     * 获取场景列表
     *
     * @return
     */
    List<Scene> getSceneList();

    /**
     * 进入场景
     * @param account
     * @param scene
     */
    void gotoScene(Account account, Scene scene);

    /**
     * 通过ID获取场景
     * @param sceneId
     * @return
     */
    Scene getSceneById(String sceneId);
}
