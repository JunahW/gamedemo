package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.scene.model.Scene;

import java.util.concurrent.ConcurrentHashMap;

public class SceneManager {
    public static ConcurrentHashMap<String, Scene> sceneId2SceneMap = new ConcurrentHashMap<String, Scene>();

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
