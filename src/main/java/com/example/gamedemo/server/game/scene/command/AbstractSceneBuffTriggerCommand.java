package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneCommand;
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
public class AbstractSceneBuffTriggerCommand extends AbstractSceneCommand {
  private static final Logger logger =
      LoggerFactory.getLogger(AbstractSceneBuffTriggerCommand.class);

  public AbstractSceneBuffTriggerCommand(int sceneId) {
    super(sceneId);
  }

  /**
   * @param sceneId
   * @return
   */
  public static AbstractSceneBuffTriggerCommand valueOf(int sceneId) {
    return new AbstractSceneBuffTriggerCommand(sceneId);
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
