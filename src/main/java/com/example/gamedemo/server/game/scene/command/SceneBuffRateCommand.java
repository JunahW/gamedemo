package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneRateCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.base.gameobject.CreatureObject;
import com.example.gamedemo.server.game.base.gameobject.SceneObject;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/7/5
 */
public class SceneBuffRateCommand extends AbstractSceneRateCommand {
  private static final Logger logger = LoggerFactory.getLogger(SceneBuffRateCommand.class);

  public SceneBuffRateCommand(int sceneId, long period) {
    super(sceneId, period);
  }

  public SceneBuffRateCommand(int sceneId, long delay, long period) {
    super(sceneId, delay, period);
  }

  /**
   * @param sceneId
   * @return
   */
  public static SceneBuffRateCommand valueOf(int sceneId, long delay, long period) {
    return new SceneBuffRateCommand(sceneId, delay, period);
  }

  @Override
  public void doAction() {
    Scene scene = SpringContext.getSceneService().getSceneById(getSceneId());
    Map<Long, SceneObject> sceneObjectMap = scene.getSceneObjectMap();
    for (Map.Entry<Long, SceneObject> entry : sceneObjectMap.entrySet()) {
      SceneObject sceneObject = entry.getValue();
      if (sceneObject instanceof CreatureObject) {
        CreatureObject creatureObject = (CreatureObject) sceneObject;
        creatureObject.executeBuff();
      }
    }
  }
}
