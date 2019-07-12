package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneRateCommand;
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

  private Scene scene;

  public SceneBuffRateCommand(Scene scene, long period) {
    super(scene, period);
    this.scene = scene;
  }

  public SceneBuffRateCommand(Scene scene, long delay, long period) {
    super(scene, delay, period);
    this.scene = scene;
  }

  /**
   * @param scene
   * @return
   */
  public static SceneBuffRateCommand valueOf(Scene scene, long delay, long period) {
    return new SceneBuffRateCommand(scene, delay, period);
  }

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void doAction() {
    Map<Long, SceneObject> sceneObjectMap = scene.getSceneObjectMap();
    for (Map.Entry<Long, SceneObject> entry : sceneObjectMap.entrySet()) {
      SceneObject sceneObject = entry.getValue();
      if (sceneObject instanceof CreatureObject) {
        CreatureObject creatureObject = (CreatureObject) sceneObject;
        creatureObject.getBuffContainer().executeBuff();
      }
    }
  }
}
