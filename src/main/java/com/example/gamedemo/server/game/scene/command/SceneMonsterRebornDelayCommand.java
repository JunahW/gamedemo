package com.example.gamedemo.server.game.scene.command;

import com.example.gamedemo.common.executer.scene.impl.AbstractSceneDelayCommand;
import com.example.gamedemo.server.common.SpringContext;
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

  public SceneMonsterRebornDelayCommand(int sceneId, long delay, int monsterResourceId) {
    super(sceneId, delay);
    this.monsterResourceId = monsterResourceId;
  }

  /**
   * @param sceneId
   * @param delay
   * @param monsterResourceId
   * @return
   */
  public static SceneMonsterRebornDelayCommand valueOf(
      int sceneId, long delay, int monsterResourceId) {
    SceneMonsterRebornDelayCommand sceneMonsterRebornDelayCommand =
        new SceneMonsterRebornDelayCommand(sceneId, delay, monsterResourceId);
    return sceneMonsterRebornDelayCommand;
  }

  public int getMonsterResourceId() {
    return monsterResourceId;
  }

  public void setMonsterResourceId(int monsterResourceId) {
    this.monsterResourceId = monsterResourceId;
  }

  @Override
  public void doAction() {
    logger.info("[{}]重新生成怪物[{}]开始", getSceneId(), monsterResourceId);
    SpringContext.getMonsterService().createMonster(getSceneId(), monsterResourceId);
    logger.info("[{}]重新生成怪物[{}]完成", getSceneId(), monsterResourceId);
  }
}
