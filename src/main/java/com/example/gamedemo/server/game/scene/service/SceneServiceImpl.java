package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.executer.scene.SceneExecutor;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.model.Drop;
import com.example.gamedemo.server.common.utils.RandomUtils;
import com.example.gamedemo.server.game.base.constant.SceneObjectTypeEnum;
import com.example.gamedemo.server.game.monster.model.DropObject;
import com.example.gamedemo.server.game.monster.model.Monster;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.resource.LandformResource;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    Scene currentScene = sceneManager.getSceneBySceneResourceId(currentSceneId);
    currentScene.leaveScene(player.getId());

    MapResource mapResource = sceneManager.getSceneResourceById(sceneId);
    player.setSceneId(sceneId);
    player.setX(mapResource.getX());
    player.setY(mapResource.getY());

    logger.info("{}进入{}", player.getId(), sceneId);
    return true;
  }

  @Override
  public Scene getSceneById(int sceneId) {
    Scene scene = sceneManager.getSceneBySceneResourceId(sceneId);
    if (scene == null) {
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    return scene;
  }

  @Override
  public boolean move2Scene(Player player, int sceneId) {
    Scene targetScene = sceneManager.getSceneBySceneResourceId(sceneId);
    if (targetScene == null) {
      logger.info("场景[{}]不存在", sceneId);
      RequestException.throwException(I18nId.SCENE_NO_EXIST);
    }
    // 当前的场景
    Scene currentScene = sceneManager.getSceneBySceneResourceId(player.getSceneId());
    int sceneResourceId = currentScene.getSceneResourceId();
    MapResource mapResource = sceneManager.getSceneResourceById(sceneResourceId);

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
      logger.info("{}进入{}失败，只能进入相邻的场景", player.getJobName(), mapResource.getMapName());
      RequestException.throwException(I18nId.SCENE_NO_NEIGHBOR);
    }
    // 退出当前场景
    currentScene.leaveScene(player.getId());

    // 进入新的场景
    targetScene = sceneManager.getSceneBySceneResourceId(sceneId);
    player.setSceneId(sceneId);
    player.setX(mapResource.getX());
    player.setY(mapResource.getY());
    targetScene.enterScene(player);
    logger.info("{}进入{}", player.getJobName(), mapResource.getMapName());
    return true;
  }

  @Override
  public boolean move2Coordinate(Player player, int x, int y) {
    int sceneId = player.getSceneId();

    MapResource mapResource = SpringContext.getSceneService().getSceneResourceById(sceneId);
    LandformResource landformResource =
        sceneManager.getLandformResourceById(mapResource.getLandformId());
    int[][] sceneMap = landformResource.getMapArray();
    // TODO 移动位置

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
  public void createMonsters4Scene(int sceneId) {
    Set<Integer> monsterSet = sceneManager.getMonsterSetBySceneId(sceneId);
    for (Integer monsterResourceId : monsterSet) {
      SpringContext.getMonsterService().createMonster(sceneId, monsterResourceId);
    }
    logger.info("[{}]场景生成怪物[{}]", sceneId, monsterSet);
  }

  @Override
  public void createDropObject(int sceneId, long monsterId) {
    Scene scene = SpringContext.getSceneService().getSceneById(sceneId);
    Monster monster = (Monster) scene.getSceneObjectMap().get(monsterId);
    MonsterResource monsterResource =
        SpringContext.getMonsterService().getMonsterResourceById(monster.getMonsterResourceId());
    List<Drop> dropList = monsterResource.getDropList();
    for (Drop drop : dropList) {
      double chance = drop.getChance();
      boolean randomBoolean = RandomUtils.getRandomBoolean(chance);
      if (randomBoolean) {
        DropObject dropObject = DropObject.valueOf(drop.getItemId(), drop.getQuantity());
        dropObject.setSceneId(sceneId);
        scene.enterScene(dropObject);
      }
    }
  }

  @Override
  public void handMonsterDeadEvent(int sceneId, long monsterId) {
    logger.info("场景[{}]的[{}]怪物已经死亡", sceneId, monsterId);
    // 掉落装备
    createDropObject(sceneId, monsterId);

    Scene scene = sceneManager.getSceneBySceneResourceId(sceneId);

    Monster monster =
        (Monster) scene.getSceneObjectByType(SceneObjectTypeEnum.MONSTER).get(monsterId);
    int monsterResourceId = monster.getMonsterResourceId();

    scene.leaveScene(monsterId);

    SceneExecutor.addDelayTask(
        sceneId,
        20000,
        new Runnable() {
          @Override
          public void run() {
            logger.info("[{}]重新生成怪物开始", sceneId);
            SpringContext.getMonsterService().createMonster(sceneId, monsterResourceId);
            logger.info("[{}]重新生成怪物完成", sceneId);
          }
        });
  }
}
