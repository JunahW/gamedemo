package com.example.gamedemo.server.game.scene.service;

import com.example.gamedemo.common.constant.I18nId;
import com.example.gamedemo.common.exception.RequestException;
import com.example.gamedemo.common.session.SessionManager;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.common.constant.GameConstant;
import com.example.gamedemo.server.common.model.Drop;
import com.example.gamedemo.server.common.utils.RandomUtils;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.model.SceneObjectView;
import com.example.gamedemo.server.game.monster.model.DropObject;
import com.example.gamedemo.server.game.monster.resource.MonsterResource;
import com.example.gamedemo.server.game.player.model.Player;
import com.example.gamedemo.server.game.scene.command.ChangeSceneCommand;
import com.example.gamedemo.server.game.scene.command.EnterSceneCommand;
import com.example.gamedemo.server.game.scene.command.SceneMonsterRebornDelayCommand;
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
    SpringContext.getSceneExecutorService()
        .submit(ChangeSceneCommand.valueOf(player.getSceneId(), player, sceneId));
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
  public boolean changeScene(Player player, int sceneId) {
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

    // 当前的场景
    Scene currentScene = sceneManager.getSceneBySceneResourceId(player.getSceneId());
    // 退出当前场景
    currentScene.leaveScene(player.getId());

    SpringContext.getSceneExecutorService().submit(EnterSceneCommand.valueOf(player, sceneId));
    return true;
  }

  @Override
  public boolean enterScene(Player player, int sceneId) {
    Scene targetScene = sceneManager.getSceneBySceneResourceId(sceneId);
    MapResource mapResource = sceneManager.getSceneResourceById(sceneId);
    player.setSceneId(sceneId);
    player.setX(mapResource.getX());
    player.setY(mapResource.getY());
    targetScene.enterScene(player);
    logger.info("[{}][{}]进入[{}]", player.getSceneObjectType(), player.getId(), sceneId);
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
  public void createMonsters4Scene(int sceneId) {
    Set<Integer> monsterSet = sceneManager.getMonsterSetBySceneId(sceneId);
    for (Integer monsterResourceId : monsterSet) {
      SpringContext.getMonsterService().createMonster(sceneId, monsterResourceId);
    }
    logger.info("[{}]场景生成怪物[{}]", sceneId, monsterSet);
  }

  @Override
  public void createDropObject(int sceneId, int monsterResourceId) {
    Scene scene = SpringContext.getSceneService().getSceneById(sceneId);

    MonsterResource monsterResource =
        SpringContext.getMonsterService().getMonsterResourceById(monsterResourceId);
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
  public void handMonsterDeadEvent(
      CreatureObject attacker, int sceneId, long monsterId, int monsterResourceId) {
    logger.info("场景[{}]的[{}]怪物已经死亡", sceneId, monsterId);
    // 掉落装备
    createDropObject(sceneId, monsterResourceId);
    // 新增经验
    MonsterResource monsterResource =
        SpringContext.getMonsterService().getMonsterResourceById(monsterResourceId);
    attacker.setExp(attacker.getExp() + monsterResource.getExp());

    Scene scene = sceneManager.getSceneBySceneResourceId(sceneId);
    scene.leaveScene(monsterId);

    SpringContext.getSceneExecutorService()
        .submit(
            SceneMonsterRebornDelayCommand.valueOf(
                sceneId, GameConstant.MONSTER_REBORN_PERIOD, monsterResourceId));
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
}
