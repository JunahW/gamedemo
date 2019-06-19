package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.npc.model.Npc;
import com.example.gamedemo.server.game.npc.resource.NpcResource;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.LandformResource;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
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

  private Map<Integer, NpcResource> npcResource = ResourceManager.getResourceMap(NpcResource.class);

  /** <sceneId,<npcId,npcResource>> */
  private Map<Integer, Map<Integer, NpcResource>> mapNpcMap = new HashMap<>();

  private Map<Integer, MonsterResource> monsterResource =
      ResourceManager.getResourceMap(MonsterResource.class);

  /** <sceneId,<monsterId,monsterResource>> */
  private Map<Integer, Map<Integer, MonsterResource>> mapMonstercMap = new HashMap<>();

  private Map<Integer, LandformResource> landformResource =
      ResourceManager.getResourceMap(LandformResource.class);

  /** 动态场景 */
  private ConcurrentHashMap<Integer, Scene> sceneMap = new ConcurrentHashMap<>();

  @PostConstruct
  public void init() {
    initMapNpcMap();
    initMapMonsterMap();
    initScene();
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

  /**
   * 通过id获取地形
   *
   * @param landformId
   * @return
   */
  public LandformResource getLandformResourceById(Integer landformId) {
    return landformResource.get(landformId);
  }

  /** 初始化MapNpcMap */
  private void initMapNpcMap() {
    Map<Integer, Map<Integer, NpcResource>> mapNpcResourceMap = new HashMap<>(16);

    Set<Map.Entry<Integer, NpcResource>> entries = npcResource.entrySet();
    for (Map.Entry<Integer, NpcResource> enhanceResourceEntry : entries) {
      NpcResource resourceValue = enhanceResourceEntry.getValue();
      int mapId = resourceValue.getMapId();
      if (!mapNpcResourceMap.containsKey(mapId)) {
        mapNpcResourceMap.put(mapId, new HashMap<>(16));
      }
      Map<Integer, NpcResource> npcResourceMap = mapNpcResourceMap.get(mapId);

      int npcId = resourceValue.getNpcId();
      if (!npcResourceMap.containsKey(npcId)) {
        npcResourceMap.put(npcId, resourceValue);
      }
    }
    this.mapNpcMap = mapNpcResourceMap;
  }

  /** 初始化场景 */
  private void initScene() {
    for (Map.Entry<Integer, MapResource> sceneResourceEntry : sceneResource.entrySet()) {
      MapResource mapResource = sceneResourceEntry.getValue();
      if (mapResource == null) {
        continue;
      }
      Scene scene = Scene.valueOf(mapResource.getMapId());
      // 初始化npc
      Map<Integer, NpcResource> npcResourceMap = mapNpcMap.get(mapResource.getMapId());
      for (Map.Entry<Integer, NpcResource> entry : npcResourceMap.entrySet()) {
        NpcResource value = entry.getValue();
        Npc npc = Npc.valueOf(value.getNpcId());
        scene.putNpc(npc);
      }
      // 初始化怪物
      Map<Integer, MonsterResource> monsterResourceMap = mapMonstercMap.get(mapResource.getMapId());
      for (Map.Entry<Integer, MonsterResource> entry : monsterResourceMap.entrySet()) {
        MonsterResource value = entry.getValue();
        Monster monster = Monster.valueOf(value.getMonsterId());
        scene.putMonster(monster);
      }

      sceneMap.put(scene.getSceneResourceId(), scene);
    }
  }

  /** 初始化MapMonsterMap */
  private void initMapMonsterMap() {
    Map<Integer, Map<Integer, MonsterResource>> mapMonsterResourceMap = new HashMap<>(16);

    Set<Map.Entry<Integer, MonsterResource>> entries = monsterResource.entrySet();
    for (Map.Entry<Integer, MonsterResource> enhanceResourceEntry : entries) {
      MonsterResource resourceValue = enhanceResourceEntry.getValue();
      int mapId = resourceValue.getMapId();
      if (!mapMonsterResourceMap.containsKey(mapId)) {
        mapMonsterResourceMap.put(mapId, new HashMap<>(16));
      }
      Map<Integer, MonsterResource> npcResourceMap = mapMonsterResourceMap.get(mapId);

      int npcId = resourceValue.getMonsterId();
      if (!npcResourceMap.containsKey(npcId)) {
        npcResourceMap.put(npcId, resourceValue);
      }
    }
    this.mapMonstercMap = mapMonsterResourceMap;
  }
}
