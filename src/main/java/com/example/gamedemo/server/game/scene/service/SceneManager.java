package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.scene.constant.ObjectStatus;
import com.example.gamedemo.server.game.scene.model.Npc;
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
        scene.setNeighbors("s1002");
        scene.getNpcSet().add(new Npc("n1001", "村民01", ObjectStatus.LIVE));
        scene.getNpcSet().add(new Npc("n1002", "村民02", ObjectStatus.LIVE));


        Scene scene1 = new Scene();
        scene1.setSceneId("s1002");
        scene1.setSceneName("山洞");
        scene1.setNeighbors("s1001,s1003");
        scene1.getNpcSet().add(new Npc("n1003", "村民03", ObjectStatus.LIVE));
        scene1.getNpcSet().add(new Npc("n1004", "村民04", ObjectStatus.LIVE));


        Scene scene2 = new Scene();
        scene2.setSceneId("s1003");
        scene2.setSceneName("城堡");
        scene2.setNeighbors("s1002");
        scene2.getNpcSet().add(new Npc("n1005", "村民05", ObjectStatus.LIVE));
        scene2.getNpcSet().add(new Npc("n1006", "村民06", ObjectStatus.LIVE));


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
