package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.common.utils.ExcelUtils;
import com.example.gamedemo.server.game.scene.model.Map;
import com.example.gamedemo.server.game.scene.model.Scene;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description:
 */
public class SceneManager {
    public static ConcurrentHashMap<String, Scene> sceneId2SceneMap = new ConcurrentHashMap<String, Scene>();

    public static ConcurrentHashMap<String, Map> mapId2MapMap = new ConcurrentHashMap<>();

    static {
        //加载地图信息
        List<Map> mapList = ExcelUtils.importExcel(Map.class);
        for (Map map : mapList) {
            SceneManager.putMap(map);
        }

        //加载配置的信息
        List<Scene> sceneList = ExcelUtils.importExcel(Scene.class);
        for (Scene scene : sceneList) {
            scene.setMap(mapId2MapMap.get(scene.getMapId()));
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

    /**
     * 新增地图
     *
     * @param map
     */
    public static void putMap(Map map) {
        mapId2MapMap.put(map.getMapId(), map);
    }

    /**
     * 获取地图
     *
     * @param mapId
     * @return
     */
    public static Map getMapById(String mapId) {
        return mapId2MapMap.get(mapId);
    }

}
