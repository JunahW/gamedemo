package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountManager;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        Set<Map.Entry<String, Scene>> entries = SceneManager.sceneId2SceneMap.entrySet();
        Iterator<Map.Entry<String, Scene>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }
        return list;
    }

    @Override
    public void gotoScene(Account account, Scene scene) {
        account.setScene(scene);
        logger.info(account.toString() + "进入" + scene.getSceneName());
        scene.getAccountSet().add(account);
        logger.info("当前场景：" + scene.toString());

        ConcurrentHashMap<String, Account> accountId2AccountMap = AccountManager.getAccountId2AccountMap();
        ConcurrentHashMap<String, Scene> sceneId2SceneMap = SceneManager.sceneId2SceneMap;


    }

    @Override
    public Scene getSceneById(String sceneId) {
        return SceneManager.sceneId2SceneMap.get(sceneId);
    }

    @Override
    public int move2Scene(String sceneId) {

        return 0;
    }
}
