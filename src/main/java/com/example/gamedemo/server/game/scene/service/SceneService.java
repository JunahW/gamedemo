package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;

import java.util.List;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 场景业务接口层
 */
public interface SceneService {
    /**
     * 获取场景列表
     *
     * @return
     */
    List<Scene> getSceneList();

    /**
     * 进入场景
     *
     * @param player
     * @param scene
     */
    void gotoScene(Player player, Scene scene);

    /**
     * 通过ID获取场景
     *
     * @param sceneId
     * @return
     */
    Scene getSceneById(String sceneId);

    /**
     * 去其他的相邻场景
     *
     * @param player
     * @param scene
     * @return
     */
    int move2Scene(Player player, Scene scene);
}
