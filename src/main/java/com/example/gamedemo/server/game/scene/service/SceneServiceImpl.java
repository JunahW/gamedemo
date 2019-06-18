package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.SceneResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wengj
 * @date: 2019/4/29
 * @description: 场景业务层
 */
@Service
public class SceneServiceImpl implements SceneService {
  private static final Logger logger = LoggerFactory.getLogger(SceneServiceImpl.class);

  @Autowired private SceneManager sceneManager;

  @Override
  public List<Scene> getSceneList() {
    List<Scene> sceneList = sceneManager.getSceneList();
    return sceneList;
  }

  @Override
  public boolean gotoScene(Player player, int sceneId) {
    Scene targetScene = getSceneById(sceneId);
    if (targetScene == null) {
      logger.info("[{}]场景不存在", sceneId);
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    // 当前场景
    int currentSceneId = player.getSceneId();
    Scene currentScene = sceneManager.getSceneBysceneResourceId(currentSceneId);
    currentScene.leaveScene(player.getId());

    SceneResource sceneResource = sceneManager.getSceneResourceById(sceneId);
    player.setSceneId(sceneId);
    player.setX(sceneResource.getX());
    player.setY(sceneResource.getY());

    logger.info("{}进入{}", player.getId(), sceneId);
    return true;
  }

  @Override
  public Scene getSceneById(int sceneId) {
    Scene scene = sceneManager.getSceneBysceneResourceId(sceneId);
    if (scene == null) {
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    return scene;
  }

  @Override
  public boolean move2Scene(Player player, int sceneId) {
    Scene scene = sceneManager.getSceneBysceneResourceId(sceneId);
    if (scene == null) {
      logger.info("场景[{}]不存在", sceneId);
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    // 当前的场景
    Scene currentScene = sceneManager.getSceneBysceneResourceId(player.getSceneId());
    int sceneResourceId = currentScene.getSceneResourceId();
    SceneResource sceneResource = sceneManager.getSceneResourceById(sceneResourceId);

    int[] neighborArray = sceneResource.getNeighborArray();
    // 判断场景是否相邻
    boolean isNeighbor = false;
    for (int neighbor : neighborArray) {
      if (neighbor == sceneId) {
        isNeighbor = true;
        break;
      }
    }
    if (!isNeighbor) {
      logger.info("{}进入{}失败，只能进入相邻的场景", player.getRoleName(), sceneResource.getSceneName());
      RequestException.throwException(I18nId.SCENE_NO_NEIGHBOR);
    }
    // 退出当前场景
    currentScene.leaveScene(player.getId());

    // 进入新的场景
    Scene targetScene = sceneManager.getSceneBysceneResourceId(sceneId);
    player.setSceneId(sceneId);
    player.setX(sceneResource.getX());
    player.setY(sceneResource.getY());
    targetScene.enterScene(player);
    logger.info("{}进入{}", player.getRoleName(), sceneResource.getSceneName());
    return true;
  }

  @Override
  public SceneResource getSceneResourceById(int sceneId) {
    return sceneManager.getSceneResourceById(sceneId);
  }
}
