package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wengj
 * @date: 2019/6/13
 * @description: 场景管理器
 */
@Component
public class SceneManager {
  /** 静态资源 */
  private Map<Integer, MapResource> sceneResource =
      ResourceManager.getResourceMap(MapResource.class);

  /** 动态场景 */
  private ConcurrentHashMap<Integer, Scene> sceneMap = new ConcurrentHashMap<>();

  @PostConstruct
  public void init() {
    for (Map.Entry<Integer, MapResource> sceneResourceEntry : sceneResource.entrySet()) {
      MapResource mapResource = sceneResourceEntry.getValue();
      if (mapResource == null) {
        continue;
      }
      Scene scene = Scene.valueOf(mapResource.getMapId());
      sceneMap.put(scene.getSceneResourceId(), scene);
    }
  }

  /**
   * 根据id获取场景静态资源
   *
   * @param sceneId
   * @return
   */
  public MapResource getSceneResourceById(int sceneId) {
    return sceneResource.get(sceneId);
  }

  /**
   * 获取场景
   *
   * @param sceneResourceId
   * @return
   */
  public Scene getSceneBysceneResourceId(int sceneResourceId) {
    return sceneMap.get(sceneResourceId);
  }

  /**
   * 获取场景列表
   *
   * @return
   */
  public List<Scene> getSceneList() {
    LinkedList<Scene> scenes = new LinkedList<>();
    for (Map.Entry<Integer, Scene> sceneEntry : sceneMap.entrySet()) {
      Scene scene = sceneEntry.getValue();
      if (scene != null) {
        scenes.add(scene);
      }
    }
    return scenes;
  }
}
