package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
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
    public List<SceneResource> getSceneList() {
        LinkedList<SceneResource> list = new LinkedList<SceneResource>();
        Set<Map.Entry<Object, SceneResource>> entries = ResourceManager.getResourceMap(SceneResource.class).entrySet();
        Iterator<Map.Entry<Object, SceneResource>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }
        return list;
    }

    @Override
    public void gotoScene(Player player, SceneResource sceneResource) {
        player.setSceneResource(sceneResource);

        logger.info("{}进入{}", player.toString(), sceneResource.getSceneName());
        sceneResource.getPlayerSet().add(player);
    }

    @Override
    public SceneResource getSceneById(String sceneId) {
        return ResourceManager.getResourceItemById(SceneResource.class, sceneId);
    }

    @Override
    public int move2Scene(Player player, SceneResource sceneResource) {
        if (null == sceneResource) {
            logger.info("该场景不存在");
            return 0;
        }
        //当前的场景
        SceneResource currentSceneResource = ResourceManager.getResourceItemById(SceneResource.class, player.getSceneResource().getSceneId());

        String[] neighbors = currentSceneResource.getNeighbors().split(",");
        //判断场景是否相邻
        boolean isNeighbor = false;
        for (String neighbor : neighbors) {
            if (neighbor.equals(sceneResource.getSceneId())) {
                isNeighbor = true;
                break;
            }
        }
        if (!isNeighbor) {
            logger.info("{}进入{}失败，只能进入相邻的场景", player.getPlayerName(), sceneResource.getSceneName());
            return 0;
        }

        //退出当前场景
        ResourceManager.getResourceItemById(SceneResource.class, currentSceneResource.getSceneId()).getPlayerSet().remove(player);
        //进入新的场景
        player.setSceneResource(sceneResource);
        player.setX(sceneResource.getX());
        player.setY(sceneResource.getY());
        sceneResource.getPlayerSet().add(player);
        logger.info("{}进入{}", player.getPlayerName(), sceneResource.getSceneName());
        return 1;
    }
}
