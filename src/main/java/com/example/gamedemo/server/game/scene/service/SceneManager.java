package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.scene.model.Scene;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
public class SceneManager {
    public static ConcurrentHashMap<String, Scene> sceneId2SceneMap = new ConcurrentHashMap<String, Scene>();

    static {
        //初始化场景
        Scene scene = new Scene();
        scene.setSceneId("s1001");
        scene.setSceneName("起始之地");
        scene.setNeighbors("a1002");

        Scene scene1 = new Scene();
        scene1.setSceneId("s1002");
        scene1.setSceneName("山洞");
        scene1.setNeighbors("s1001,s1003");

        Scene scene2 = new Scene();
        scene2.setSceneId("s1003");
        scene2.setSceneName("城堡");
        scene2.setNeighbors("s1002");

        SceneManager.setScene(scene);
        SceneManager.setScene(scene1);
        SceneManager.setScene(scene2);
    }

    /**
     * 获取场景地图
     *
     * @param sceneId
     * @return
     */
    public static Scene getSceneById(String sceneId) {
        return sceneId2SceneMap.get(sceneId);
    }

    /**
     * 新增场景
     *
     * @param scene
     */
    public static void setScene(Scene scene) {
        sceneId2SceneMap.put(scene.getSceneId(), scene);
    }
}
