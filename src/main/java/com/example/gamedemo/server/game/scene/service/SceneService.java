package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.SceneResource;

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
   * @param sceneId
   * @return
   */
  boolean gotoScene(Player player, int sceneId);

  /**
   * 通过ID获取场景
   *
   * @param sceneId
   * @return
   */
  Scene getSceneById(int sceneId);

  /**
   * 去其他的相邻场景
   *
   * @param player
   * @param sceneId
   * @return
   */
  boolean move2Scene(Player player, int sceneId);

  /**
   * 通过id获取配置信息
   *
   * @param sceneId
   * @return
   */
  SceneResource getSceneResourceById(int sceneId);
}
