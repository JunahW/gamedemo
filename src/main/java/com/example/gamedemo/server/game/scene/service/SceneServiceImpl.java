package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.model.Drop;
import com.example.gamedemo.server.common.utils.RandomUtils;
import com.example.gamedemo.server.game.bag.model.AbstractItem;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.monster.model.DropObject;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.command.ChangeSceneCommand;
import com.example.gamedemo.server.game.scene.command.EnterSceneCommand;
import com.example.gamedemo.server.game.scene.command.SceneMonsterRebornDelayCommand;
import com.example.gamedemo.server.game.scene.constant.SceneTypeEnum;
import com.example.gamedemo.server.game.scene.handler.AbstractMapHandler;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.packet.SM_Aoi;
import com.example.gamedemo.server.game.scene.resource.LandformResource;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    Scene targetScene = getSceneById(player, sceneId);
    if (targetScene == null) {
      logger.info("[{}]场景不存在", sceneId);
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    SpringContext.getSceneExecutorService()
        .submit(ChangeSceneCommand.valueOf(player.getSceneId(), player, sceneId));
    return true;
  }

  @Override
  public Scene getSceneById(CreatureObject creature, int sceneId) {
    Scene scene = sceneManager.getSceneBySceneResourceId(sceneId);
    if (scene == null) {
      scene = SpringContext.getDungeonService().getDungeonSceneByPlayerId(creature.getId());
    }
    if (scene == null) {
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    return scene;
  }

  @Override
  public boolean changeScene(Player player, int sceneId) {
    MapResource targetMapResource = sceneManager.getSceneResourceById(sceneId);
    if (targetMapResource == null) {
      logger.info("场景[{}]不存在", sceneId);
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }

    // 如果进入的是副本
    if (targetMapResource.getSceneTypeEnum().equals(SceneTypeEnum.DUNGEON_SCENE)) {
      SpringContext.getSceneExecutorService()
          .submit(ChangeSceneCommand.valueOf(player.getSceneId(), player, sceneId));
      return true;
    }
    // 当前的场景
    Scene currentScene = getSceneById(player, player.getSceneId());
    MapResource mapResource = sceneManager.getSceneResourceById(currentScene.getSceneResourceId());
    // 如果是从副本跳会原场景
    if (mapResource.getSceneTypeEnum().equals(SceneTypeEnum.DUNGEON_SCENE)) {
      SpringContext.getSceneExecutorService()
          .submit(ChangeSceneCommand.valueOf(player.getSceneId(), player, sceneId));
      return true;
    }

    int[] neighborArray = mapResource.getNeighborArray();
    // 判断场景是否相邻
    boolean isNeighbor = false;
    for (int neighbor : neighborArray) {
      if (neighbor == sceneId) {
        isNeighbor = true;
        break;
      }
    }
    if (!isNeighbor) {
      logger.info(
          "[{}][{}]进入[{}]失败，只能进入相邻的场景", player.getSceneObjectType(), player.getId(), sceneId);
      RequestException.throwException(I18nId.SCENE_NO_NEIGHBOR);
    }
    SpringContext.getSceneExecutorService()
        .submit(ChangeSceneCommand.valueOf(player.getSceneId(), player, sceneId));
    return true;
  }

  @Override
  public boolean serverChangeScene(Player player, int sceneId) {
    leaveScene(player);
    SpringContext.getSceneExecutorService().submit(EnterSceneCommand.valueOf(player, sceneId));
    return true;
  }

  @Override
  public void leaveScene(Player player) {
    // 清理buff
    player.getBuffContainer().clear();
    MapResource sceneResource = sceneManager.getSceneResourceById(player.getSceneId());
    AbstractMapHandler handler = AbstractMapHandler.getHandler(sceneResource.getSceneTypeEnum());
    handler.leaveMap(player);
  }

  @Override
  public boolean enterScene(Player player, int sceneId) {
    MapResource sceneResource = sceneManager.getSceneResourceById(sceneId);
    AbstractMapHandler handler = AbstractMapHandler.getHandler(sceneResource.getSceneTypeEnum());
    handler.enterMap(player, sceneId);
    return true;
  }

  @Override
  public boolean move2Coordinate(Player player, int x, int y) {
    int sceneId = player.getSceneId();

    MapResource mapResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    LandformResource landformResource =
        sceneManager.getLandformResourceById(mapResource.getLandformId());
    int[][] sceneMap = landformResource.getMapArray();

    if (landformResource.getWidth() - 1 < x || landformResource.getHeight() - 1 < y) {
      logger.info("请求位置参数不合法");
      RequestException.throwException(I18nId.SCENE_POSITION_ERROR);
    }
    // 修改玩家当前位置
    if (sceneMap[x][y] == 0) {
      logger.info("该位置有障碍物");
      RequestException.throwException(I18nId.SCENE_OBSTACLE);
    }

    /** 移动位置 */
    int preX = player.getX();
    int preY = player.getY();
    player.setX(x);
    player.setY(y);

    // 通知场景玩家移动了
    Scene scene = sceneManager.getSceneBySceneResourceId(sceneId);
    scene.biologyObjectMove(player);
    logger.info("({},{})从移动到({},{})", preX, preY, x, y);
    return true;
  }

  @Override
  public MapResource getSceneResourceById(int sceneId) {
    return sceneManager.getSceneResourceById(sceneId);
  }

  @Override
  public void createDropObject(Scene scene, int monsterResourceId) {

    MonsterResource monsterResource =
        SpringContext.getMonsterService().getMonsterResourceById(monsterResourceId);
    List<Drop> dropList = monsterResource.getDropList();
    for (Drop drop : dropList) {
      double chance = drop.getChance();
      boolean randomBoolean = RandomUtils.getRandomBoolean(chance);
      if (randomBoolean) {
        DropObject dropObject = DropObject.valueOf(drop.getItemId(), drop.getQuantity());
        dropObject.setSceneId(scene.getSceneResourceId());
        scene.enterScene(dropObject);
        AbstractItem item = dropObject.getItem();
        logger.info("掉落物[{}*{}]", item.getItemResourceId(), item.getQuantity());
      }
    }
  }

  @Override
  public void handMonsterDeadEvent(Player attacker, Scene scene, Monster monster) {

    logger.info("场景[{}]的[{}]怪物已经死亡", scene.getSceneResourceId(), monster.getId());
    // 掉落装备
    int monsterResourceId = monster.getMonsterResourceId();
    createDropObject(scene, monsterResourceId);
    // 新增经验
    MonsterResource monsterResource =
        SpringContext.getMonsterService().getMonsterResourceById(monsterResourceId);
    attacker.setExp(attacker.getExp() + monsterResource.getExp());

    scene.leaveScene(monster.getId());

    // 怪物重生
    long rebornTime = monsterResource.getRebornTime();
    if (rebornTime > 0) {
      SpringContext.getSceneExecutorService()
          .submit(SceneMonsterRebornDelayCommand.valueOf(scene, rebornTime, monsterResourceId));
    }

    MapResource sceneResource = sceneManager.getSceneResourceById(scene.getSceneResourceId());
    AbstractMapHandler handler = AbstractMapHandler.getHandler(sceneResource.getSceneTypeEnum());
    handler.handleMonsterDead(attacker, scene, monster);
  }

  @Override
  public void sceneStart() {
    List<Scene> sceneList = getSceneList();
    for (Scene scene : sceneList) {
      scene.start();
      scene.initMonster();
    }
  }

  @Override
  public Map<Integer, MonsterResource> getMonsterResourceMapBySceneId(int sceneId) {
    return sceneManager.getMonsterResourceMapBySceneId(sceneId);
  }

  @Override
  public void aoi(Player player) {
    SceneObjectView sceneObjectView = player.getSceneObjectView();
    MapResource mapResource =
        SpringContext.getSceneService().getSceneResourceById(player.getSceneId());
    LandformResource landformResource =
        sceneManager.getLandformResourceById(mapResource.getLandformId());
    SM_Aoi sm_aoi =
        SM_Aoi.valueOf(
            sceneObjectView, landformResource.getMapArray(), player.getX(), player.getY());
    SessionManager.sendMessage(player, sm_aoi);
  }

  @Override
  public LandformResource getLandformResourceById(int id) {
    return sceneManager.getLandformResourceById(id);
  }

  @Override
  public Scene createDungeonSceneBySceneId(int sceneId) {
    // 创建副本
    MapResource sceneResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    if (sceneResource == null
        || !sceneResource.getSceneTypeEnum().equals(SceneTypeEnum.DUNGEON_SCENE)) {
      logger.info("该场景配置资源不存在");
      RequestException.throwException(I18nId.SCENE_RESOURCE_NO_EXIST);
    }
    Scene scene = Scene.valueOf(sceneId);
    scene.initMonster();
    return scene;
  }
}
