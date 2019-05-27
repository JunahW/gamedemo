package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 场景业务层
 */
@Service
public class SceneServiceImpl implements SceneService {
    private static final Logger logger = LoggerFactory.getLogger(SceneServiceImpl.class);

    @Override
    public List<Scene> getSceneList() {
        LinkedList<Scene> list = new LinkedList<Scene>();
        Set<Map.Entry<Object, Scene>> entries = ResourceManager.getResourceMap(Scene.class).entrySet();
        Iterator<Map.Entry<Object, Scene>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }
        return list;
    }

    @Override
    public void gotoScene(Account account, Scene scene) {
        account.setScene(scene);

        logger.info("{}进入{}", account.toString(), scene.getSceneName());
        scene.getAccountSet().add(account);
    }

    @Override
    public Scene getSceneById(String sceneId) {
        return ResourceManager.getResourceItemById(Scene.class, sceneId);
    }

    @Override
    public int move2Scene(Account account, Scene scene) {
        if (null == scene) {
            logger.info("该场景不存在");
            return 0;
        }
        //当前的场景
        Scene currentScene = ResourceManager.getResourceItemById(Scene.class, account.getScene().getSceneId());

        String[] neighbors = currentScene.getNeighbors().split(",");
        //判断场景是否相邻
        boolean isNeighbor = false;
        for (String neighbor : neighbors) {
            if (neighbor.equals(scene.getSceneId())) {
                isNeighbor = true;
                break;
            }
        }
        if (!isNeighbor) {
            logger.info("{}进入{}失败，只能进入相邻的场景", account.getAcountName(), scene.getSceneName());
            return 0;
        }

        //退出当前场景
        ResourceManager.getResourceItemById(Scene.class, currentScene.getSceneId()).getAccountSet().remove(account);
        //进入新的场景
        account.setScene(scene);
        account.setX(scene.getX());
        account.setY(scene.getY());
        scene.getAccountSet().add(account);
        logger.info("{}进入{}", account.getAcountName(), scene.getSceneName());
        return 1;
    }
}
