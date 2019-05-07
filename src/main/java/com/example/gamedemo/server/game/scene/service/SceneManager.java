package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.common.utils.ExcelUtils;
import com.example.gamedemo.server.game.scene.constant.ObjectStatus;
import com.example.gamedemo.server.game.scene.model.Npc;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
public class SceneManager {
    public static ConcurrentHashMap<String, Scene> sceneId2SceneMap = new ConcurrentHashMap<String, Scene>();

    static {
        //加载配置的信息
        List<Scene> sceneList = ExcelUtils.importExcel(Scene.class);
        for (Scene scene : sceneList) {
            SceneManager.setScene(scene);
        }
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
