package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.server.common.SpringContext;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengj
 * @description
 * @date 2019/7/9
 */
public class SceneMonsterRebornDelayCommand extends AbstractSceneDelayCommand {
  private static final Logger logger =
      LoggerFactory.getLogger(SceneMonsterRebornDelayCommand.class);
  private int monsterResourceId;
  private Scene scene;

  public SceneMonsterRebornDelayCommand(Scene scene, long delay, int monsterResourceId) {
    super(scene, delay);
    this.monsterResourceId = monsterResourceId;
    this.scene = scene;
  }

  /**
   * @param scene
   * @param delay
   * @param monsterResourceId
   * @return
   */
  public static SceneMonsterRebornDelayCommand valueOf(
      Scene scene, long delay, int monsterResourceId) {
    SceneMonsterRebornDelayCommand sceneMonsterRebornDelayCommand =
        new SceneMonsterRebornDelayCommand(scene, delay, monsterResourceId);
    return sceneMonsterRebornDelayCommand;
  }

  public int getMonsterResourceId() {
    return monsterResourceId;
  }

  public void setMonsterResourceId(int monsterResourceId) {
    this.monsterResourceId = monsterResourceId;
  }

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void doAction() {
    logger.info("[{}]重新生成怪物[{}]开始", getSceneId(), monsterResourceId);
    SpringContext.getMonsterService().createMonster(scene, monsterResourceId);
    logger.info("[{}]重新生成怪物[{}]完成", getSceneId(), monsterResourceId);
  }
}
