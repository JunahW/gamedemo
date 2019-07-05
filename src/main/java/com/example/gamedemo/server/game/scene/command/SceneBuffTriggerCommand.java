package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.SceneCommand;
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
public class SceneBuffTriggerCommand extends SceneCommand {
  private static final Logger logger = LoggerFactory.getLogger(SceneBuffTriggerCommand.class);

  public SceneBuffTriggerCommand(int sceneId) {
    super(sceneId);
  }

  /**
   * @param sceneId
   * @return
   */
  public static SceneBuffTriggerCommand valueOf(int sceneId) {
    return new SceneBuffTriggerCommand(sceneId);
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